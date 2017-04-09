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

  protected View rootView;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    rootView = inflater.inflate(getLayoutId(), container, false);
    onCreateView();
    return rootView;
  }

  /**
   * The layout id of the fragment
   *
   * @return the layout id
   */
  protected abstract int getLayoutId();

  /**
   * When the view is created
   */
  protected abstract void onCreateView();

  /**
   * When the root view of the fragment
   *
   * @return the root view
   */
  protected View getRootView() {
    return rootView;
  }

  /**
   * The findViewById from the root view.
   *
   * @param resourceId the resource id of the layout
   * @return the view
   */
  protected View findViewById(int resourceId) {
    return rootView.findViewById(resourceId);
  }
}
