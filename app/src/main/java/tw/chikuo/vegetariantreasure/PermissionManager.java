package tw.chikuo.vegetariantreasure;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by chikuo on 2016/1/26.
 */
public class PermissionManager {


    public static final int REQUEST_READ_EXTERNAL_STORAGE = 0;
    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_ACCESS_COARSE_LOCATION = 2;
    public static final int REQUEST_ACCESS_FINE_LOCATION = 3;


    private static Activity requestActivity;
    private static OnGrantedResultListener onGrantedResultListener;

    public PermissionManager(Activity requestActivity) {
        this.requestActivity = requestActivity;
    }

    // Permission
    public void loadPermissions(String perm,int requestCode, OnGrantedResultListener onGrantedResultListener) {
        this.onGrantedResultListener = onGrantedResultListener;

        if (ContextCompat.checkSelfPermission(requestActivity, perm) != PackageManager.PERMISSION_GRANTED) {
            // Permission did not granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(requestActivity,new String[]{perm},requestCode);
            }
        } else {
            // Permission granted
            if (onGrantedResultListener != null) {
                onGrantedResultListener.granted();
            }

        }
    }
    public void onResultHandle(int requestCode,String[] permissions, int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // granted
            if (onGrantedResultListener != null) {
                onGrantedResultListener.granted();
            }
        } else {
            // no granted
            // TODO show error message
        }
    }


    public interface OnGrantedResultListener {
        public void granted();
    }

}
