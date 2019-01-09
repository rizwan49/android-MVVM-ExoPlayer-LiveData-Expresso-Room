package com.ar.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ar.bakingapp.network.model.Recipe;
import com.ar.bakingapp.network.model.StepsItem;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getAll();

    @Insert(onConflict = REPLACE)
    void insertAll(Recipe... recipe);

    @Delete
    void delete(Recipe user);

    @Insert(onConflict = REPLACE)
    void insertList(List<Recipe> body);

    @Query("SELECT * FROM recipe where id=:recipeId")
    LiveData<Recipe> getRecipeDetail(int recipeId);

}
