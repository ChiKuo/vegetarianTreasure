package tw.chikuo.vegetariantreasure.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import tw.chikuo.vegetariantreasure.PermissionManager;
import tw.chikuo.vegetariantreasure.R;
import tw.chikuo.vegetariantreasure.RestaurantToMapItemTask;
import tw.chikuo.vegetariantreasure.activity.MapsActivity;
import tw.chikuo.vegetariantreasure.activity.RestaurantActivity;
import tw.chikuo.vegetariantreasure.activity.WebViewActivity;
import tw.chikuo.vegetariantreasure.object.MapItem;
import tw.chikuo.vegetariantreasure.object.MapObject;
import tw.chikuo.vegetariantreasure.object.parse.Diary;
import tw.chikuo.vegetariantreasure.object.parse.Restaurant;

/**
 * Created by Chi on 2016/3/26.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PermissionManager permissionManager;

    private Context context;
    private Restaurant restaurant;
    private List<Diary> diaryList;

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_FOOD_DIARY = 1;

    public RestaurantAdapter(Context context,Restaurant restaurant) {
        this.context = context;
        this.restaurant = restaurant;

        diaryList = new ArrayList<>();
        ParseQuery<Diary> diaryParseQuery = ParseQuery.getQuery(Diary.class);
        diaryParseQuery.whereEqualTo("restaurantPointer",restaurant);
        diaryParseQuery.findInBackground(new FindCallback<Diary>() {
            @Override
            public void done(List<Diary> objects, ParseException e) {
                if (e == null){
                    diaryList = objects;
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {

            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.recycler_restaurant_header, parent, false);

            return new HeaderViewHolder(itemView);
        } else if (viewType == TYPE_FOOD_DIARY) {

            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.recycler_restaurant_food_diary, parent, false);

            return new FoodDiaryViewHolder(itemView);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof HeaderViewHolder){
            HeaderViewHolder holder = (HeaderViewHolder)viewHolder;

            // Photo
            if (restaurant.getPhoto() != null) {
                Ion.with(holder.photoImageView)
                        .load(restaurant.getPhoto().getUrl());
            }

            // Phone
            if (restaurant.getPhone() != null) {
                holder.phoneButton.setText(restaurant.getPhone());

                // Call
                holder.phoneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final PopupMenu popupMenu = new PopupMenu(context, v);
                        popupMenu.inflate(R.menu.phone_menu);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.call_button:
                                        makePhoneCall(restaurant);
                                        break;
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });
            }

            // Address
            if (restaurant.getAddress() != null) {
                holder.addressButton.setText(restaurant.getAddress());

                // Open Map
                holder.addressButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<MapItem> mapItemList = RestaurantToMapItemTask.translation(restaurant);
                        MapObject mapObject = new MapObject();
                        mapObject.setMapItemList(mapItemList);
                        mapObject.setTitle(restaurant.getName());

                        Intent mapIntent = new Intent(context, MapsActivity.class);
                        mapIntent.putExtra("mapObject",mapObject);
                        context.startActivity(mapIntent);
                    }
                });
            }

            // Food diary title
            if (diaryList.size() <= 0){
                holder.foodDiaryTextView.setVisibility(View.GONE);
            } else {
                holder.foodDiaryTextView.setVisibility(View.VISIBLE);
            }

        } else if (viewHolder instanceof FoodDiaryViewHolder){
            FoodDiaryViewHolder holder = (FoodDiaryViewHolder)viewHolder;

            // Dairy Button
            final Diary diary = diaryList.get(position - 1);
            if (diary.getTitle() != null){
                holder.diaryButton.setText(diary.getTitle());
            }
            holder.diaryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO
                    if (diary.getUrl() != null){
                        Intent diaryIntent = new Intent(context, WebViewActivity.class);
                        diaryIntent.putExtra("webUrl",diary.getUrl());
                        context.startActivity(diaryIntent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return diaryList.size() + 1 ;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_HEADER;
        } else {
            return TYPE_FOOD_DIARY;
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private ImageView photoImageView;
        private Button phoneButton;
        private Button addressButton;
        private TextView foodDiaryTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            photoImageView = (ImageView) itemView.findViewById(R.id.restaurant_photo_image);
            phoneButton = (Button) itemView.findViewById(R.id.restaurant_phone_button);
            addressButton = (Button) itemView.findViewById(R.id.restaurant_address_button);
            foodDiaryTextView = (TextView) itemView.findViewById(R.id.food_diary_title);
        }

    }


    public class FoodDiaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button diaryButton;

        public FoodDiaryViewHolder(View itemView) {
            super(itemView);
            diaryButton = (Button) itemView.findViewById(R.id.diary_button);
            diaryButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }


    private void makePhoneCall(final Restaurant restaurant) {
        // Show permission for android 6.0
        permissionManager = new PermissionManager((RestaurantActivity)context);
        permissionManager.loadPermissions(Manifest.permission.CALL_PHONE,
                PermissionManager.REQUEST_CALL_PHONE, new PermissionManager.OnGrantedResultListener() {
                    @Override
                    public void granted() {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + restaurant.getPhone()));
                        context.startActivity(intent);
                    }
                });
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }
}
