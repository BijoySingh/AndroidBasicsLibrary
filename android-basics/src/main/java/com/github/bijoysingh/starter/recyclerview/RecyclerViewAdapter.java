package com.github.bijoysingh.starter.recyclerview;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * The recycler view adapter for list of content
 * Created by bijoy on 1/7/16.
 */
public abstract class RecyclerViewAdapter<T, U extends RecyclerViewHolder<T>> extends
    RecyclerView.Adapter<U> {

  // The application/activity context
  protected Context context;

  // The layout resource file id
  protected Integer layout;

  // List of contents
  protected List<T> contents;

  // The class type
  protected Class<U> type;

  // Is the click enabled
  protected boolean isClickEnabled;

  // Extra bundle to pass
  protected Bundle extra;

  // The Layout manager
  protected RecyclerView.LayoutManager layoutManager;

  /**
   * The recycler view adapter constructor
   *
   * @param context the application/activity context
   * @param layout  the layout resource file id
   * @param type    the class of the Recycler View Holder
   */
  public RecyclerViewAdapter(Context context, Integer layout, Class<U> type) {
    this.context = context;
    this.layout = layout;
    this.type = type;
    this.contents = new ArrayList<>();
    this.isClickEnabled = false;
  }

  /**
   * Sets if the click is enabled on the items
   *
   * @param clickEnabled is the click enabled
   */
  public void setClickEnabled(boolean clickEnabled) {
    isClickEnabled = clickEnabled;
  }

  /**
   * Sets extra bundle to be send to the view holders
   *
   * @param extra the bundle
   */
  public void setExtra(Bundle extra) {
    this.extra = extra;
  }

  /**
   * On item click listener to Override
   *
   * @param holder The view holder
   * @param item   The object item
   */
  public void onItemClick(U holder, T item) {
    return;
  }


  @Override
  public U onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(layout, parent, false);

    try {
      return type.getConstructor(Context.class, View.class).newInstance(context, v);
    } catch (Exception exception) {
      return null;
    }
  }

  @Override
  public void onBindViewHolder(final U holder, final int position) {
    final T data = getItems().get(position);
    if (isClickEnabled) {
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          onItemClick(holder, data);
        }
      });
    }
    holder.populate(data, extra);
  }

  @Override
  public int getItemCount() {
    return getItems().size();
  }

  /**
   * Returns the items of the list
   *
   * @return contents
   */
  public List<T> getItems() {
    return contents;
  }

  /**
   * Sets the items of the list
   *
   * @param list the list object
   */
  public void setItems(List<T> list) {
    contents = list;
    notifyDataSetChanged();
  }

  /**
   * Adds an item into the contents
   *
   * @param item     the item to be added
   * @param position the position you wish to add item in
   */
  public void addItem(T item, int position) {
    contents.add(position, item);
    notifyItemInserted(position);
  }

  public void updateItem(T item, int position) {
    contents.remove(position);
    contents.add(position, item);
    notifyItemChanged(position);
  }

  /**
   * Adds an item into the contents
   *
   * @param item the item to be added
   */
  public void addItem(T item) {
    contents.add(item);
    notifyItemInserted(contents.size() - 1);
  }

  /**
   * Add multiple items into the contents
   *
   * @param items the list of items
   */
  public void addItems(List<T> items) {
    contents.addAll(items);
    notifyItemRangeInserted(contents.size() - items.size() - 1, items.size());
  }

  /**
   * Remove an item by value from the list of contents
   *
   * @param item the item
   */
  public void removeItem(T item) {
    int position = contents.indexOf(item);
    if (position != -1) {
      removeItem(position);
    }
  }

  /**
   * Remove all the items in the list of contents
   */
  public void clearItems() {
    contents.clear();
    notifyDataSetChanged();
  }

  /**
   * Remove an item by position from the list of contents
   *
   * @param position the position
   */
  public void removeItem(int position) {
    contents.remove(position);
    notifyItemRemoved(position);
  }

  /**
   * Get the quick variable for the Linear Layout Manager
   *
   * @return the linear layout manager
   */
  public LinearLayoutManager getLinearLayoutManager() {
    if (layoutManager == null || !(layoutManager instanceof LinearLayoutManager)) {
      layoutManager = new LinearLayoutManager(context);
    }
    return (LinearLayoutManager) layoutManager;
  }

  /**
   * Get a quick variable for the Grid Layout Manager
   *
   * @param columns the number of columns
   * @return the gird layout manager
   */
  public GridLayoutManager getGridLayoutManager(int columns) {
    if (layoutManager == null || !(layoutManager instanceof GridLayoutManager)) {
      layoutManager = new GridLayoutManager(context, columns);
    }
    return (GridLayoutManager) layoutManager;
  }

}
