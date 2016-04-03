package tw.chikuo.vegetariantreasure.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import tw.chikuo.vegetariantreasure.Object.parse.Restaurant;
import tw.chikuo.vegetariantreasure.PermissionManager;
import tw.chikuo.vegetariantreasure.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
        }

        // Show permission for android 6.0
        permissionManager = new PermissionManager(MapsActivity.this);
        permissionManager.loadPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                PermissionManager.REQUEST_ACCESS_FINE_LOCATION, new PermissionManager.OnGrantedResultListener() {
                    @Override
                    public void granted() {
                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(MapsActivity.this);
                    }
                });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mMap != null) {

            // Check Permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            // Show my location and room in
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            mMap.setMyLocationEnabled(true);
            mMap.animateCamera(zoom);

            // TODO Change data from Intent
            // Add restaurant Mark
            ParseQuery<Restaurant> restaurantParseQuery = ParseQuery.getQuery(Restaurant.class);
            restaurantParseQuery.findInBackground(new FindCallback<Restaurant>() {
                @Override
                public void done(List<Restaurant> objects, ParseException e) {
                    if (e == null) {

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();

                        // TODO Show all markers info on map , Use Google Maps Android API utility library

                        // Setup each marker
                        for (Restaurant restaurant : objects) {
                            if (restaurant.getGeoPoint() != null) {
                                LatLng latLng = new LatLng(restaurant.getGeoPoint().getLatitude(),
                                        restaurant.getGeoPoint().getLongitude());
                                MarkerOptions options = new MarkerOptions().position(latLng).title(restaurant.getName());
                                mMap.addMarker(options);
                                builder.include(options.getPosition());
                            }
                        }

                        // Calculate the bounds of all markers
                        LatLngBounds bounds = builder.build();

                        // Zoom to see all markers
                        int padding = 100;
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        mMap.moveCamera(cameraUpdate);
                        mMap.animateCamera(cameraUpdate);
                    }
                }
            });

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionManager.onResultHandle(requestCode, permissions, grantResults);
    }

}
