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
   * Simple Share Builder class to share text on Android
   */
  public static class ShareBuilder {
    private Context context;
    private String subject;
    private String text;
    private String chooserText = "Share using...";

    /**
     * Constructer for the intent share builder
     *
     * @param context the context
     */
    public ShareBuilder(Context context) {
      this.context = context;
    }

    /**
     * Set the Subject of the share intent
     *
     * @param subject the subject
     * @return the builder
     */
    public ShareBuilder setSubject(String subject) {
      this.subject = subject;
      return this;
    }

    /**
     * Set the Text of the share intent
     *
     * @param text the text
     * @return the builder
     */
    public ShareBuilder setText(String text) {
      this.text = text;
      return this;
    }

    /**
     * Set the chooser text for the share builder
     *
     * @param chooserText the chooser text
     * @return the builder
     */
    public ShareBuilder setChooserText(String chooserText) {
      this.chooserText = chooserText;
      return this;
    }

    /**
     * Start the share intent
     */
    public void share() {
      Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
      sharingIntent.setType("text/plain");
      if (!TextUtils.isNullOrEmpty(subject)) {
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
      }
      if (!TextUtils.isNullOrEmpty(text)) {
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
      }
      context.startActivity(Intent.createChooser(sharingIntent, "Share using..."));
    }
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
