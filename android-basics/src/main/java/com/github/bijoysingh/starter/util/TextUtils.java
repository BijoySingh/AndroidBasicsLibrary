package com.github.bijoysingh.starter.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

/**
 * Simple functions for Texts and Strings on Android
 * Created by bijoy on 3/29/17.
 */

public class TextUtils {

  /**
   * Is the String null or empty
   *
   * @param value the string to test
   * @return is the string null or empty
   */
  public static boolean isNullOrEmpty(String value) {
    return value == null || value.isEmpty();
  }

  /**
   * Copies the text to the system clipboard
   *
   * @param context  the context
   * @param clipText the clip to copy
   */
  public static void copyToClipboard(Context context, String clipText) {
    final ClipboardManager clipboard = (ClipboardManager)
        context.getSystemService(Context.CLIPBOARD_SERVICE);
    ClipData clip = ClipData.newPlainText("simple text", clipText);
    clipboard.setPrimaryClip(clip);
  }

}
