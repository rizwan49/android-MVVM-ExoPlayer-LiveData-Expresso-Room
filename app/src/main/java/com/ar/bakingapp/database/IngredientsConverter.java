package com.ar.bakingapp.database;

import android.arch.persistence.room.TypeConverter;

import com.ar.bakingapp.network.model.IngredientsItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientsConverter {
    @TypeConverter
    public String fromList(List<IngredientsItem> items) {
        if (items == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<IngredientsItem>>() {
        }.getType();
        return gson.toJson(items, type);
    }

    @TypeConverter
    public List<IngredientsItem> toList(String itemString) {
        if (itemString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<IngredientsItem>>() {
        }.getType();
        return gson.fromJson(itemString, type);
    }
}
