package com.github.bijoysingh.starter.recyclerview;

/**
 * Item to control the item properties.
 * Created by bijoy on 6/11/17.
 */

public class MultiRecyclerViewControllerItem<T> {

  public static final int DEFAULT_VIEW_TYPE = 0;
  public static final int DEFAULT_SPAN_SIZE = 1;

  // The View Type for which the item is
  private Integer viewType = null;

  // The Snap Size of the item
  private Integer spanSize = null;

  // The Resource layout file
  private Integer layoutFile = null;

  // The Class for the Recycler View Holder
  private Class<? extends RecyclerViewHolder<T>> holderClass = null;

  private MultiRecyclerViewControllerItem(Builder<T> builder) {
    viewType = builder.viewType;
    spanSize = builder.spanSize;
    layoutFile = builder.layoutFile;
    holderClass = builder.holderClass;
  }

  public Integer getViewType() {
    return viewType;
  }

  public Integer getSpanSize() {
    return spanSize;
  }

  public Integer getLayoutFile() {
    return layoutFile;
  }

  public Class<? extends RecyclerViewHolder<T>> getHolderClass() {
    return holderClass;
  }

  public static final class Builder<U> {
    private Integer viewType;
    private Integer spanSize;
    private Integer layoutFile;
    private Class<? extends RecyclerViewHolder<U>> holderClass;

    public Builder() {
      viewType = DEFAULT_VIEW_TYPE;
      spanSize = DEFAULT_SPAN_SIZE;
    }

    public Builder<U> viewType(Integer val) {
      viewType = val;
      return this;
    }

    public Builder<U> spanSize(Integer val) {
      spanSize = val;
      return this;
    }

    public Builder<U> layoutFile(Integer val) {
      layoutFile = val;
      return this;
    }

    public Builder<U> holderClass(Class<? extends RecyclerViewHolder<U>> val) {
      holderClass = val;
      return this;
    }

    public MultiRecyclerViewControllerItem<U> build() {
      if (layoutFile == null || holderClass == null || viewType == null) {
        throw new IllegalArgumentException("Layout file cannot be null");
      }
      return new MultiRecyclerViewControllerItem<U>(this);
    }
  }
}
