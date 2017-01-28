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

  /**
   * Converts pixel value to dp value
   *
   * @param context the app context
   * @param pixels  the length in pixels
   * @return the dp size
   */
  public static int pixelsToDp(Context context, int pixels) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return Math.round(pixels / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
  }
}
