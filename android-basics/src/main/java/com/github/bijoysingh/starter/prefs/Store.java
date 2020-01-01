package com.github.bijoysingh.starter.prefs;

import android.content.Context;

import com.github.bijoysingh.starter.util.FileManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Faster and more reliable alternative to DataStore
 * which does not store a reference to the context after creation.
 * Soon the DataStore will be removed.
 * <p>
 * Allows for cross service preferences
 * Created by bijoy on 2/12/17.
 */
public class Store {

  private static final String STORE_NAME_PREFIX = "store__";
  private static final String DEFAULT_STORE = "DEFAULT_STORE";

  protected static Map<String, Store> sStores = new ConcurrentHashMap<>();

  protected final String mStoreName;
  protected final File mPathToStore;
  protected final File mTempPathToStore;
  protected final Map<String, Object> mMemoryCache;
  protected final ExecutorService mSingleThreadExecutor;
  protected final Runnable mUpdateDiskRunnable;

  private long mLastModifiedTime = 0L;

  protected Store(Context context, String storeName) {
    mStoreName = storeName;

    String filename = STORE_NAME_PREFIX + storeName;
    mPathToStore = new File(context.getFilesDir(), filename);
    mTempPathToStore = new File(context.getFilesDir(), filename + ".temp");
    mLastModifiedTime = mPathToStore.exists() ? mPathToStore.lastModified() : 0L;

    mMemoryCache = new ConcurrentHashMap<>();
    mSingleThreadExecutor = Executors.newSingleThreadExecutor();
    mUpdateDiskRunnable = new Runnable() {
      @Override
      public void run() {
        writeToDisk();
        onWriteCompleted();
      }
    };
  }

  public static Store get(Context context) {
    return get(context, DEFAULT_STORE);
  }

  public static Store get(Context context, String storeName) {
    if (sStores.containsKey(storeName)) {
      return sStores.get(storeName);
    }
    Store store = new Store(context, storeName);
    store.initialise();
    sStores.put(storeName, store);
    return store;
  }

  /**
   * Tell if it has the key
   *
   * @param key the key
   * @return has the key
   */
  public boolean has(String key) {
    return mMemoryCache.containsKey(key);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, boolean value) {
    putAndWrite(key, value);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, int value) {
    putAndWrite(key, value);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, String value) {
    putAndWrite(key, value);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, double value) {
    putAndWrite(key, value);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, float value) {
    putAndWrite(key, value);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, long value) {
    putAndWrite(key, value);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  public void put(String key, Object value) {
    putAndWrite(key, value);
  }

  /**
   * Saves the data into the storage
   *
   * @param key   the key
   * @param value the value to store
   */
  private void putAndWrite(String key, Object value) {
    mMemoryCache.put(key, value);
    mSingleThreadExecutor.submit(mUpdateDiskRunnable);
  }

  private void writeToDisk() {
    JSONObject json = new JSONObject(mMemoryCache);
    FileManager.writeCompressedFile(mTempPathToStore, json.toString());
    mTempPathToStore.renameTo(mPathToStore);
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public boolean get(String key, boolean defaultValue) {
    return has(key) ? (boolean) mMemoryCache.get(key) : defaultValue;
  }

  /**
   * Gets the stored integer value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public int get(String key, int defaultValue) {
    return has(key) ? Integer.parseInt(mMemoryCache.get(key).toString()) : defaultValue;
  }

  /**
   * Gets the stored string value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public String get(String key, String defaultValue) {
    return has(key) ? (String) mMemoryCache.get(key) : defaultValue;
  }

  /**
   * Gets the stored double value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public double get(String key, double defaultValue) {
    return has(key) ? Double.parseDouble(mMemoryCache.get(key).toString()) : defaultValue;
  }

  /**
   * Gets the stored float value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public float get(String key, float defaultValue) {
    return has(key) ? Float.parseFloat(mMemoryCache.get(key).toString()) : defaultValue;
  }

  /**
   * Gets the stored long value
   *
   * @param key          the key
   * @param defaultValue the default value in case not found
   * @return the value or default value
   */
  public long get(String key, long defaultValue) {
    return has(key) ? Long.parseLong(mMemoryCache.get(key).toString()) : defaultValue;
  }

  public void clear() {
    mMemoryCache.clear();
    mSingleThreadExecutor.execute(mUpdateDiskRunnable);
  }

  public void clearSync() {
    mMemoryCache.clear();
    mUpdateDiskRunnable.run();
  }

  public void destroy() {
    sStores.remove(mStoreName);
    mMemoryCache.clear();
  }

  public Set<String> keys() {
    return mMemoryCache.keySet();
  }

  public void remove(String key) {
    mMemoryCache.remove(key);
    mSingleThreadExecutor.submit(mUpdateDiskRunnable);
  }

  public void refresh() {
    mMemoryCache.clear();
    readFromDisk();
  }

  public boolean hasChanged() {
    return mLastModifiedTime != mPathToStore.lastModified();
  }

  protected void initialise() {
    readFromDisk();
  }

  private synchronized void readFromDisk() {
    String cache = FileManager.readCompressedFile(mPathToStore);
    mLastModifiedTime = mPathToStore.lastModified();
    try {
      JSONObject json = new JSONObject(cache);
      Iterator<String> keys = json.keys();
      while (keys.hasNext()) {
        String key = keys.next();
        mMemoryCache.put(key, json.get(key));
      }
      onReadCompleted();
    } catch (JSONException exception) {
      // Read from disk failed
    }
  }

  protected void onReadCompleted() {
    // Nothing to do here
  }

  protected void onWriteCompleted() {
    // Nothing to do here
  }
}
