package com.github.bijoysingh.starter.prefs;

import android.content.Context;
import android.util.Log;

import com.github.bijoysingh.starter.util.FileManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Alternative to PreferenceManager.
 * Allows for cross service preferences
 * Created by bijoy on 2/12/17.
 */

public class StorageManager {

  private Context context;
  private JSONObject data;

  /**
   * StorageManager construct
   *
   * @param context the application context
   */
  public StorageManager(Context context) {
    this.context = context;
    refresh();
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
    try {
      return data.getBoolean(key);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public int get(String key, int defaultValue) {
    try {
      return data.getInt(key);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public String get(String key, String defaultValue) {
    try {
      return data.getString(key);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public double get(String key, double defaultValue) {
    try {
      return data.getDouble(key);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public float get(String key, float defaultValue) {
    try {
      return (float) data.getDouble(key);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public long get(String key, long defaultValue) {
    try {
      return data.getLong(key);
    } catch (JSONException exception) {
      return defaultValue;
    }
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
   */
  public StorageManager refresh() {
    String content = FileManager.read(context, getFilename());
    try {
      data = new JSONObject(content);
    } catch (JSONException exception) {
      data = new JSONObject();
    }
    return this;
  }

  /**
   * Write the contents into the storage
   */
  private void write() {
    FileManager.write(context, getFilename(), data.toString());
  }
}
