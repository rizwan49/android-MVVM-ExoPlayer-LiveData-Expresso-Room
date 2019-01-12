package com.ar.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.ar.bakingapp.database.DataBaseHelper;
import com.ar.bakingapp.network.model.IngredientsItem;
import com.ar.bakingapp.network.model.Recipe;

import java.io.Serializable;
import java.util.List;

import static com.ar.bakingapp.activities.recipe.RecipeActivity.RECIPE_ID;

public class UpdateBakingService extends IntentService {
    public static final String WIDGET_RECEIVER_ACTION_NAME = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String FROM_ACTIVITY_INGREDIENTS_LIST = "INGREDIENTS_LIST";

    public UpdateBakingService() {
        super("UpdateBakingService");
    }

    public static void startBakingService(Context context, int recipeId) {
        Intent intent = new Intent(context, UpdateBakingService.class);
        intent.putExtra(RECIPE_ID, recipeId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            int id = intent.getIntExtra(RECIPE_ID, -1);
            if (id != -1) {
                Recipe recipe = DataBaseHelper.getInstance().recipeDao().getRecipeDetailNoLiveData(id);
                if (recipe != null) {
                    updateBakingWidgets(recipe.getIngredients(), id);
                }
            }
        }
    }


    private void updateBakingWidgets(List<IngredientsItem> ingredients, int recipeId) {
        Intent intent = new Intent(WIDGET_RECEIVER_ACTION_NAME);
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST, (Serializable) ingredients);
        intent.putExtra(RECIPE_ID, recipeId);
        sendBroadcast(intent);
    }
}
