package com.github.bijoysingh.starter.server;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * The network call handler
 * Created by bijoy on 10/24/15.
 */
public abstract class AccessManager {

    public static final Integer TIMEOUT = 7500;

    public Context context;

    public AccessManager(Context context) {
        this.context = context;
    }

    public JsonObjectRequest getJsonRequest(final AccessItem access,
                                            final Map<String, Object> map) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
            access.method,
            access.url,
            new JSONObject(map),
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject json) {
                    handleSendResponse(access, json);
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleSendError(access, error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (access.authenticated) {
                    params.putAll(getAuthenticationData());
                }
                return params;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
            TIMEOUT,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return jsonRequest;
    }

    public StringRequest getStringRequest(final AccessItem access) {
        StringRequest request = new StringRequest(
            Request.Method.GET, access.url, new Response
            .Listener<String>() {
            @Override
            public void onResponse(String response) {
                handleGetResponse(access, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleGetError(access, error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (access.authenticated) {
                    params.putAll(getAuthenticationData());
                }
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
            TIMEOUT,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return request;
    }

    public void get(final AccessItem access) {
        StringRequest request = getStringRequest(access);
        Volley.newRequestQueue(context).add(request);
    }

    public void send(final AccessItem access,
                     final Map<String, Object> map) {
        JsonObjectRequest request = getJsonRequest(access, map);
        Volley.newRequestQueue(context).add(request);
    }

    public abstract Map<String, String> getAuthenticationData();

    public abstract void handleGetResponse(AccessItem access, String response);

    public abstract void handleSendResponse(AccessItem access, JSONObject response);

    public abstract void handleGetError(AccessItem access, VolleyError error);

    public abstract void handleSendError(AccessItem access, VolleyError error);
}
