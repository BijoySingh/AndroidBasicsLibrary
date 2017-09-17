package com.github.bijoysingh.starter.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class SimpleAppCompatActivity extends AppCompatActivity implements ViewContainer {

  public View findView(int id) {
    return findViewById(id);
  }

  public TextView findTextView(int id) {
    return (TextView) findView(id);
  }

  public EditText findEditText(int id) {
    return (EditText) findView(id);
  }

  public ImageView findImageView(int id) {
    return (ImageView) findView(id);
  }

  public LinearLayout findLinearLayout(int id) {
    return (LinearLayout) findView(id);
  }

  public RelativeLayout findRelativeLayout(int id) {
    return (RelativeLayout) findView(id);
  }

  public FrameLayout findFrameLayout(int id) {
    return (FrameLayout) findView(id);
  }

  public ViewGroup findViewGroup(int id) {
    return (ViewGroup) findView(id);
  }

  public CheckBox findCheckBox(int id) {
    return (CheckBox) findView(id);
  }

  public RecyclerView findRecyclerView(int id) {
    return (RecyclerView) findView(id);
  }
}
