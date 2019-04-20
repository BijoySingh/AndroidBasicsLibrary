package com.bsk.sampleapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.bijoysingh.starter.prefs.DataStore;
import com.github.bijoysingh.starter.prefs.Store;

public class TestStoreVsDataStore extends AppCompatActivity {

  LinearLayout layout;

  private int INT_TEST_VALUE = 28984912;
  private long LONG_TEST_VALUE = 1321038921L;
  private float FLOAT_TEST_VALUE = 321094.42142f;
  private double DOUBLE_TEST_VALUE = 32102394.423213;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_store_vs_data_store);

    layout = findViewById(R.id.executor_results);

    setupStoreTest();
    setupDataStoreTest();
    testMigration();
  }

  private void setupStoreTest() {
    long time = System.nanoTime();

    String totalMessage = "--Starting Store Setup--\n";
    Store store = Store.get(this);
    store.put("k_string", "String");
    store.put("k_long", LONG_TEST_VALUE);
    store.put("k_int", INT_TEST_VALUE);
    store.put("k_float", FLOAT_TEST_VALUE);
    store.put("k_double", DOUBLE_TEST_VALUE);
    store.put("k_boolean", true);
    totalMessage += (System.nanoTime() - time)/(1000*1000.0) + "ms\n";
    totalMessage += "--Ending Store Setup--\n";

    store.destroy();
    store = Store.get(this);

    time = System.nanoTime();
    totalMessage += "--Starting Store Test--\n";
    store.get("k_string", "String");
    store.put("k_long", LONG_TEST_VALUE);
    store.get("k_int", INT_TEST_VALUE);
    store.put("k_float", FLOAT_TEST_VALUE);
    store.get("k_double", DOUBLE_TEST_VALUE);
    store.put("k_boolean", true);
    store.put("k_string", "String");
    store.get("k_long", LONG_TEST_VALUE);
    store.put("k_int", INT_TEST_VALUE);
    store.get("k_float", FLOAT_TEST_VALUE);
    store.put("k_double", DOUBLE_TEST_VALUE);
    store.get("k_boolean", true);
    totalMessage += (System.nanoTime() - time)/(1000*1000.0) + "ms\n";
    totalMessage += "--Ending Store Test--\n";
    addTextView( "[DONE]... " + totalMessage);
  }

  private void setupDataStoreTest() {
    long time = System.nanoTime();

    String totalMessage = "--Starting DataStore Setup--\n";
    DataStore store = DataStore.get(this);
    store.put("k_string", "String");
    store.put("k_long", LONG_TEST_VALUE);
    store.put("k_int", INT_TEST_VALUE);
    store.put("k_float", FLOAT_TEST_VALUE);
    store.put("k_double", DOUBLE_TEST_VALUE);
    store.put("k_boolean", true);
    totalMessage += (System.nanoTime() - time)/(1000*1000.0) + "ms\n";
    totalMessage += "--Ending DataStore Setup--\n";

    store.destroy();
    store = DataStore.get(this);

    time = System.nanoTime();
    totalMessage += "--Starting DataStore Test--\n";
    store.get("k_string", "String");
    store.put("k_long", LONG_TEST_VALUE);
    store.get("k_int", INT_TEST_VALUE);
    store.put("k_float", FLOAT_TEST_VALUE);
    store.get("k_double", DOUBLE_TEST_VALUE);
    store.put("k_boolean", true);
    store.put("k_string", "String");
    store.get("k_long", LONG_TEST_VALUE);
    store.put("k_int", INT_TEST_VALUE);
    store.get("k_float", FLOAT_TEST_VALUE);
    store.put("k_double", DOUBLE_TEST_VALUE);
    store.get("k_boolean", true);
    totalMessage += (System.nanoTime() - time)/(1000*1000.0) + "ms\n";
    totalMessage += "--Ending DataStore Test--\n";
    addTextView( "[DONE]... " + totalMessage);
  }

  public void testMigration() {
    DataStore dataStore = DataStore.get(this);
    dataStore.empty();
    dataStore.put("k_string", "String");
    dataStore.put("k_long", LONG_TEST_VALUE);
    dataStore.put("k_int", INT_TEST_VALUE);
    dataStore.put("k_float", FLOAT_TEST_VALUE);
    dataStore.put("k_double", DOUBLE_TEST_VALUE);
    dataStore.put("k_boolean", true);

    Store store = Store.get(this);
    store.clearSync();

    String textResult = "";
    dataStore.migrateToStore(store);
    textResult += "String worked? " + (store.get("k_string", "").equals("String")) + "\n";
    textResult += "Int worked? " + (store.get("k_int", 0) == INT_TEST_VALUE) + "\n";
    textResult += "Double worked? " + (store.get("k_double", 0.0) == DOUBLE_TEST_VALUE) + "\n";
    textResult += "Long worked? " + (store.get("k_long", 0L) == LONG_TEST_VALUE) + "\n";
    textResult += "Float worked? " + (store.get("k_float", 0.0f) == FLOAT_TEST_VALUE) + "\n";
    textResult += "Boolean worked? " + (store.get("k_boolean", false) == true) + "\n";
    addTextView( "[DONE]... " + textResult);
  }

  private void addTextView(String text) {
    TextView tv = new TextView(this);
    tv.setLayoutParams(new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    ));
    tv.setText(text);
    layout.addView(tv);
  }
}
