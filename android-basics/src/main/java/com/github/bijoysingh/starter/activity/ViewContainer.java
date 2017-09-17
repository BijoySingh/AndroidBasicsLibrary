package com.github.bijoysingh.starter.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * An interface which contains view finder methods
 * Created by bijoy on 9/17/17.
 */

public interface ViewContainer {

  View findView(int id);

}
