package com.github.bijoysingh.starter.server;

import android.app.Activity;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bijoy on 1/1/16.
 * The access item
 */
public class QueryParams {

  private final String url;
  private final String cache;
  private final Integer queryIdentifier;
  private final Boolean isAuthenticated;
  private final Integer method;
  private final Activity activity;
  private final Fragment fragment;
  private final Map<String, Object> extra;

  /**
   * The private QueryParam constructor
   *
   * @param url             the url of the query
   * @param cache           optional cache filename
   * @param queryIdentifier a query type identifier to handle distictly
   * @param isAuthenticated is the query authenticated
   * @param method          the method to send the query. e.g. Request.Method.POST
   * @param activity        the activity
   * @param fragment        the fragment
   * @param extra           extra variables
   */
  private QueryParams(String url,
                      @Nullable String cache,
                      Integer queryIdentifier,
                      Boolean isAuthenticated,
                      @Nullable Integer method,
                      @Nullable Activity activity,
                      @Nullable Fragment fragment,
                      Map<String, Object> extra) {
    this.url = url;
    this.cache = cache;
    this.queryIdentifier = queryIdentifier;
    this.isAuthenticated = isAuthenticated;
    this.method = method;
    this.activity = activity;
    this.fragment = fragment;
    this.extra = extra;
  }

  /**
   * Builder class for the {@link QueryParams} class
   */
  public static class Builder {
    private String url;
    private String cache;
    private Integer queryIdentifier;
    private Boolean isAuthenticated;
    private Integer method;
    private Activity activity;
    private Fragment fragment;
    private Map<String, Object> extra;

    /**
     * The constructor for the Builder class
     *
     * @param url the query url
     */
    public Builder(String url) {
      this.url = url;
      cache = null;
      queryIdentifier = -1;
      isAuthenticated = false;
      method = null;
      activity = null;
      fragment = null;
      extra = new HashMap<>();
    }

    /**
     * Set the cache filename
     *
     * @param cacheFilename cache filename
     * @return Builder
     */
    public Builder setCache(String cacheFilename) {
      this.cache = cacheFilename;
      return this;
    }

    /**
     * Set the query identifier
     *
     * @param queryIdentifier unique id to identify the type of the query
     * @return Builder
     */
    public Builder setQueryIdentifier(Integer queryIdentifier) {
      this.queryIdentifier = queryIdentifier;
      return this;
    }

    /**
     * Set if the query is authenticated
     *
     * @param authenticated is authenticated
     * @return Builder
     */
    public Builder setAuthenticated(Boolean authenticated) {
      isAuthenticated = authenticated;
      return this;
    }

    /**
     * Set the query method
     *
     * @param method query method Request.Method.POST, etc.
     * @return Builder
     */
    public Builder setMethod(Integer method) {
      this.method = method;
      return this;
    }

    /**
     * Set activity in query
     *
     * @param activity activity making request
     * @return Builder
     */
    public Builder setActivity(Activity activity) {
      this.activity = activity;
      return this;
    }

    /**
     * Set fragment in query
     *
     * @param fragment fragment making request
     * @return Builder
     */
    public Builder setFragment(Fragment fragment) {
      this.fragment = fragment;
      return this;
    }

    /**
     * Set extras to be passed
     *
     * @param extra extras
     * @return Builder
     */
    public Builder setExtra(Map<String, Object> extra) {
      this.extra = extra;
      return this;
    }

    /**
     * Add extra to be passed
     *
     * @param key   key
     * @param value value
     * @return Builder
     */
    public Builder addExtra(String key, Object value) {
      this.extra.put(key, value);
      return this;
    }

    /**
     * Build the Query Param object
     *
     * @return object
     */
    public QueryParams build() {
      return new QueryParams(url, cache, queryIdentifier, isAuthenticated, method, activity,
                             fragment, extra);
    }

  }

  /**
   * Get the url of the query
   *
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Get the cache filename of the query
   *
   * @return the cache file
   */
  public String getCache() {
    return cache;
  }

  /**
   * Get the query identifier
   *
   * @return the query id
   */
  public Integer getQueryIdentifier() {
    return queryIdentifier;
  }

  /**
   * Gets if the query is authenticated
   *
   * @return is query authenticated
   */
  public Boolean getAuthenticated() {
    return isAuthenticated;
  }

  /**
   * Get the query method
   *
   * @return the method
   */
  public Integer getMethod(Integer defaultMethod) {
    return method == null ? defaultMethod : method;
  }

  /**
   * Get the activity of the query
   *
   * @return the activity
   */
  public Activity getActivity() {
    return activity;
  }

  /**
   * Get the fragment of the query
   *
   * @return the fragment
   */
  public Fragment getFragment() {
    return fragment;
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
