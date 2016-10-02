package com.github.bijoysingh.starter.server;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.github.bijoysingh.starter.util.FileManager;

import org.json.JSONObject;

import java.util.Map;

/**
 * The network call handler
 * Created by bijoy on 10/24/15.
 */
public class DefaultQueryExecutor extends QueryExecutor {

  protected Integer timeout;
  protected Integer maxRetries;
  protected float retryBackoffMultiplier;
  protected OnQueryListener onQueryListener;
  protected AuthenticationProvider authenticationProvider;

  /**
   * Constructor for the {@link DefaultQueryExecutor}
   *
   * @param context                the activity context
   * @param timeout                the timeout amount
   * @param maxRetries             the maximinum number of retries
   * @param retryBackoffMultiplier the backoff multiplier
   * @param onQueryListener        the onQueryListener
   * @param authenticationProvider the authentication provider
   */
  private DefaultQueryExecutor(
      Context context,
      Integer timeout,
      Integer maxRetries,
      float retryBackoffMultiplier,
      @Nullable OnQueryListener onQueryListener,
      @Nullable AuthenticationProvider authenticationProvider) {
    super(context);
    this.timeout = timeout;
    this.maxRetries = maxRetries;
    this.retryBackoffMultiplier = retryBackoffMultiplier;
    this.onQueryListener = onQueryListener;
    this.authenticationProvider = authenticationProvider;
  }

  @Override
  protected void handleGetResponse(QueryParams queryParams, String response) {
    if (queryParams.getCache() != null && !queryParams.getCache().isEmpty()) {
      FileManager.write(context, queryParams.getCache(), response);
    }

    if (onQueryListener != null) {
      onQueryListener.onSuccess(queryParams, response);
    }
  }

  @Override
  protected void handleSendResponse(QueryParams queryParams, JSONObject response) {
    if (queryParams.getCache() != null && !queryParams.getCache().isEmpty()) {
      FileManager.write(context, queryParams.getCache(), response.toString());
    }

    if (onQueryListener != null) {
      onQueryListener.onSuccess(queryParams, response.toString());
    }
  }

  @Override
  protected void handleGetError(QueryParams queryParams, VolleyError error) {
    if (onQueryListener != null) {
      onQueryListener.onFail(queryParams, error);
    }
  }

  @Override
  protected void handleSendError(QueryParams queryParams, VolleyError error) {
    if (onQueryListener != null) {
      onQueryListener.onFail(queryParams, error);
    }
  }

  /**
   * The OnQueryListener interface for the the Executor
   */
  public interface OnQueryListener {
    /**
     * Called when the query executes successfully
     *
     * @param queryParams the query parameters
     * @param response    the response from the server
     */
    void onSuccess(QueryParams queryParams, String response);

    /**
     * Called when the query executes with an error
     *
     * @param queryParams the query parameters
     * @param error       the error object from the server/device
     */
    void onFail(QueryParams queryParams, VolleyError error);
  }

  /**
   * AuthenticationProvider interface for the executor
   */
  public interface AuthenticationProvider {
    /**
     * Returns the authentication data for the query
     *
     * @return the map key value pair
     */
    Map<String, String> getAuthenticationData();
  }

  @Override
  protected Map<String, String> getAuthenticationData() {
    if (authenticationProvider == null) {
      return super.getAuthenticationData();
    } else {
      return authenticationProvider.getAuthenticationData();
    }
  }

  @Override
  protected float getBackoffMultiplier() {
    return retryBackoffMultiplier;
  }

  @Override
  protected Integer getMaxRetries() {
    return maxRetries;
  }

  @Override
  protected Integer getTimeoutMillis() {
    return timeout;
  }

  /**
   * The Builder class for the DefaultExecutor
   */
  public static class Builder {
    private Context context;
    private Integer timeout;
    private Integer maxRetries;
    private float retryBackoffMultiplier;
    private OnQueryListener onQueryListener;
    private AuthenticationProvider authenticationProvider;

    /**
     * Constructor for the Builder class
     *
     * @param context the activity context
     */
    public Builder(Context context) {
      this.context = context;
      this.timeout = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
      this.maxRetries = DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
      this.retryBackoffMultiplier = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
    }

    /**
     * Set the timeout duration
     *
     * @param timeout the timeout duration
     * @return the Builder object
     */
    public Builder setTimeout(Integer timeout) {
      this.timeout = timeout;
      return this;
    }

    /**
     * Set the max number of retries
     *
     * @param maxRetries the max number of retries
     * @return the Builder object
     */
    public Builder setMaxRetries(Integer maxRetries) {
      this.maxRetries = maxRetries;
      return this;
    }

    /**
     * Set the retry backoff multuplier
     *
     * @param retryBackoffMultiplier the retry backoff multiplier
     * @return the Builder object
     */
    public Builder setRetryBackoffMultiplier(float retryBackoffMultiplier) {
      this.retryBackoffMultiplier = retryBackoffMultiplier;
      return this;
    }

    /**
     * Sets the query listener
     *
     * @param onQueryListener the query listener
     * @return the Builder object
     */
    public Builder setOnQueryListener(OnQueryListener onQueryListener) {
      this.onQueryListener = onQueryListener;
      return this;
    }

    /**
     * Sets the authentication provider
     *
     * @param provider the authentication provider object
     * @return the Builder object
     */
    public Builder setAuthenticationProvider(AuthenticationProvider provider) {
      this.authenticationProvider = provider;
      return this;
    }

    /**
     * The Builder build method
     *
     * @return the DefaultQueryExecutor object
     */
    public DefaultQueryExecutor build() {
      return new DefaultQueryExecutor(
          context,
          timeout,
          maxRetries,
          retryBackoffMultiplier,
          onQueryListener,
          authenticationProvider
      );
    }
  }
}
