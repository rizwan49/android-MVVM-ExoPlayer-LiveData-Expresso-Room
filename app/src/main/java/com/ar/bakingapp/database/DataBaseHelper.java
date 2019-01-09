package com.ar.bakingapp.database;

import android.arch.persistence.room.Room;

import com.ar.bakingapp.AppApplication;

public class DataBaseHelper {
    private static AppDatabase db;
    private final static String TAG = DataBaseHelper.class.getName();

    private DataBaseHelper() {
    }

    /***
     *
     * @return an instance of AppDatabase
     */
    public static AppDatabase getInstance() {
        if (db == null) {
            // To make thread safe
            synchronized (DataBaseHelper.class) {
                // check again as multiple threads
                if (db == null) {
                    db = Room.databaseBuilder(AppApplication.getContext(),
                            AppDatabase.class, "recipe_db").build();

                }
            }
        }
        return db;
    }

    public static void dbClose() {
        if (db != null && db.isOpen())
            db.close();
        db = null;
    }
}