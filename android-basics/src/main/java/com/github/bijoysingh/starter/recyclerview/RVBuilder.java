package com.github.bijoysingh.starter.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Builder
 * Created by bijoy on 10/2/16.
 */
public class RVBuilder {

  // The Activity context
  private Context context;

  // The root view
  private View root;

  // The layout manager
  private LinearLayoutManager layoutManager;

  // The Scroll listener
  private OnScrollListener onScrollListener;

  // The RV Adapter
  private RecyclerView.Adapter adapter;

  // The RV layout id
  private Integer recyclerViewId;

  // The RV
  private RecyclerView recyclerView;

  /**
   * Constructor for the RVBuilder
   *
   * @param context the activity context
   */
  public RVBuilder(Context context) {
    this.context = context;
  }

  /**
   * Sets the view of the RV
   *
   * @param root           the root view
   * @param recyclerViewId the layout id of the recycler view
   * @return this instance
   */
  public RVBuilder setView(View root, Integer recyclerViewId) {
    this.root = root;
    this.recyclerViewId = recyclerViewId;
    return this;
  }

  /**
   * Sets the RV
   *
   * @param recyclerView the recycler view
   * @return this instance
   */
  public RVBuilder setRecyclerView(RecyclerView recyclerView) {
    this.recyclerView = recyclerView;
    return this;
  }

  /**
   * Sets the recycler to linear
   *
   * @return this instance
   */
  public RVBuilder setLinear() {
    layoutManager = new LinearLayoutManager(context);
    return this;
  }

  /**
   * Sets an over scroll listener
   *
   * @param scrollListener the listner
   * @return this instance
   */
  public RVBuilder setOnScrollListener(OnScrollListener scrollListener) {
    if (layoutManager == null) {
      setLinear();
    }
    onScrollListener = scrollListener;
    return this;
  }

  /**
   * Set the adapter
   *
   * @param adapter the recycler view adapter
   * @return this instance
   */
  public RVBuilder setAdapter(RecyclerView.Adapter adapter) {
    this.adapter = adapter;
    return this;
  }

  /**
   * RVBuilder build method
   *
   * @return the recycler view
   */
  public RecyclerView build() {
    if ((root == null || recyclerViewId == null) && recyclerView == null) {
      throw new IllegalArgumentException("Cannot instantiate with null view");
    }

    if (layoutManager == null) {
      setLinear();
    }

    recyclerView = recyclerView == null
        ? (RecyclerView) root.findViewById(recyclerViewId)
        : recyclerView;
    recyclerView.setLayoutManager(layoutManager);

    if (adapter != null) {
      recyclerView.setAdapter(adapter);
    }

    recyclerView.setHasFixedSize(false);
    if (onScrollListener != null) {
      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          if (dy > 0) {
            Integer visibleItemCount = layoutManager.getChildCount();
            Integer totalItemCount = layoutManager.getItemCount();
            Integer pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
              onScrollListener.onScrollToBottom(recyclerView);
            }
          }
        }
      });
    }

    return recyclerView;
  }

  /**
   * Interface class for the Listener
   */
  public interface OnScrollListener {
    void onScrollToBottom(RecyclerView recyclerView);
  }
}
