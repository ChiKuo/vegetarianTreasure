package tw.chikuo.vegetariantreasure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import tw.chikuo.vegetariantreasure.Object.parse.Restaurant;
import tw.chikuo.vegetariantreasure.R;

/**
 * Created by Chi on 2016/3/26.
 */
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    private Context context;
    private List<Restaurant> restaurantList;

    public RestaurantListAdapter(Context context) {
        this.context = context;
        restaurantList = new ArrayList<>();
    }

    @Override
    public RestaurantListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO Setup 2 ViewHolder for horizontal & vertical
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.recycler_restaurant_list_vertical, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantListAdapter.ViewHolder holder, int position) {

        Restaurant restaurant = restaurantList.get(position);

        if (restaurant.getName() != null) {
            holder.nameTextView.setText(restaurant.getName());
        }
        if (restaurant.getPhoto() != null) {
            Ion.with(holder.imageView)
                    .centerCrop()
                    .load(restaurant.getPhoto().getUrl());
        }
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView nameTextView;
        protected ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nameTextView = (TextView) itemView.findViewById(R.id.restaurant_name);
            imageView = (ImageView) itemView.findViewById(R.id.restaurant_image);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }
}
