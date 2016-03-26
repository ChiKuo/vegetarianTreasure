package tw.chikuo.vegetariantreasure.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
public class SectionListAdapter extends RecyclerView.Adapter<SectionListAdapter.ViewHolder> {

    private Context context;

    public SectionListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SectionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.recycler_section_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SectionListAdapter.ViewHolder holder, int position) {

        // RecyclerView
        RestaurantListAdapter restaurantListAdapter = new RestaurantListAdapter();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(restaurantListAdapter);

        switch (position){
            case 0:
                holder.sectionTitleTextView.setText("在你附近");
                break;
            case 1:
                holder.sectionTitleTextView.setText("好多人去喔");
                break;
            case 2:
                holder.sectionTitleTextView.setText("來嚐鮮吧");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView sectionTitleTextView;
        protected RecyclerView recyclerView;
        protected Button mapButton;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            sectionTitleTextView = (TextView) itemView.findViewById(R.id.section_title_text_view);
            mapButton = (Button) itemView.findViewById(R.id.map_button);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
