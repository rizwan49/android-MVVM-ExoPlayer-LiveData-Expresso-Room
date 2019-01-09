package com.ar.bakingapp.network;



import android.app.Application;
import android.content.Context;

import com.ar.bakingapp.AppApplication;
import com.ar.bakingapp.BuildConfig;
import com.ar.bakingapp.network.interceptors.ErrorHandlerInterceptor;
import com.ar.bakingapp.network.interceptors.HeaderModifierInterceptor;


import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abdul on 14/10/18.
 * 1. created RestClient to setup once, which will improve performance and help to make a server call;
 * 2. added LogInterceptor only into debug mode;
 * 3. added ErrorHandlerInterceptor
 * 4. added HeaderModifier
 */

public class RestClient {
    private RecipeApiService apiService;

    @Inject
    public RestClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.interceptors().add(new HeaderModifierInterceptor());
        httpClient.interceptors().add(new ErrorHandlerInterceptor(AppApplication.getContext()));
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.interceptors().add(logging);
        }

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(RecipeApiService.BASE_URL)//passing API_URL
                .addConverterFactory(GsonConverterFactory.create()) //passing MoshiConverterFactory to convert json key and value into our object
                .client(httpClient.build())//passing OkHttpClient object
                .build();
        apiService = restAdapter.create(RecipeApiService.class);
    }


    //double checked locking singleTon Design.
    public RecipeApiService getApiService() {
        if (apiService == null) {
            synchronized (RestClient.class) {
                if (apiService == null)
                    new RestClient();
            }
        }
        return apiService;
    }

}
