package com.github.bijoysingh.starter.server;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.android.volley.Request;

import java.util.HashMap;
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

    public AccessItem(String url) {
        this.url = url;
        this.filename = null;
        this.type = -1;
        this.authenticated = false;
    }

    public AccessItem(String url, Integer type) {
        this.url = url;
        this.filename = null;
        this.type = type;
        this.authenticated = false;
    }

    public AccessItem(String url, Boolean authenticated) {
        this.url = url;
        this.filename = null;
        this.type = -1;
        this.authenticated = authenticated;
    }

    public AccessItem(String url, Integer type, Boolean authenticated) {
        this.url = url;
        this.filename = null;
        this.type = type;
        this.authenticated = authenticated;
    }

    public AccessItem(String url, String filename, Integer type, Boolean authenticated) {
        this.url = url;
        this.filename = filename;
        this.type = type;
        this.authenticated = authenticated;
    }

    /**
     * Sets the method for the request
     *
     * @param method Use Request.Method.POST/GET/...
     * @return The AccessItem object instance
     */
    public AccessItem setMethod(Integer method) {
        this.method = method;
        return this;
    }

    /**
     * Sets the activity to be passed as instance variable
     * Note, this is not the best way wrt performance due to memory usage,
     * but is a good prototype method
     *
     * @param activity The governing activity
     * @return The AccessItem object instance
     */
    public AccessItem setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    /**
     * Sets the fragment to be passed as instance variable
     * Note, this is not the best way wrt performance due to memory usage,
     * but is a good prototype method
     *
     * @param fragment The governing fragment
     * @return The AccessItem object instance
     */
    public AccessItem setFragment(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    /**
     * Add any extra values into the object which can be later retrieved.
     *
     * @param key    the key of the object
     * @param object the object
     * @return The AccessItem object instance
     */
    public AccessItem addExtra(String key, Object object) {
        if (extra == null) {
            extra = new HashMap<>();
        }

        extra.put(key, object);
        return this;
    }

    /**
     * Get extra values
     *
     * @param key the key of the object
     * @return The object if key exists, else null
     */
    public Object getExtra(String key) {
        if (extra == null || !extra.containsKey(key)) {
            return null;
        }
        return extra.get(key);
    }
}
