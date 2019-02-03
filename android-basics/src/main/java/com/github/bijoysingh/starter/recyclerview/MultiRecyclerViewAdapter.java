package com.github.bijoysingh.starter.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Recycler View which can holder multiple ViewHolders
 * Created by bijoy on 6/11/17.
 */

public abstract class MultiRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView
    .ViewHolder> {

  // The type map indicating the view type => view holder class
  protected Map<Integer, MultiRecyclerViewControllerItem<T>> controller;

  // The application/activity context
  protected Context context;

  // List of contents
  protected List<T> contents;

  // Is the click enabled
  protected boolean isClickEnabled;

  // Extra bundle to pass
  protected Bundle extra;

  // The Layout manager
  protected RecyclerView.LayoutManager layoutManager;

  public MultiRecyclerViewAdapter(
      Context context, List<MultiRecyclerViewControllerItem<T>> controllerItemList) {
    this.context = context;
    this.contents = new ArrayList<>();
    this.isClickEnabled = false;
    this.controller = new HashMap<>();
    for (MultiRecyclerViewControllerItem<T> item : controllerItemList) {
      controller.put(item.getViewType(), item);
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    MultiRecyclerViewControllerItem<T> item = controller.get(viewType);
    View v = LayoutInflater.from(parent.getContext()).inflate(item.getLayoutFile(), parent, false);
    try {
      return item.getHolderClass().getConstructor(Context.class, View.class).newInstance(context,
                                                                                         v);
    } catch (Exception exception) {
      return null;
    }
  }

  @Override
  public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
    int viewType = getItemViewType(position);
    MultiRecyclerViewControllerItem<T> item = controller.get(viewType);

    final Class<? extends RecyclerViewHolder<T>> classToCast = item.getHolderClass();
    final RecyclerViewHolder<T> castedHolder = classToCast.cast(holder);
    final T data = getItems().get(position);
    if (isClickEnabled) {
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          onItemClick(castedHolder, data);
        }
      });
    }
    castedHolder.populate(data, extra);
  }

  @Override
  public int getItemCount() {
    return getItems().size();
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
    ((GridLayoutManager) layoutManager).setSpanSizeLookup(getSpanSizeLookup());
    return (GridLayoutManager) layoutManager;
  }

  /**
   * Gets the span size lookup for the items
   *
   * @return the span size lookup
   */
  protected GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
    return new GridLayoutManager.SpanSizeLookup() {
      @Override
      public int getSpanSize(int position) {
        int viewType = getItemViewType(position);
        return controller.get(viewType).getSpanSize();
      }
    };
  }


  abstract public int getItemViewType(int position);

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
  public void onItemClick(RecyclerViewHolder<T> holder, T item) {
    return;
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

}
