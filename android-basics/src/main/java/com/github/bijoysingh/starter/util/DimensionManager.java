package com.github.bijoysingh.starter.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Simple dimension functions
 * Created by bijoy on 1/28/17.
 */

public class DimensionManager {
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
