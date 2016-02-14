package com.github.bijoysingh.starter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Bijoy on 3/6/2015.
 * This file is the core function file of the entire application
 * All the functions here are static functions and form the basis of the app
 */
public class Functions {

    /**
     * Makes a toast(this is much cleaner than the code for toast)
     *
     * @param context the activity context
     * @param text    the text to be printed
     */
    public static void makeToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Converts dp value to pixel value
     *
     * @param context the app context
     * @param dp      the length in dp
     * @return the pixel size
     */
    public static int dpToPixels(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
