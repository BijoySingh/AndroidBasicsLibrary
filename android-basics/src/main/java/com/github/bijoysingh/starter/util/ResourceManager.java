package com.github.bijoysingh.starter.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

/**
 * Resource functions
 * Created by bijoy on 1/31/16.
 */
public class ResourceManager {

    public static Integer getColor(Context context, Integer colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getColorLollipop(context, colorId);
        } else {
            return getColorDeprecated(context, colorId);
        }
    }

    private static Integer getColorDeprecated(Context context, Integer colorId) {
        return context.getResources().getColor(colorId);
    }

    @TargetApi(23)
    private static Integer getColorLollipop(Context context, Integer colorId) {
        return context.getResources().getColor(colorId, context.getTheme());
    }
}
