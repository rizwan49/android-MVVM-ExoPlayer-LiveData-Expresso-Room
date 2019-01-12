package com.ar.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ar.bakingapp.R;
import com.ar.bakingapp.activities.home.HomeActivity;
import com.ar.bakingapp.activities.recipe.RecipeActivity;
import com.ar.bakingapp.network.model.IngredientsItem;

import java.util.List;

import static com.ar.bakingapp.activities.recipe.RecipeActivity.RECIPE_ID;
import static com.ar.bakingapp.widget.UpdateBakingService.FROM_ACTIVITY_INGREDIENTS_LIST;
import static com.ar.bakingapp.widget.UpdateBakingService.WIDGET_RECEIVER_ACTION_NAME;


public class BakingWidgetProvider extends AppWidgetProvider {

    static List<IngredientsItem> ingredientsList;
    static int recipeId;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);

        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        //at init click on widget redirect to homeActivity
        Intent appIntent = new Intent(context, HomeActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.empty_view, appPendingIntent);

        //if widget has ingredient list and clicking on gridview redirect to recipeActivity
        Intent recipeIntent = new Intent(context, RecipeActivity.class);
        recipeIntent.putExtra(RECIPE_ID, recipeId);
        PendingIntent recipePendingIntent = PendingIntent.getActivity(context, 1, recipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, recipePendingIntent);

        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingWidgetProvider.class));

        final String action = intent.getAction();

        if (action.equals(WIDGET_RECEIVER_ACTION_NAME)) {
            if (intent.getExtras().getSerializable(FROM_ACTIVITY_INGREDIENTS_LIST) != null) {
                ingredientsList = (List<IngredientsItem>) intent.getExtras().getSerializable(FROM_ACTIVITY_INGREDIENTS_LIST);
                recipeId = intent.getIntExtra(RECIPE_ID, -1);
            }
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
            //Now update all widgets
            BakingWidgetProvider.updateBakingWidgets(context, appWidgetManager, appWidgetIds);
            super.onReceive(context, intent);
        }
    }

}

