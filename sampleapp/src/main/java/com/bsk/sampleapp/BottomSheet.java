package com.bsk.sampleapp;

import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import com.github.bijoysingh.starter.fragments.SimpleBottomSheetFragment;

public class BottomSheet extends SimpleBottomSheetFragment {
  @Override
  public int getLayout() {
    return R.layout.layout_bottom_sheet;
  }

  @Override
  public void setupView(Dialog contentView) {
    TextView title = contentView.findViewById(R.id.title);
    title.setText("What is Lorem Ipsum?");
  }
}
