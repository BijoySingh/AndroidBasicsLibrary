package com.birdlabs.basicproject.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by bijoy on 1/7/16.
 */
public class RVHolder<T> extends RecyclerView.ViewHolder {

    public RVHolder(View itemView) {
        super(itemView);
    }

    public void populate(T data) {;}
}
