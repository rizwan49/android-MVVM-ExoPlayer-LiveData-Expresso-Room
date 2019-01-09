package com.ar.bakingapp.activities.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ar.bakingapp.database.DataBaseHelper;
import com.ar.bakingapp.database.RecipeDao;
import com.ar.bakingapp.network.RestClient;
import com.ar.bakingapp.network.model.Recipe;

import java.util.List;

import javax.inject.Singleton;

@Singleton
public class HomeViewModel extends ViewModel {

    private LiveData<List<Recipe>> recipeLiveData;
    private HomeModel recipeRepo;
    private RecipeDao dao;

    // Instructs Dagger 2 to provide the UserRepository parameter.
    public HomeViewModel() {
        dao = DataBaseHelper.getInstance().recipeDao();
        this.recipeRepo = new HomeModel(dao, new RestClient());
    }


    public void init() {
        if (this.recipeLiveData != null) {
            // ViewModel is created on a per-Fragment basis, so the userId
            // doesn't change.
            return;
        }
        recipeLiveData = recipeRepo.getAllRecipes();
    }

    public LiveData<List<Recipe>> getRecipesList() {
        return this.recipeLiveData;
    }

}
