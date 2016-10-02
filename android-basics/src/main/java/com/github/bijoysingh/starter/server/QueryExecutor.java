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
   * @param queryParams the AccessItem instance
   * @param map    the key-values to be sent
   * @return the volley request object
   */
  protected JsonObjectRequest getJsonRequest(final QueryParams queryParams,
                                             final Map<String, Object> map) {
    JsonObjectRequest jsonRequest = new JsonObjectRequest(
        queryParams.getMethod(Request.Method.POST),
        queryParams.getUrl(),
        new JSONObject(map),
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject json) {
            handleSendResponse(queryParams, json);
          }
        }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        handleSendError(queryParams, error);
      }
    }) {

      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        if (queryParams.getAuthenticated()) {
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
   * @param queryParams the AccessItem instance
   * @return the volley StringRequest object
   */
  protected StringRequest getStringRequest(final QueryParams queryParams) {
    StringRequest request = new StringRequest(
        queryParams.getMethod(Request.Method.GET), queryParams.getUrl(), new Response
        .Listener<String>() {
      @Override
      public void onResponse(String response) {
        handleGetResponse(queryParams, response);
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        handleGetError(queryParams, error);
      }
    }) {

      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        if (queryParams.getAuthenticated()) {
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
   * @param queryParams the AccessItem instance
   */
  public void get(final QueryParams queryParams) {
    StringRequest request = getStringRequest(queryParams);
    Volley.newRequestQueue(context).add(request);
  }

  /**
   * Sends a map object as a JSON object to the server
   *
   * @param queryParams the AccessItem instance
   * @param map    the key-values to be sent
   */
  public void send(final QueryParams queryParams,
                   final Map<String, Object> map) {
    JsonObjectRequest request = getJsonRequest(queryParams, map);
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
   * @param queryParams   the QueryParams instance used in the query
   * @param response the String response from the server
   */
  protected abstract void handleGetResponse(QueryParams queryParams, String response);

  /**
   * Handler for when a send() function gets a successful response
   *
   * @param queryParams   the QueryParams instance used in the query
   * @param response the JSON response from the server
   */
  protected abstract void handleSendResponse(QueryParams queryParams, JSONObject response);

  /**
   * Handler for when a get() function gets an error
   *
   * @param queryParams the QueryParams instance used in the query
   * @param error  the volley error object
   */
  protected abstract void handleGetError(QueryParams queryParams, VolleyError error);

  /**
   * Handler for when a send() function gets an error
   *
   * @param queryParams the QueryParams instance used in the query
   * @param error  the volley error object
   */
  protected abstract void handleSendError(QueryParams queryParams, VolleyError error);

}
