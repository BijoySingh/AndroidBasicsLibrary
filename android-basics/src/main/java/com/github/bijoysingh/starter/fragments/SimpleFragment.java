package com.github.bijoysingh.starter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Simple Fragment base class to ease the process of the fragment creation
 * Created by bijoy on 3/31/17.
 */

public abstract class SimpleFragment extends Fragment {

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(getLayoutId(), container, false);
    onCreateView(root);
    return root;
  }

  protected abstract int getLayoutId();

  protected abstract void onCreateView(View root);

}
