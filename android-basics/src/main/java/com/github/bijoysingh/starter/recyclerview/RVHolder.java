package com.github.bijoysingh.starter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Parent Class for the Recycler View holders
 * <p>
 * Created by bijoy on 1/7/16.
 */
public class RVHolder<T> extends RecyclerView.ViewHolder {

    // The Context object
    protected Context context;

    /**
     * Constructor for the recycler view holder
     *
     * @param context  the application/activity context
     * @param itemView the view of the current item
     */
    public RVHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
    }

    /**
     * Populate the recycler view with the data
     *
     * @param data the Item value
     */
    public void populate(T data) {
        ;
    }

    /**
     * Returns the context object
     *
     * @return the application/activity context
     */
    public Context getContext() {
        return context;
    }
}
