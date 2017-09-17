package com.github.bijoysingh.starter.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A simple extension to an app compat activity. This has a bunch of utility methods which can be
 * used easily
 * Created by bijoy on 9/17/17.
 */

public class SimpleAppCompatActivity extends AppCompatActivity {

  protected TextView getTextView(int id) {
    return (TextView) findViewById(id);
  }

  protected EditText getEditText(int id) {
    return (EditText) findViewById(id);
  }

  protected ImageView getImageView(int id) {
    return (ImageView) findViewById(id);
  }

  protected LinearLayout getLinearLayout(int id) {
    return (LinearLayout) findViewById(id);
  }

  protected RelativeLayout getRelativeLayout(int id) {
    return (RelativeLayout) findViewById(id);
  }

  protected FrameLayout getFrameLayout(int id) {
    return (FrameLayout) findViewById(id);
  }

  protected ViewGroup getViewGroup(int id) {
    return (ViewGroup) findViewById(id);
  }

  protected CheckBox getCheckBox(int id) {
    return (CheckBox) findViewById(id);
  }
}
