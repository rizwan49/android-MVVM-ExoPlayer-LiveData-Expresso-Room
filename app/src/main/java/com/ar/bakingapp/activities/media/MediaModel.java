package com.ar.bakingapp.activities.media;

import android.arch.lifecycle.LiveData;

import com.ar.bakingapp.database.RecipeDao;
import com.ar.bakingapp.network.model.Recipe;
import com.ar.bakingapp.network.model.StepsItem;

import java.util.List;

public class MediaModel {
    private final RecipeDao recipeDao;

    public MediaModel(RecipeDao dao) {
        this.recipeDao = dao;
    }

    public LiveData<Recipe> getRecipeSteps(int recipeId) {
        return recipeDao.getRecipeDetail(recipeId);
    }
}
