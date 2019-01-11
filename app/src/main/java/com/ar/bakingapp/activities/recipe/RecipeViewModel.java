package com.ar.bakingapp.activities.recipe;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ar.bakingapp.database.DataBaseHelper;
import com.ar.bakingapp.database.RecipeDao;
import com.ar.bakingapp.network.model.Recipe;

import javax.inject.Singleton;

@Singleton
public class RecipeViewModel extends ViewModel {
    public int selectedRecipeId;
    public int selectedStepIndex;
    private LiveData<Recipe> recipeLiveData;
    private RecipeDao dao;
    private RecipeModel recipeRepo;

    // Instructs Dagger 2 to provide the UserRepository parameter.
    public RecipeViewModel() {
        dao = DataBaseHelper.getInstance().recipeDao();
        this.recipeRepo = new RecipeModel(dao);
    }


    public void init(int recipeId) {
        if (this.recipeLiveData != null) {
            // ViewModel is created on a per-Fragment basis, so the userId
            // doesn't change.
            return;
        }
        recipeLiveData = recipeRepo.getRecipeDetail(recipeId);
    }

    public LiveData<Recipe> getRecipes() {
        return this.recipeLiveData;
    }
}
