package com.github.bijoysingh.starter.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Parent Class for the Recycler View holders
 * <p>
 * Created by bijoy on 1/7/16.
 */
public abstract class RecyclerViewHolder<T> extends RecyclerView.ViewHolder {

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

  public <T extends View> T findViewById(int id) {
    return root.findViewById(id);
  }
}
