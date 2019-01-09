package com.ar.bakingapp.activities.recipe;

import android.arch.lifecycle.LiveData;

import com.ar.bakingapp.database.RecipeDao;
import com.ar.bakingapp.network.model.Recipe;

import javax.inject.Singleton;

@Singleton
public class RecipeModel {
    private final RecipeDao recipeDao;

    public RecipeModel(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public LiveData<Recipe> getRecipeDetail(int recipeId) {
        // Returns a LiveData object directly from the database.
        return recipeDao.getRecipeDetail(recipeId);
    }
}
