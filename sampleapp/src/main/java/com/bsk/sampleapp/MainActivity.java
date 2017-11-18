package com.bsk.sampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.bijoysingh.starter.activity.SimpleAppCompatActivity;
import com.github.bijoysingh.starter.util.IntentUtils;

public class MainActivity extends SimpleAppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findView(R.id.test_data_store).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        IntentUtils.startActivity(view.getContext(), DataStoreActivity.class);
      }
    });
  }
}
