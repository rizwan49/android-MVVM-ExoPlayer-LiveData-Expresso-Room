package com.ar.bakingapp.activities.media;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ar.bakingapp.database.DataBaseHelper;
import com.ar.bakingapp.database.RecipeDao;
import com.ar.bakingapp.fragments.PlayerFragment;
import com.ar.bakingapp.network.model.Recipe;
import com.ar.bakingapp.network.model.StepsItem;

import java.util.List;

public class MediaViewModel extends ViewModel {
    public int selectedRecipeId;
    public int selectedPosition;
    private LiveData<Recipe> recipeLiveData;
    private RecipeDao dao;
    private MediaModel mediaRepo;

    // Instructs Dagger 2 to provide the UserRepository parameter.
    public MediaViewModel() {
        dao = DataBaseHelper.getInstance().recipeDao();
        this.mediaRepo = new MediaModel(dao);
    }


    public void init(int recipeId) {
        if (this.recipeLiveData != null) {
            // ViewModel is created on a per-Fragment basis, so the userId
            // doesn't change.
            return;
        }
        recipeLiveData = mediaRepo.getRecipeSteps(recipeId);
    }

    public LiveData<Recipe> getRecipes() {
        return this.recipeLiveData;
    }
}
