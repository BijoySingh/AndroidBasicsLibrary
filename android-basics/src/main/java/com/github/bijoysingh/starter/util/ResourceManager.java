package com.github.bijoysingh.starter.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Resource functions
 * Created by bijoy on 1/31/16.
 */
public class ResourceManager {

    public static Integer getColor(Context context, Integer colorId) {
        return ContextCompat.getColor(context, colorId);
    }

}
