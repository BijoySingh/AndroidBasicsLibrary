package com.birdlabs.basicproject.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * The recycler view adapter for list of content
 * Created by bijoy on 1/7/16.
 */
public abstract class RVAdapter<T> extends RecyclerView.Adapter<RVHolder<T>> {

    private Integer layout;
    public List<T> contents;

    public RVAdapter(Integer layout) {
        this.layout = layout;
    }

    public abstract List<T> getValues();

    @Override
    public RVHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        return new RVHolder<T>(v);
    }

    @Override
    public void onBindViewHolder(RVHolder<T> holder, final int position) {

        final T data = getValues().get(position);
        holder.populate(data);
    }

    @Override
    public int getItemCount() {
        return getValues().size();
    }
}
