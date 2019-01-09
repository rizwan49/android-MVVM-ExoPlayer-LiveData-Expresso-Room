package com.ar.bakingapp.network.interceptors;

import android.content.Context;
import android.util.Log;

import com.ar.bakingapp.Utils;
import com.ar.bakingapp.network.NoConnectivityException;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/***
 * this interceptor will check few condition
 * 1. it will check Internet availability if not then throw an exception which will be handle by presenter and eventBus
 * 2. if internet is available then proceed for the network call
 * 3. if in response getting 200 ie: success then returning response;
 * 3. if in response not getting 200 then passing that response code to mainScreen to show the view with the help of eventBus
 */
public class ErrorHandlerInterceptor implements Interceptor {
    private static final String TAG = ErrorHandlerInterceptor.class.getName();
    private final Context context;

    public ErrorHandlerInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (!Utils.isNetworkAvailable(context)) {
            Log.d(TAG, "errorHandler:internet issue");
            throw new NoConnectivityException(context);
        }

        Response response = chain.proceed(originalRequest);
        Boolean isSuccess = response.code() / 100 == 2 || response.code() / 100 == 3;
        if (isSuccess) {
            return response;
        }

        Log.d(TAG, "responseCode:" + response.code());
        EventBus.getDefault().post(response.code());

        return response;
    }
}
