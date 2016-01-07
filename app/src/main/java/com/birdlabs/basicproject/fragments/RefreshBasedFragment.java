package com.birdlabs.basicproject.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.birdlabs.basicproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for recycler view and allowing refresh easily
 * Created by bijoy on 1/1/16.
 */
public abstract class RefreshBasedFragment<T> extends Fragment {

    public List<T> values = new ArrayList<>();
    public Context context;
    public RecyclerView.LayoutManager layoutManager;
    public RecyclerView recyclerView;
    public SwipeRefreshLayout swipeRefreshLayout;

    public void rerenderList(List<T> list) {
        ;
    }

    public void addItems(List<T> list) {
        ;
    }

    public void removeItem(int position) {
        values.remove(position);
    }

    public void stopRefreshIndicator() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void initializeRecyclerView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        swipeRefreshLayout.setEnabled(true);
    }

    public void refreshList() {
        ;
    }

    public void clearList() {
        ;
    }

    public boolean renderOfflineList() {
        return false;
    }

    public List<T> getValues() {
        return values;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
}

