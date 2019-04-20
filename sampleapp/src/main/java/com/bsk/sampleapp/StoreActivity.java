package com.bsk.sampleapp;

import android.os.Bundle;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.bijoysingh.starter.prefs.Store;
import com.github.bijoysingh.starter.prefs.VersionedStore;
import com.github.bijoysingh.starter.util.IntentUtils;

public class StoreActivity extends AppCompatActivity {

  Store store;
  Store copyStore;
  Store alternateStore;

  Store versionedStore;

  TextView testingOutput;

  private int INT_TEST_VALUE = 28984912;
  private long LONG_TEST_VALUE = 1321038921L;
  private float FLOAT_TEST_VALUE = 321094.42142f;
  private double DOUBLE_TEST_VALUE = 32102394.423213;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_store);

    findViewById(R.id.test_data_store).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        IntentUtils.startActivity(view.getContext(), DataStoreActivity.class);
      }
    });
    findViewById(R.id.test_data_store_v_store).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        IntentUtils.startActivity(view.getContext(), TestStoreVsDataStore.class);
      }
    });

    initStore();

    store.clear();
    versionedStore.clearSync();
    versionedStore = VersionedStore.get(this, "VERSIONED", 1);
    alternateStore.clear();

    testingOutput = findViewById(R.id.testing_output);
    startTesting();
    startTestingVersioned();
  }

  private void initStore() {
    store = Store.get(this);
    copyStore = Store.get(this);
    alternateStore = Store.get(this, "ALTERNATE");
    versionedStore = Store.get(this, "VERSIONED");
  }

  private void startTesting() {
    testingOutput.setText("Starting Testing...");
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        boolean isValid = readCache(store, false);
        writeOnUIThread(isValid ? "[DONE] Empty Read Passed" : "[FAILED] Empty Read Failed");
        writeOnUIThread(readCache(copyStore, false) ? "[DONE] Copy Empty Read Passed" : "[FAILED] Copy Empty Read Failed");
        writeOnUIThread(readCache(alternateStore, false) ? "[DONE] Alternative Empty Read Passed" : "[FAILED] Alternative Empty Read Failed");

        startWriting(store);
        writeOnUIThread("[DONE] Writing the contents");
        SystemClock.sleep(500);

        isValid = readCache(store, true);
        writeOnUIThread(isValid ? "[DONE] Memory Read Passed" : "[FAILED] Memory Read Failed");
        writeOnUIThread(readCache(copyStore, true) ? "[DONE] Memory Copy Read Passed" : "[FAILED] Memory Copy Read Failed");
        writeOnUIThread(readCache(alternateStore, false) ? "[DONE] Memory for Alternate Read Passed" : "[FAILED] Memory for Alternate Read Failed");

        store.destroy();
        initStore();

        isValid = readCache(store, true);
        writeOnUIThread(isValid ? "[DONE] Disk Read Passed" : "[FAILED] Disk Read Failed");

        store.clear();
        isValid = readCache(store, false);
        writeOnUIThread(isValid ? "[DONE] Clear Read Passed" : "[FAILED] Clear Read Failed");
      }
    });
    thread.start();
  }

  private void startTestingVersioned() {
    testingOutput.setText("Starting Version Testing...");
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        boolean isValid = readCache(versionedStore, false);
        writeOnUIThread(isValid ? "[DONE] Versioned Empty Read Passed" : "[FAILED] Versioned Empty Read Failed");

        startWriting(versionedStore);
        writeOnUIThread("[DONE] Versioned Writing the contents");
        SystemClock.sleep(1000);

        isValid = readCache(versionedStore, true);
        writeOnUIThread(isValid ? "[DONE] Versioned Memory Read Passed" : "[FAILED] Versioned Memory Read Failed");

        versionedStore = VersionedStore.get(StoreActivity.this, "VERSIONED", 2);

        isValid = readCache(versionedStore, true);
        writeOnUIThread(isValid ? "[DONE] Versioned Disk Read Passed" : "[FAILED] Versioned Disk Read Failed");

        SystemClock.sleep(1000);

        versionedStore = VersionedStore.get(StoreActivity.this, "VERSIONED", 3, new VersionedStore.Migration() {
          @Override
          public void onMigration(int startVersion, int endVersion, VersionedStore vstore) {
            if (startVersion == 2) {
              vstore.clear();

              boolean isValid = readCache(versionedStore, false);
              writeOnUIThread(isValid ? "[DONE] Versioned Clear Read Passed" : "[FAILED] Versioned Clear Read Failed");
            }
          }
        });
      }
    });
    thread.start();
  }

  private void writeOnUIThread(final String content) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        testingOutput.setText(testingOutput.getText() + "\n" + content);
      }
    });
  }

  public void startWriting(Store store) {
    store.put("k_string", "String");
    store.put("k_long", LONG_TEST_VALUE);
    store.put("k_int", INT_TEST_VALUE);
    store.put("k_float", FLOAT_TEST_VALUE);
    store.put("k_double", DOUBLE_TEST_VALUE);
    store.put("k_boolean", true);
  }

  public boolean readCache(Store store, boolean expectContent) {
    String rString = store.get("k_string", "");
    long rLong = store.get("k_long", 0L);
    int rInt = store.get("k_int", 0);
    float rFloat = store.get("k_float", 0.0f);
    double rDouble = store.get("k_double", 0.0);
    boolean rBoolean = store.get("k_boolean", false);

    boolean isValid = (expectContent ? rString.equals("String") : rString.equals(""));
    isValid = isValid && (expectContent ? rLong == LONG_TEST_VALUE : rLong == 0L);
    isValid = isValid && (expectContent ? rInt == INT_TEST_VALUE : rInt == 0);
    isValid = isValid && (expectContent ? rFloat == FLOAT_TEST_VALUE : rFloat == 0.0f);
    isValid = isValid && (expectContent ? rDouble == DOUBLE_TEST_VALUE : rDouble == 0.0);
    isValid = isValid && (expectContent == rBoolean);
    return isValid;
  }
}
