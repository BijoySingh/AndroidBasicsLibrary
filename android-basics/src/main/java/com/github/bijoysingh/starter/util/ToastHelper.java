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

    public ToastHelper(Context context) {
        this.context = context;
    }

    public void show(String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    public void showLong(String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
    }

    public void show(@StringRes Integer toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    public void showLong(@StringRes Integer toast) {
        Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
    }

    public static void show(Context context, String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, @StringRes Integer toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }
}
