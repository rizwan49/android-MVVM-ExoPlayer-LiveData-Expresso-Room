package com.ar.bakingapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.ar.bakingapp.network.model.Recipe;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
@TypeConverters({IngredientsConverter.class, StepsConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
}