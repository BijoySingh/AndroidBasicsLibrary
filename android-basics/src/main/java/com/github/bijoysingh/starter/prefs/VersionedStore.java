package com.github.bijoysingh.starter.prefs;

import android.content.Context;

public class VersionedStore extends Store {

  private static final String STORE_VERSION_KEY = "__store_version__";

  private final int mVersionNumber;
  private final Migration mMigration;

  private VersionedStore(Context context, String storeName, int versionNumber, Migration migration) {
    super(context, storeName);
    mVersionNumber = versionNumber;
    mMigration = migration;
  }

  public static VersionedStore get(Context context, String storeName, int versionNumber) {
    return get(context, storeName, versionNumber, null);
  }

  public static VersionedStore get(
      Context context,
      String storeName,
      int versionNumber,
      Migration migration) {
    if (versionNumber <= 0) {
      throw new IllegalArgumentException("Version should not be negative");
    }
    if (sStores.containsKey(storeName)) {
      sStores.get(storeName).destroy();
    }

    VersionedStore store = new VersionedStore(context, storeName, versionNumber, migration);
    store.initialise();
    sStores.put(storeName, store);
    return store;
  }

  @Override
  protected void onReadCompleted() {
    super.onReadCompleted();
    int versionKey = get(STORE_VERSION_KEY, 0);
    if (versionKey == 0) {
      put(STORE_VERSION_KEY, mVersionNumber);
      return;
    }
    if (versionKey >= mVersionNumber) {
      return;
    }
    if (mMigration == null) {
      return;
    }
    for (int startIndex = versionKey; startIndex < mVersionNumber; startIndex++) {
      mMigration.onMigration(startIndex, startIndex + 1, this);
    }
    put(STORE_VERSION_KEY, mVersionNumber);
  }

  public interface Migration {
    void onMigration(int startVersion, int endVersion, VersionedStore store);
  }
}
