package com.ar.bakingapp.network;

import com.ar.bakingapp.network.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeApiService {
    String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    String RECIPE_URL = "topher/2017/May/59121517_baking/baking.json";

    @GET(RECIPE_URL)
    Call<List<Recipe>> getAllRecipes();


}
