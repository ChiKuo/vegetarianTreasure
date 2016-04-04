package tw.chikuo.vegetariantreasure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import tw.chikuo.vegetariantreasure.Object.parse.Restaurant;
import tw.chikuo.vegetariantreasure.R;
import tw.chikuo.vegetariantreasure.adapter.RestaurantListAdapter;
import tw.chikuo.vegetariantreasure.adapter.SectionListAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private SectionListAdapter sectionListAdapter;
    private RestaurantListAdapter restaurantListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("附近美食");
        setSupportActionBar(toolbar);

        // TODO study to use FadingActionBar

        // DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // RecyclerView (For SectionListAdapter)
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//        sectionListAdapter = new SectionListAdapter(MainActivity.this);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(sectionListAdapter);

        // RecyclerView (For SectionListAdapter)
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        restaurantListAdapter = new RestaurantListAdapter(MainActivity.this);
        restaurantListAdapter.setOrientation(RestaurantListAdapter.TYPE_VERTICAL);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(restaurantListAdapter);
        findRestaurant();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_map) {
            Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
            this.startActivity(mapIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void findRestaurant() {
        ParseQuery<Restaurant> restaurantParseQuery = ParseQuery.getQuery(Restaurant.class);
        restaurantParseQuery.findInBackground(new FindCallback<Restaurant>() {
            @Override
            public void done(List<Restaurant> objects, ParseException e) {
                if (e == null) {
                    restaurantListAdapter.setRestaurantList(objects);
                    restaurantListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
