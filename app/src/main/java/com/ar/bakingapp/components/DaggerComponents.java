package com.ar.bakingapp.components;

import com.ar.bakingapp.activities.home.HomeActivity;
import com.ar.bakingapp.activities.home.HomeModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = HomeModel.class)
public interface DaggerComponents {
    void inject(HomeActivity mainActivity);
}
