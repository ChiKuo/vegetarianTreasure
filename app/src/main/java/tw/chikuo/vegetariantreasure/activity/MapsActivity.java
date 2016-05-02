package tw.chikuo.vegetariantreasure.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.style.StyleSpan;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.BubbleIconFactory;
import com.google.maps.android.ui.IconGenerator;

import java.util.HashMap;
import java.util.List;

import tw.chikuo.vegetariantreasure.object.MapItem;
import tw.chikuo.vegetariantreasure.object.MapObject;
import tw.chikuo.vegetariantreasure.object.parse.Restaurant;
import tw.chikuo.vegetariantreasure.PermissionManager;
import tw.chikuo.vegetariantreasure.R;

/**
 *  Input data : MapItem (title & restaurantList)
 *
 *  https://developers.google.com/maps/documentation/android-api/utility/#introduction
 *  https://github.com/googlemaps/android-maps-utils/blob/master/demo/src/com/google/maps/android/utils/demo/IconGeneratorDemoActivity.java
 *
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PermissionManager permissionManager;

    // Extra Input
    private String title;
    private List<MapItem> mapObjectList;

    private HashMap<Marker,MapItem> markerRestaurantHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Get extra data
        if (getIntent().hasExtra("mapObject")){
            MapObject mapObject = (MapObject) getIntent().getSerializableExtra("mapObject");
            if (mapObject != null && mapObject.getTitle() != null && mapObject.getMapItemList() != null){
                title = mapObject.getTitle();
                mapObjectList = mapObject.getMapItemList();
            }
        }

        // Actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
        }
        if (title != null){
            actionBar.setTitle(title);
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


            if (mapObjectList != null && mapObjectList.size() > 0){

                // Put Result on map
                markerRestaurantHashMap = new HashMap<>();

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                // Setup each marker
                for (MapItem mapItem : mapObjectList) {
                    if (mapItem.getName() != null){
                        LatLng latLng = new LatLng(mapItem.getLatitude(),mapItem.getLongitude());

                        // Show all markers info on map , Use Google Maps Android API utility library
                        IconGenerator iconFactory = new IconGenerator(this);
                        iconFactory.setTextAppearance(R.style.mapIconText);
                        addIcon(iconFactory, mapItem, latLng);

                        builder.include(latLng);
                    }
                }

                // Setup camera
                if (mapObjectList.size() == 1){
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(mapObjectList.get(0).getLatitude(), mapObjectList.get(0).getLongitude()), 15.0f));
                } else {

                    // Calculate the bounds of all markers
                    final LatLngBounds bounds = builder.build();

                    // Zoom to see all markers
                    // Add bounds to map to avoid swiping outside a certain region:
                    // http://stackoverflow.com/questions/25231949/add-bounds-to-map-to-avoid-swiping-outside-a-certain-region
                    mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                        @Override
                        public void onMapLoaded() {
                            int padding = 200;
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                            mMap.moveCamera(cameraUpdate);
                            mMap.animateCamera(cameraUpdate);
                        }
                    });
                }

                // Marker click
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        MapItem mapItem = markerRestaurantHashMap.get(marker);

                        Intent restaurantIntent = new Intent(MapsActivity.this, RestaurantActivity.class);
                        restaurantIntent.putExtra("restaurantId", mapItem.getRestaurantId());
                        startActivity(restaurantIntent);

                        return false;
                    }
                });

            }

        }

    }


    private void addIcon(IconGenerator iconFactory, MapItem mapItem, LatLng position) {
        CharSequence text = mapItem.getName();
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        // Setup marker with seller calendar data.
        Marker marker = mMap.addMarker(markerOptions);
        markerRestaurantHashMap.put(marker, mapItem);
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
