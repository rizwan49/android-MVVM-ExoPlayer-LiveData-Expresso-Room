package com.ar.bakingapp.activities.home;

import android.arch.lifecycle.LiveData;

import com.ar.bakingapp.database.RecipeDao;
import com.ar.bakingapp.network.RestClient;
import com.ar.bakingapp.network.model.Recipe;

import java.util.List;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class HomeModel {

    private final RecipeDao recipeDao;
    private final RestClient restClient;

    public HomeModel(RecipeDao recipeDao, RestClient restClient) {
        this.recipeDao = recipeDao;
        this.restClient = restClient;
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        refreshRecipe();
        // Returns a LiveData object directly from the database.
        return recipeDao.getAll();
    }

    private void refreshRecipe() {

        restClient.getApiService().getAllRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> recipeDao.insertList(response.body())).start();
                }
                // FIXME: 02/01/19 need to improve.
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // FIXME: 02/01/19 need to add on failure as well
            }
        });
        // Check for errors here.

    }
}
