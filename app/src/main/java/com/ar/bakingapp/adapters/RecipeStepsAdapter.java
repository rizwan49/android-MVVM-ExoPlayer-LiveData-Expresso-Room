package com.ar.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ar.bakingapp.R;
import com.ar.bakingapp.network.model.StepsItem;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.MyViewHolder> {
    private final ListItemOnClickListener mOnClickListener;
    private List<StepsItem> list;
    private Context context;

    public RecipeStepsAdapter(List<StepsItem> list, ListItemOnClickListener mOnClickListener) {
        this.list = list;
        this.mOnClickListener = mOnClickListener;
    }

    /***
     * this method used to add list into existing list
     * @param mList contains latest list which is fetched based on page wise;
     */
    public void addAllItem(final List<StepsItem> mList) {
        if (list == null && mList != null && mList.size() > 0) {
            list = new ArrayList<>();
            list.addAll(mList);
            this.notifyDataSetChanged();
        }
    }

    /***
     * clear all the items of list;
     */
    public void clearAllItems() {
        if (list != null && list.size() > 0) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public RecipeStepsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_item_view, parent, false);
        context = parent.getContext();
        return new RecipeStepsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeStepsAdapter.MyViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0)
            return 0;
        return list.size();
    }

    /***
     * listener for viewHolder's items
     */
    public interface ListItemOnClickListener {
        void onListItemClick(StepsItem selectedObject, View view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stepT;

        MyViewHolder(View view) {
            super(view);
            init(view);
        }

        private void init(View view) {
            stepT = view.findViewById(R.id.stepT);
            itemView.setOnClickListener(this);
        }

        private void bindView(int position) {
            if (position == 0)
                stepT.setText(list.get(position).getShortDescription());
            else
                stepT.setText(context.getString(R.string.step_short_description, list.get(position).getId() + "", list.get(position).getShortDescription()));
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(list.get(getAdapterPosition()), v.findViewById(R.id.recipeImage));
        }
    }
}
