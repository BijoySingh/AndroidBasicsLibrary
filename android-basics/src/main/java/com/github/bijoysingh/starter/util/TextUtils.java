package com.github.bijoysingh.starter.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

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
   * Checks if two nullable strings are equal
   *
   * @param value1 first nullable value
   * @param value2 second nullable value
   * @return are the two String are equal
   */
  public static boolean areEqual(@Nullable String value1, @Nullable String value2) {
    if (value1 == null && value2 == null) {
      return true;
    } else if (value1 == null || value2 == null) {
      return false;
    }
    return value1.contentEquals(value2);
  }

  /**
   * Checks if the two nullable strings are equal
   *
   * @param value1 first nullable value
   * @param value2 second nullable value
   * @return the two Strings are equal. If a string is null it's considered equal to empty
   */
  public static boolean areEqualNullIsEmpty(@Nullable String value1, @Nullable String value2) {
    return areEqual(value1 == null ? "" : value1, value2 == null ? "" : value2);
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
