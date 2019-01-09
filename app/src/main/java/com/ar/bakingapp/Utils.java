package com.ar.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Objects;


/**
 * Created by abdul on 15/10/18.
 */

public final class Utils {
    /***
     * using this method to set the view visible
     * @param views list of views...
     */
    public static void hideViews(View... views) {
        for (View view : views) {
            if (view == null) continue;
            view.setVisibility(View.GONE);
        }
    }

    private Utils(){

    }

    /***
     * using this method to set the view visible
     * @param views list of views...
     */
    public static void showViews(View... views) {
        for (View view : views) {
            if (view == null) continue;
            view.setVisibility(View.VISIBLE);
        }
    }


    /***
     * this method used to load image into specified imageView;
     * @param context pass context for Picasso to initiate;
     * @param imageView after loading the image will be appear on this widget;
     * @param url image url
     * @param errorImageDrawable in case error comes during fetching image from given url then this errorImageDrawable will be appear into imageView;
     */
    public static void loadImage(Context context, ImageView imageView, Uri url, int placeHolder, int errorImageDrawable) {
        Picasso.with(context)
                .load(url)
                .placeholder(placeHolder)
                .error(errorImageDrawable)
                .into(imageView);

    }

    /***
     * @param context passing to get the network status
     * @return boolean value as true or false
     * true: if network is available
     * false: if not available
     */
    public static boolean isNetworkAvailable(final Context context) {
        if (context == null) return false;
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (Objects.requireNonNull(connectivityManager).getActiveNetworkInfo() != null) {
            try {
                return connectivityManager.getActiveNetworkInfo().isConnected();
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

}
