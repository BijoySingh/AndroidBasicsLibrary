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
public abstract class QueryExecutor {

    // The application context
    protected Context context;

    protected QueryExecutor(Context context) {
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
    protected JsonObjectRequest getJsonRequest(final QueryParams access,
                                            final Map<String, Object> map) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
            access.getMethod(),
            access.getUrl(),
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
                if (access.getAuthenticated()) {
                    params.putAll(getAuthenticationData());
                }
                return params;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
            getTimeoutMillis(),
            getMaxRetries(),
            getBackoffMultiplier()));

        return jsonRequest;
    }

    /**
     * Creates a volley StringRequest which will automatically call the handleGetResponse/Error
     * functions based on situations and the various parameters of the access item object
     *
     * @param access the AccessItem instance
     * @return the volley StringRequest object
     */
    protected StringRequest getStringRequest(final QueryParams access) {
        StringRequest request = new StringRequest(
            Request.Method.GET, access.getUrl(), new Response
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
                if (access.getAuthenticated()) {
                    params.putAll(getAuthenticationData());
                }
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
            getTimeoutMillis(),
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return request;
    }

    /**
     * @return Timeout milliseconds
     */
    protected Integer getTimeoutMillis() {
        return DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
    }

    /**
     * @return Maximum number of retries
     */
    protected Integer getMaxRetries() {
        return DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
    }

    /**
     * @return Backoff multiplier
     */
    protected float getBackoffMultiplier() {
        return DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
    }

    /**
     * Gets the string value from the server based on the request
     *
     * @param access the AccessItem instance
     */
    public void get(final QueryParams access) {
        StringRequest request = getStringRequest(access);
        Volley.newRequestQueue(context).add(request);
    }

    /**
     * Sends a map object as a JSON object to the server
     *
     * @param access the AccessItem instance
     * @param map    the key-values to be sent
     */
    public void send(final QueryParams access,
                     final Map<String, Object> map) {
        JsonObjectRequest request = getJsonRequest(access, map);
        Volley.newRequestQueue(context).add(request);
    }

    /**
     * The header information which you wish to send when a request is authenticated
     *
     * @return the extra header key-value pairs
     */
    protected Map<String, String> getAuthenticationData() {
        return new HashMap<>();
    }


    /**
     * Handler for when a get() function gets a successful response
     *
     * @param access   the QueryParams instance used in the query
     * @param response the String response from the server
     */
    protected abstract void handleGetResponse(QueryParams access, String response);

    /**
     * Handler for when a send() function gets a successful response
     *
     * @param access   the QueryParams instance used in the query
     * @param response the JSON response from the server
     */
    protected abstract void handleSendResponse(QueryParams access, JSONObject response);

    /**
     * Handler for when a get() function gets an error
     *
     * @param access the QueryParams instance used in the query
     * @param error  the volley error object
     */
    protected abstract void handleGetError(QueryParams access, VolleyError error);

    /**
     * Handler for when a send() function gets an error
     *
     * @param access the QueryParams instance used in the query
     * @param error  the volley error object
     */
    protected abstract void handleSendError(QueryParams access, VolleyError error);

}
