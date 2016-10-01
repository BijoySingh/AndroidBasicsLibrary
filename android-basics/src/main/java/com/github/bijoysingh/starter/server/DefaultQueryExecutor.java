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
public class DefaultQueryExecutor extends QueryExecutor {

    protected Integer timeout;
    protected Integer maxRetries;
    protected float retryBackoffMultiplier;
    protected OnQueryListener onQueryListener;
    protected AuthenticationProvider authenticationProvider;

    public DefaultQueryExecutor(
        Context context,
        Integer timeout,
        Integer maxRetries,
        float retryBackoffMultiplier,
        OnQueryListener onQueryListener,
        AuthenticationProvider authenticationProvider) {
        super(context);
        this.timeout = timeout;
        this.maxRetries = maxRetries;
        this.retryBackoffMultiplier = retryBackoffMultiplier;
        this.onQueryListener = onQueryListener;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void handleGetResponse(QueryParams access, String response) {
        if (onQueryListener != null) {
            onQueryListener.onSuccess(access, response);
        }
    }

    @Override
    protected void handleSendResponse(QueryParams access, JSONObject response) {
        if (onQueryListener != null) {
            onQueryListener.onSuccess(access, response.toString());
        }
    }

    @Override
    protected void handleGetError(QueryParams access, VolleyError error) {
        if (onQueryListener != null) {
            onQueryListener.onFail(access, error);
        }
    }

    @Override
    protected void handleSendError(QueryParams access, VolleyError error) {
        if (onQueryListener != null) {
            onQueryListener.onFail(access, error);
        }
    }

    public interface OnQueryListener {
        void onSuccess(QueryParams access, String response);
        void onFail(QueryParams access, VolleyError error);
    }

    public interface AuthenticationProvider {
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

    public static class Builder {
        private Context context;
        private Integer timeout;
        private Integer maxRetries;
        private float retryBackoffMultiplier;
        private OnQueryListener onQueryListener;
        private AuthenticationProvider authenticationProvider;

        public Builder(Context context) {
            this.context = context;
            this.timeout = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
            this.maxRetries = DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
            this.retryBackoffMultiplier = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        }

        public Builder setTimeout(Integer timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder setMaxRetries(Integer maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        public Builder setRetryBackoffMultiplier(float retryBackoffMultiplier) {
            this.retryBackoffMultiplier = retryBackoffMultiplier;
            return this;
        }

        public Builder setOnQueryListener(OnQueryListener onQueryListener) {
            this.onQueryListener = onQueryListener;
            return this;
        }

        public Builder setAuthenticationProvider(AuthenticationProvider provider) {
            this.authenticationProvider = provider;
            return this;
        }

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
