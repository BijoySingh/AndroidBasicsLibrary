package com.bsk.sampleapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.bijoysingh.starter.fragments.SimpleBottomSheetFragment;

public class BottomSheetActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bottom_sheet);

    findViewById(R.id.test_bottom_sheet).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openBottomSheet();
      }
    });
  }

  private void openBottomSheet() {
    BottomSheet fragment = new BottomSheet();
    fragment.show(getSupportFragmentManager(), fragment.getTag());
  }
}
