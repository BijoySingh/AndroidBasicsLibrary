package com.github.bijoysingh.starter.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Common Intent functions
 * Created by bijoy on 9/17/17.
 */

public class IntentUtils {

  /**
   * Start an activity intent
   * @param context the context
   * @param activity the class of the activity
   * @param <T> class which must extend activity
   */
  public static <T extends Activity> void startActivity(Context context, Class<T> activity) {
    Intent intent = new Intent(context, activity);
    context.startActivity(intent);
  }

  /**
   * Opens the play store page for the current app. Falls back on web page if the app doesn't exist
   * @param context the context
   */
  public static void openAppPlayStore(Context context) {
    String appPackageName = context.getPackageName();
    openAppPlayStore(context, appPackageName);
  }

  /**
   * Opens the play store page for the app. Falls back on web page if the app doesn't exist
   * @param context the context
   * @param appPackageName the app's package name you want to open
   */
  public static void openAppPlayStore(Context context, String appPackageName) {
    try {
      context.startActivity(new Intent(
          Intent.ACTION_VIEW,
          Uri.parse("market://details?id=" + appPackageName)));
    } catch (android.content.ActivityNotFoundException anfe) {
      context.startActivity(new Intent(
          Intent.ACTION_VIEW,
          Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
    }
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
}
