package com.birdlabs.basicproject.server;

import com.android.volley.Request;

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
}
