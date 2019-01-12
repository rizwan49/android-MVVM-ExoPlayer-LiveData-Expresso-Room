package com.ar.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ar.bakingapp.R;
import com.ar.bakingapp.network.model.IngredientsItem;

import java.util.ArrayList;
import java.util.List;

import static com.ar.bakingapp.activities.recipe.RecipeActivity.RECIPE_ID;
import static com.ar.bakingapp.widget.BakingWidgetProvider.ingredientsList;
import static com.ar.bakingapp.widget.BakingWidgetProvider.recipeId;

public class GridWidgetService extends RemoteViewsService {
    List<IngredientsItem> remoteIngredientList = new ArrayList<>();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }

    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext = null;

        public GridRemoteViewsFactory(Context context) {
            mContext = context;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            remoteIngredientList = ingredientsList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (remoteIngredientList == null) return 0;
            return remoteIngredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (getCount() == 0) return null;
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_view_item);
            views.setTextViewText(R.id.widget_grid_view_item, getString(R.string.ingredient_summery, remoteIngredientList.get(position).getQuantity() + "", remoteIngredientList.get(position).getMeasure() + "", remoteIngredientList.get(position).getIngredient() + ""));

            Intent fillInIntent = new Intent();
            fillInIntent.putExtra(RECIPE_ID, recipeId);
            views.setOnClickFillInIntent(R.id.widget_grid_view_item, fillInIntent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}

