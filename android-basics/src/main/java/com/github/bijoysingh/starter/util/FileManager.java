package com.github.bijoysingh.starter.util;

import android.content.Context;

import com.github.bijoysingh.starter.async.SimpleThreadExecutor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
   * @param content  the string to be stored
   */
  public static void writeAsync(final Context context,
                                final String filename,
                                final String content) {
    SimpleThreadExecutor.execute(new Runnable() {
      @Override
      public void run() {
        write(context, filename, content);
      }
    });
  }

  /**
   * It store the file data into the file given by the filename at the root level
   *
   * @param context  the activity context
   * @param filename the filename to store in
   * @param content  the string to be stored
   */
  public static void write(Context context, String filename, String content) {
    writeToFile(context.getFileStreamPath(filename), content);
  }

  /**
   * Reads the data stored in the file given by the filename at the root level
   *
   * @param context  the activity context
   * @param filename the name of the filename
   * @return the string read
   */
  public static String read(Context context, String filename) {
    return readFromFile(context.getFileStreamPath(filename));
  }

  public static String readCompressedFile(File file) {
    try {
      GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(file));
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int length;
      while ((length = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, length);
      }
      outputStream.close();
      inputStream.close();
      return new String(outputStream.toByteArray());
    } catch (IOException ex) {
      // Handle exception
      return "";
    }
  }

  public static void writeCompressedFile(File file, String content) {
    try {
      InputStream inputStream = new ByteArrayInputStream(content.getBytes());
      GZIPOutputStream outputStream = new GZIPOutputStream(new FileOutputStream(file));
      byte[] buffer = new byte[1024];
      int length;
      while ((length = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, length);
      }
      outputStream.close();
      inputStream.close();
    } catch (IOException ex) {
      // Ignore exception
    }
  }

  public static String readFromFile(File fileToRead) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(fileToRead));
      StringBuilder fileContents = new StringBuilder();
      String line = reader.readLine();
      while (line != null) {
        fileContents.append(line);
        line = reader.readLine();
      }
      return fileContents.toString();
    } catch (IOException exception) {
      return "";
    }
  }

  public static boolean writeToFile(File fileToWrite, String content) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite));
      writer.write(content);
      writer.close();
      return true;
    } catch (IOException exception) {
      return false;
    }
  }
}
