package com.ar.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ar.bakingapp.R;
import com.ar.bakingapp.network.model.IngredientsItem;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {
    private List<IngredientsItem> list;
    private Context context;

    public IngredientsAdapter(List<IngredientsItem> list) {
        this.list = list;
    }

    /***
     * this method used to add list into existing list
     * @param mList contains latest list which is fetched based on page wise;
     */
    public void addAllItem(final List<IngredientsItem> mList) {
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
    public IngredientsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item_view, parent, false);
        context = parent.getContext();
        return new IngredientsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientsAdapter.MyViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0)
            return 0;
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientText;

        MyViewHolder(View view) {
            super(view);
            init(view);
        }

        private void init(View view) {
            ingredientText = view.findViewById(R.id.text);
        }

        private void bindView(int position) {
                ingredientText.setText(context.getString(R.string.ingredient_summery, list.get(position).getQuantity() + "", list.get(position).getMeasure()+"",list.get(position).getIngredient()+""));
        }

    }
}