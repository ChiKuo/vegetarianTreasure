package tw.chikuo.vegetariantreasure.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import tw.chikuo.vegetariantreasure.Object.parse.Restaurant;
import tw.chikuo.vegetariantreasure.R;

/**
 * Created by Chi on 2016/4/10.
 */
public class RestaurantActivity extends AppCompatActivity {

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
        if (getIntent().hasExtra("restaurantId")){

            String restaurantId = getIntent().getStringExtra("restaurantId");

            ParseQuery<Restaurant> restaurantParseQuery = ParseQuery.getQuery(Restaurant.class);
            restaurantParseQuery.getInBackground(restaurantId, new GetCallback<Restaurant>() {
                @Override
                public void done(Restaurant restaurant, ParseException e) {
                    if (e == null) {

                        // Name
                        if (restaurant.getName() != null && actionBar != null){
                            actionBar.setTitle(restaurant.getName());
                        }

                        // Photo
                        if (restaurant.getPhoto() != null){
                            ImageView photoImageView = (ImageView) findViewById(R.id.restaurant_photo_image);
                            Ion.with(photoImageView)
                                    .load(restaurant.getPhoto().getUrl());
                        }

                        // Phone
                        if (restaurant.getPhone() != null){
                            Button phoneButton = (Button) findViewById(R.id.restaurant_phone_button);
                            phoneButton.setText(restaurant.getPhone());
                        }

                        // Address
                        if (restaurant.getAddress() != null) {
                            Button addressButton = (Button) findViewById(R.id.restaurant_address_button);
                            addressButton.setText(restaurant.getAddress());
                        }
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
}
