package com.bsk.sampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.bijoysingh.starter.prefs.DataStore;

import java.util.concurrent.Future;

public class DataStoreActivity extends AppCompatActivity {

  private static final String KEY_STRING = "KEY_STRING";
  private static final String KEY_INTEGER = "KEY_INTEGER";
  private static final String KEY_BOOL = "KEY_BOOL";
  private static final String KEY_LONG = "KEY_LONG";
  private static final String KEY_DOUBLE = "KEY_DOUBLE";

  private static final String TEST_STRING = "TEST_STRING";
  private static final int TEST_INTEGER = 100;
  private static final boolean TEST_BOOL = true;
  private static final long TEST_LONG = 50L;
  private static final double TEST_DOUBLE = 0.5;

  String logText;
  TextView logView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_data_store);

    logView = findViewById(R.id.logs);
  }

  @Override
  protected void onResume() {
    super.onResume();
    startTest();
  }

  private void startTest() {
    logText = "";
    notifyText();
    flushExistingData();
    testSynchronousLoadAndWrite();
    testSynchronousLoad();
    testAsynchronousLoad();
  }

  private void notifyText() {
    logView.setText(logText);
  }

  private void flushExistingData() {
    log("INFO", "Starting flushExistingData()");
    DataStore store = DataStore.get(this);
    store.setWriteSynchronous(true);
    store.empty();

    sleep();
    DataStore.reset();

    log("DONE", "Data Flushed");
    notifyText();
  }


  private void testSynchronousLoadAndWrite() {
    log("INFO", "Starting testSynchronousLoadAndWrite()");
    DataStore store = DataStore.get(this);
    store.setWriteSynchronous(true);
    loadData(store);
    verifyData(store);

    sleep();
    DataStore.reset();

    store = DataStore.get(this);
    log("DONE", "Instance Reset");

    verifyData(store);
  }

  private void testSynchronousLoad() {
    log("INFO", "Starting testSynchronousLoad()");
    DataStore store = DataStore.get(this);
    loadData(store);
    verifyData(store);

    sleep();
    DataStore.reset();

    store = DataStore.get(this);
    log("DONE", "Instance Reset");

    verifyData(store);
  }

  private void testAsynchronousLoad() {
    log("INFO", "Starting testAsynchronousLoad()");
    Future<DataStore> storeFuture = DataStore.getLater(this);

    while (!storeFuture.isDone()) {
      // Do nothing
    }

    try {
      DataStore store = storeFuture.get();
      log("DONE", "Future Instance Loaded");

      loadData(store);
      verifyData(store);

      sleep();
      DataStore.reset();

      store = DataStore.get(this);
      log("DONE", "Instance Reset");

      verifyData(store);
    } catch (Exception exception) {
      log("FAILED", "Data Store Asynchronously Failed");
    }
  }

  private void loadData(DataStore store) {
    store.put(KEY_STRING, TEST_STRING);
    store.put(KEY_INTEGER, TEST_INTEGER);
    store.put(KEY_BOOL, TEST_BOOL);
    store.put(KEY_LONG, TEST_LONG);
    store.put(KEY_DOUBLE, TEST_DOUBLE);
    log("DONE", "Data Added");
  }

  private void verifyData(DataStore store) {
    if (!store.get(KEY_STRING, "").contentEquals(TEST_STRING)) {
      log("FAILED", "String Add/Load Failed");
    }
    if (store.get(KEY_INTEGER, 0) != TEST_INTEGER) {
      log("FAILED", "Integer Add/Load Failed");
    }
    if (store.get(KEY_BOOL, false) != TEST_BOOL) {
      log("FAILED", "Boolean Add/Load Failed");
    }
    if (store.get(KEY_LONG, 0L) != TEST_LONG) {
      log("FAILED", "Long Add/Load Failed");
    }
    if (store.get(KEY_DOUBLE, 0.0) != TEST_DOUBLE) {
      log("FAILED", "Double Add/Load Failed");
    }
    log("DONE", "Data Verified");
  }

  private void log(String state, String value) {
    String textToLog = "[" + state + "]... " + value;
    logText += textToLog + "\n";
    Log.d("DataStoreTest", textToLog);
    notifyText();
  }

  private void sleep() {
    try {
      Thread.sleep(1000);
    } catch (Exception exception) {
      // Do nothing
    }
  }
}
