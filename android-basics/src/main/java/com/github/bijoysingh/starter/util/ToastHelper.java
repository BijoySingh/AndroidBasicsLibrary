package com.github.bijoysingh.starter.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * A seamingly pointless class but helps writing unnecessary arguments when writing toasts
 * Created by bijoy on 10/1/16.
 */
public class ToastHelper {
  private Context context;

  /**
   * The ToastHelper constructor
   *
   * @param context the activity context
   */
  public ToastHelper(Context context) {
    this.context = context;
  }

  /**
   * Show a toast string
   *
   * @param toast the toast string
   */
  public void show(String toast) {
    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
  }

  /**
   * Show a toast string for a long time
   *
   * @param toast the toast string
   */
  public void showLong(String toast) {
    Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
  }

  /**
   * Show a toast string resource
   *
   * @param toast the toast string resource
   */
  public void show(@StringRes Integer toast) {
    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
  }

  /**
   * Show a toast string resource for a long time
   *
   * @param toast the toast string resource
   */
  public void showLong(@StringRes Integer toast) {
    Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
  }

  /**
   * Static method to show a toast string
   *
   * @param context the activity context
   * @param toast   the toast string
   */
  public static void show(Context context, String toast) {
    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
  }

  /**
   * Static method to show a toast string resource
   *
   * @param context the activity context
   * @param toast   the toast string resource
   */
  public static void show(Context context, @StringRes Integer toast) {
    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
  }
}
