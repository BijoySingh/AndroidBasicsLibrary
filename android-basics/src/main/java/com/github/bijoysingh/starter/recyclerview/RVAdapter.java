package com.github.bijoysingh.starter.recyclerview;

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
public abstract class RVAdapter<T, U extends RVHolder<T>> extends RecyclerView.Adapter<U> {

    protected Context context;
    protected Integer layout;
    protected List<T> contents;
    protected Class<U> type;

    public RVAdapter(Context context, Integer layout, Class<U> type) {
        this.context = context;
        this.layout = layout;
        this.type = type;
    }

    public abstract List<T> getValues();

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
    public void onBindViewHolder(U holder, final int position) {
        final T data = getValues().get(position);
        holder.populate(data);
    }

    @Override
    public int getItemCount() {
        return getValues().size();
    }
}
