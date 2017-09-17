package com.github.bijoysingh.starter.recyclerview;

import android.content.Context;
import android.os.Bundle;
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

import com.github.bijoysingh.starter.activity.ViewContainer;

/**
 * Parent Class for the Recycler View holders
 * <p>
 * Created by bijoy on 1/7/16.
 */
public abstract class RecyclerViewHolder<T>
    extends RecyclerView.ViewHolder
    implements ViewContainer {

  // The Context object
  protected Context context;
  protected View root;

  /**
   * Constructor for the recycler view holder
   *
   * @param context  the application/activity context
   * @param root the view of the current item
   */
  public RecyclerViewHolder(Context context, View root) {
    super(root);
    this.root = root;
    this.context = context;
  }

  /**
   * Populate the recycler view with the data
   *
   * @param data the Item value
   */
  public abstract void populate(T data, Bundle extra);

  /**
   * Returns the context object
   *
   * @return the application/activity context
   */
  private Context getContext() {
    return context;
  }

  public View findView(int id) {
    return root.findViewById(id);
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
