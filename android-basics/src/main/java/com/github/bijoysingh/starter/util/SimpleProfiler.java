package com.github.bijoysingh.starter.util;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple class to log timed content in code
 * Created by bijoy on 2/8/17.
 */

public class SimpleProfiler {

  private String tag;
  private int logMode;

  private long startMillis;
  private long lastMillis;

  public SimpleProfiler(String tag, int logMode) {
    this.tag = tag;
    this.logMode = logMode;
  }

  public static class Builder {
    private String tag;
    private int logMode;

    public Builder() {
      tag = SimpleProfiler.class.getSimpleName();
      logMode = Log.DEBUG;
    }

    public Builder setTag(String tag) {
      this.tag = tag;
      return this;
    }

    public Builder setLogMode(int logMode) {
      this.logMode = logMode;
      return this;
    }

    public SimpleProfiler build() {
      return new SimpleProfiler(tag, logMode);
    }
  }

  public void start() {
    startMillis = System.currentTimeMillis();
    lastMillis = startMillis;
  }

  public void capture() {
    long millis = System.currentTimeMillis();
    logEvent("null", millis);
    lastMillis = millis;
  }

  public void capture(String tag) {
    long millis = System.currentTimeMillis();
    logEvent(tag, millis);
    lastMillis = millis;
  }

  private void logEvent(String event, long millis) {
    Map<String, Object> log = new HashMap<>();
    log.put("event", event);
    log.put("system_time(ms)", millis);
    log.put("since_start(ms)", millis - startMillis);
    log.put("since_last(ms)", millis - lastMillis);
    Log.println(logMode, tag, new JSONObject(log).toString());
  }
}
