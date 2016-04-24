package tw.chikuo.vegetariantreasure.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import com.koushikdutta.ion.Ion;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import tw.chikuo.vegetariantreasure.PermissionManager;
import tw.chikuo.vegetariantreasure.RestaurantToMapItemTask;
import tw.chikuo.vegetariantreasure.adapter.RestaurantAdapter;
import tw.chikuo.vegetariantreasure.object.MapItem;
import tw.chikuo.vegetariantreasure.object.MapObject;
import tw.chikuo.vegetariantreasure.object.parse.Restaurant;
import tw.chikuo.vegetariantreasure.R;

/**
 * Created by Chi on 2016/4/10.
 *
 * Need to input data : restaurantId
 */
public class RestaurantActivity extends AppCompatActivity {

    private RestaurantAdapter restaurantAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        // Actionbar
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
        }


        // Query for the restaurant
        if (getIntent().hasExtra("restaurantId")) {

            String restaurantId = getIntent().getStringExtra("restaurantId");

            ParseQuery<Restaurant> restaurantParseQuery = ParseQuery.getQuery(Restaurant.class);
            restaurantParseQuery.getInBackground(restaurantId, new GetCallback<Restaurant>() {
                @Override
                public void done(final Restaurant restaurant, ParseException e) {
                    if (e == null) {

                        // Name
                        if (restaurant.getName() != null && actionBar != null) {
                            actionBar.setTitle(restaurant.getName());
                        }

                        // RecyclerView
                        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                        restaurantAdapter = new RestaurantAdapter(RestaurantActivity.this,restaurant);
                        LinearLayoutManager layoutManager
                                = new LinearLayoutManager(RestaurantActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(restaurantAdapter);

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
        if (restaurantAdapter.getPermissionManager() != null){
            restaurantAdapter.getPermissionManager().onResultHandle(requestCode, permissions, grantResults);
        }
    }
}
