package com.birdlabs.starter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by bijoy on 1/7/16.
 */
public class RVHolder<T> extends RecyclerView.ViewHolder {

    protected Context context;

    public RVHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
    }

    public void populate(T data) {
        ;
    }
}
