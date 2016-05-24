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

    // Timeout value for the request
    public static final Integer TIMEOUT = 7500;

    // The application context
    public Context context;

    /**
     * AccessManager default constructor
     *
     * @param context the application context
     */
    public AccessManager(Context context) {
        this.context = context;
    }

    /**
     * Creates a volley JsonObjectRequest which will automatically call the handleSendResponse/Error
     * functions based on situations and the various parameters of the access item object
     *
     * @param access the AccessItem instance
     * @param map    the key-values to be sent
     * @return the volley request object
     */
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

    /**
     * Creates a volley StringRequest which will automatically call the handleGetResponse/Error
     * functions based on situations and the various parameters of the access item object
     *
     * @param access the AccessItem instance
     * @return the volley StringRequest object
     */
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

    /**
     * Gets the string value from the server based on the request
     *
     * @param access the AccessItem instance
     */
    public void get(final AccessItem access) {
        StringRequest request = getStringRequest(access);
        Volley.newRequestQueue(context).add(request);
    }

    /**
     * Sends a map object as a JSON object to the server
     *
     * @param access the AccessItem instance
     * @param map    the key-values to be sent
     */
    public void send(final AccessItem access,
                     final Map<String, Object> map) {
        JsonObjectRequest request = getJsonRequest(access, map);
        Volley.newRequestQueue(context).add(request);
    }

    /**
     * The header information which you wish to send when a request is authenticated
     *
     * @return the extra header key-value pairs
     */
    public abstract Map<String, String> getAuthenticationData();

    /**
     * Handler for when a get() function gets a successful response
     *
     * @param access   the AccessItem instance used in the query
     * @param response the String response from the server
     */
    public abstract void handleGetResponse(AccessItem access, String response);

    /**
     * Handler for when a send() function gets a successful response
     *
     * @param access   the AccessItem instance used in the query
     * @param response the JSON response from the server
     */
    public abstract void handleSendResponse(AccessItem access, JSONObject response);

    /**
     * Handler for when a get() function gets an error
     *
     * @param access the AccessItem instance used in the query
     * @param error  the volley error object
     */
    public abstract void handleGetError(AccessItem access, VolleyError error);

    /**
     * Handler for when a send() function gets an error
     *
     * @param access the AccessItem instance used in the query
     * @param error  the volley error object
     */
    public abstract void handleSendError(AccessItem access, VolleyError error);
}
