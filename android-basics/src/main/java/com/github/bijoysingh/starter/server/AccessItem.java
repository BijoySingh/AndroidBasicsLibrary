package com.github.bijoysingh.starter.server;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.android.volley.Request;

import java.util.Map;

/**
 * Created by bijoy on 1/1/16.
 * The access item
 */
public class AccessItem {

    public String url;
    public String filename;
    public Integer type;
    public Boolean authenticated;
    public Integer method = Request.Method.POST;
    public Activity activity;
    public Fragment fragment;
    public Map<String, Object> extra;

    public AccessItem(String url, String filename, Integer type, Boolean authenticated) {
        this.url = url;
        this.filename = filename;
        this.type = type;
        this.authenticated = authenticated;
    }

    public AccessItem setMethod(Integer method) {
        this.method = method;
        return this;
    }

    public AccessItem setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public AccessItem setFragment(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    public AccessItem addExtra(String key, Object object) {
        extra.put(key, object);
        return this;
    }
}
