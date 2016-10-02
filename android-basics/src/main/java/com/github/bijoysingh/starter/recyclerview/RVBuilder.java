package com.github.bijoysingh.starter.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by bijoy on 10/2/16.
 */
public class RVBuilder {

  private Context context;
  private View root;
  private LinearLayoutManager layoutManager;
  private OnScrollListener onScrollListener;
  private RecyclerView.Adapter adapter;

  private Integer recyclerViewId;

  public RVBuilder(Context context) {
    this.context = context;
  }

  public RVBuilder setView(View root, Integer recyclerViewId) {
    this.root = root;
    this.recyclerViewId = recyclerViewId;
    return this;
  }

  public RVBuilder setLinear() {
    layoutManager = new LinearLayoutManager(context);
    return this;
  }

  public RVBuilder setOnScrollListener(OnScrollListener scrollListener) {
    if (layoutManager == null) {
      setLinear();
    }
    onScrollListener = scrollListener;
  }

  public RecyclerView setAdapter(RecyclerView.Adapter adapter) {
    this.adapter = adapter;
  }

  public RecyclerView build() {
    if (adapter == null || root == null || recyclerViewId == null) {
      throw new IllegalArgumentException("Cannot instantiate with null adapter of view");
    }

    if (layoutManager == null) {
      setLinear();
    }

    RecyclerView recyclerView = (RecyclerView) root.findViewById(recyclerViewId);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
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

  public interface OnScrollListener {
    void onScrollToBottom(RecyclerView recyclerView);
  }
}
