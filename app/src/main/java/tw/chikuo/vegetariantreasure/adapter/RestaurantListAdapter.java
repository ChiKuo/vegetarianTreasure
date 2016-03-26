package tw.chikuo.vegetariantreasure.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tw.chikuo.vegetariantreasure.R;

/**
 * Created by Chi on 2016/3/26.
 */
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
    @Override
    public RestaurantListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.recycler_restaurant_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        protected TextView ;
//        protected Button ;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//            sectionTitleTextView = (TextView) itemView.findViewById(R.id.section_title_text_view);
//            mapButton = (Button) itemView.findViewById(R.id.map_button);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
