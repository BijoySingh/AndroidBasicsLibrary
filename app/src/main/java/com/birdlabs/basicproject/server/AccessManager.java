package com.birdlabs.basicproject.server;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

    Context context;

    public AccessManager(Context context) {
        this.context = context;
    }

    public void get(final AccessItem access) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET, access.url, new Response
                .Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {
                handleGetResponse(json);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleGetError(error);
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
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public void send(final AccessItem access,
                     final Map<String, Object> map) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                access.method,
                access.url,
                new JSONObject(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d(AccessManager.class.getSimpleName(), json.toString());
                        handleSendResponse(json);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleSendError(error);
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
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public abstract Map<String, String> getAuthenticationData();

    public abstract void handleGetResponse(JSONObject response);

    public abstract void handleSendResponse(JSONObject response);

    public abstract void handleGetError(VolleyError error);

    public abstract void handleSendError(VolleyError error);
}
