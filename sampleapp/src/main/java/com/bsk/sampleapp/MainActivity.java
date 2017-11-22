package com.bsk.sampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.bijoysingh.starter.util.IntentUtils;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.test_data_store).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        IntentUtils.startActivity(view.getContext(), DataStoreActivity.class);
      }
    });
    findViewById(R.id.test_async_task).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        IntentUtils.startActivity(view.getContext(), AsyncActivity.class);
      }
    });

    findViewById(R.id.test_bottom_sheet).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        IntentUtils.startActivity(view.getContext(), BottomSheetActivity.class);
      }
    });

  }
}
