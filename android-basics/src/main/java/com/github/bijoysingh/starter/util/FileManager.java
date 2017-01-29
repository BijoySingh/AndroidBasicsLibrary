package com.github.bijoysingh.starter.util;

import android.content.Context;
import android.util.Log;

import com.github.bijoysingh.starter.async.SimpleThreadExecutor;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by bijoy on 1/1/16.
 * The file manager class
 */
public class FileManager {

  /**
   * It store the file data into the file given by the filename asynchronously
   *
   * @param context  the activity context
   * @param filename the filename to store in
   * @param filedata the string to be stored
   */
  public static void writeAsync(final Context context, final String filename,
                                final String filedata) {
    SimpleThreadExecutor.execute(new Runnable() {
      @Override
      public void run() {
        write(context, filename, filedata);
      }
    });
  }

  /**
   * It store the file data into the file given by the filename
   *
   * @param context  the activity context
   * @param filename the filename to store in
   * @param filedata the string to be stored
   */
  public static void write(Context context, String filename, String filedata) {
    FileOutputStream fos = null;
    try {
      fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
      fos.write(filedata.getBytes());
    } catch (Exception e) {
      Log.e(FileManager.class.getSimpleName(), e.getMessage(), e);
    } finally {
      try {
        if (fos != null) {
          fos.close();
        }
      } catch (Exception e) {
        Log.e(FileManager.class.getSimpleName(), e.getMessage(), e);
      }
    }
  }

  /**
   * Reads the data stored in the file given by the filename
   *
   * @param context  the activity context
   * @param filename the name of the filename
   * @return the string read
   */
  public static String read(Context context, String filename) {
    FileInputStream fis = null;
    try {
      fis = context.openFileInput(filename);
      StringBuilder builder = new StringBuilder();
      int ch;
      while ((ch = fis.read()) != -1) {
        builder.append((char) ch);
      }

      return builder.toString();
    } catch (Exception e) {
      Log.e(FileManager.class.getSimpleName(), e.getMessage(), e);
    } finally {
      try {
        fis.close();
      } catch (Exception e) {
        Log.e(FileManager.class.getSimpleName(), e.getMessage(), e);
      }
    }

    return "";
  }

}
