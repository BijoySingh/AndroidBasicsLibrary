package com.github.bijoysingh.starter.prefs;

import android.content.Context;
import android.util.Log;

import com.github.bijoysingh.starter.json.SafeJson;
import com.github.bijoysingh.starter.util.FileManager;

import org.json.JSONException;

/**
 * Alternative to PreferenceManager.
 * Allows for cross service preferences
 * Created by bijoy on 2/12/17.
 */

public class StorageManager {

  private Context context;
  private static SafeJson data;
  private boolean isAsync;

  /**
   * StorageManager construct
   *
   * @param context the application context
   */
  public StorageManager(Context context) {
    this.context = context;
    this.isAsync = false;
    maybeRefresh();
  }

  /**
   * The filename to store the content
   *
   * @return the filename
   */
  protected String getFilename() {
    return "StorageManager." + context.getPackageName() + ".txt";
  }

  /**
   * Tell if it has the key
   *
   * @param key the key
   * @return has the key
   */
  public boolean has(String key) {
    return data.has(key);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, boolean value) {
    putSafe(key, value);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, int value) {
    putSafe(key, value);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, String value) {
    putSafe(key, value);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, double value) {
    putSafe(key, value);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, float value) {
    putSafe(key, value);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, long value) {
    putSafe(key, value);
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public boolean get(String key, boolean defaultValue) {
    return data.getBoolean(key, defaultValue);
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public int get(String key, int defaultValue) {
    return data.getInt(key, defaultValue);
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public String get(String key, String defaultValue) {
    return data.getString(key, defaultValue);
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public double get(String key, double defaultValue) {
    return data.getDouble(key, defaultValue);
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public float get(String key, float defaultValue) {
    return (float) data.getDouble(key, defaultValue);
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public long get(String key, long defaultValue) {
    return data.getLong(key, defaultValue);
  }

  /**
   * Puts the content with exception safety
   *
   * @param key   the key
   * @param value the value
   */
  private void putSafe(String key, Object value) {
    try {
      data.put(key, value);
      write();
    } catch (JSONException exception) {
      Log.e(StorageManager.class.getSimpleName(), "Put Failed", exception);
    }
  }

  /**
   * Reloads the preferences. Essential for service accesses
   *
   * @return this reference
   */
  public StorageManager refresh() {
    String content = FileManager.read(context, getFilename());
    try {
      data = new SafeJson(content);
    } catch (JSONException exception) {
      data = new SafeJson();
    }
    return this;
  }

  /**
   * Maybe refresh the file.
   *
   * @return this reference
   */
  private StorageManager maybeRefresh() {
    if (data == null) {
      return refresh();
    }
    return this;
  }

  /**
   * Writing is async.
   *
   * @param isAsync is async
   * @return this reference
   */
  private StorageManager setIsAsync(boolean isAsync) {
    this.isAsync = isAsync;
    return this;
  }

  /**
   * Write the contents into the storage
   */
  private void write() {
    if (isAsync) {
      FileManager.writeAsync(context, getFilename(), data.toString());
      return;
    }
    FileManager.write(context, getFilename(), data.toString());
  }
}
