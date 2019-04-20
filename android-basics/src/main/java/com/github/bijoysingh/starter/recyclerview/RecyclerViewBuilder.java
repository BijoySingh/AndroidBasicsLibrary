package com.github.bijoysingh.starter.recyclerview;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Builder
 * Created by bijoy on 10/2/16.
 */
public class RecyclerViewBuilder {

  // The Activity context
  private Context context;

  // The layout manager
  private RecyclerView.LayoutManager layoutManager;

  // The Scroll listener
  private OnScrollListener onScrollListener;

  // The RV Adapter
  private RecyclerView.Adapter adapter;

  // The RV
  private RecyclerView recyclerView;

  /**
   * Constructor for the RVBuilder
   *
   * @param context the activity context
   */
  public RecyclerViewBuilder(Context context) {
    this.context = context;
  }

  /**
   * Sets the view of the RV
   *
   * @param root           the root view
   * @param recyclerViewId the layout id of the recycler view
   * @return this instance
   */
  public RecyclerViewBuilder setView(View root, Integer recyclerViewId) {
    this.recyclerView = (RecyclerView) root.findViewById(recyclerViewId);
    return this;
  }


  /**
   * Sets the view of the RV
   *
   * @param activity       the activity which contains the view
   * @param recyclerViewId the layout id of the recycler view
   * @return this instance
   */
  public RecyclerViewBuilder setView(Activity activity, Integer recyclerViewId) {
    this.recyclerView = (RecyclerView) activity.findViewById(recyclerViewId);
    return this;
  }

  /**
   * Sets the RV
   *
   * @param recyclerView the recycler view
   * @return this instance
   */
  public RecyclerViewBuilder setRecyclerView(RecyclerView recyclerView) {
    this.recyclerView = recyclerView;
    return this;
  }

  /**
   * Sets the recycler to linear
   *
   * @return this instance
   */
  public RecyclerViewBuilder setLinear() {
    layoutManager = new LinearLayoutManager(context);
    return this;
  }

  public RecyclerViewBuilder setLayoutManager(RecyclerView.LayoutManager manager) {
    layoutManager = manager;
    return this;
  }

  /**
   * Sets an over scroll listener
   *
   * @param scrollListener the listner
   * @return this instance
   */
  public RecyclerViewBuilder setOnScrollListener(OnScrollListener scrollListener) {
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
  public RecyclerViewBuilder setAdapter(RecyclerView.Adapter adapter) {
    this.adapter = adapter;
    return this;
  }

  /**
   * RVBuilder build method
   *
   * @return the recycler view
   */
  public RecyclerView build() {
    if (recyclerView == null) {
      throw new IllegalArgumentException("Cannot instantiate with null view");
    }

    if (layoutManager == null) {
      setLinear();
    }

    recyclerView.setLayoutManager(layoutManager);

    if (adapter != null) {
      recyclerView.setAdapter(adapter);
    }

    recyclerView.setHasFixedSize(false);
    if (onScrollListener != null && layoutManager instanceof LinearLayoutManager) {
      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          if (dy > 0) {
            Integer visibleItemCount = layoutManager.getChildCount();
            Integer totalItemCount = layoutManager.getItemCount();
            Integer pastVisiblesItems = ((LinearLayoutManager) layoutManager)
                .findFirstVisibleItemPosition();

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
