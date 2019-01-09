package com.ar.bakingapp.network.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.ar.bakingapp.database.IngredientsConverter;
import com.ar.bakingapp.database.StepsConverter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Recipe {

    @SerializedName("image")
    private String image;

    @SerializedName("servings")
    private int servings;

    @SerializedName("name")
    private String name;

    @TypeConverters(IngredientsConverter.class)
    @SerializedName("ingredients")
    private List<IngredientsItem> ingredients;

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @TypeConverters(StepsConverter.class)
    @SerializedName("steps")
    private List<StepsItem> steps;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientsItem> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientsItem> ingredients) {
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<StepsItem> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsItem> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return
                "Recipe{" +
                        "image = '" + image + '\'' +
                        ",servings = '" + servings + '\'' +
                        ",name = '" + name + '\'' +
                        ",ingredients = '" + ingredients + '\'' +
                        ",id = '" + id + '\'' +
                        ",steps = '" + steps + '\'' +
                        "}";
    }


}