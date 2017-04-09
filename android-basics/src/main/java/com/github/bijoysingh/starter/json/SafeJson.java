package com.github.bijoysingh.starter.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Map;

/**
 * Safe version of JSONObject
 * Created by bijoy on 4/9/17.
 */

public class SafeJson extends JSONObject {
  public SafeJson() {
  }

  public SafeJson(Map copyFrom) {
    super(copyFrom);
  }

  public SafeJson(JSONTokener readFrom) throws JSONException {
    super(readFrom);
  }

  public SafeJson(String json) throws JSONException {
    super(json);
  }

  public SafeJson(JSONObject copyFrom, String[] names) throws JSONException {
    super(copyFrom, names);
  }

  @Override
  public JSONObject getJSONObject(String name) {
    return getJSONObject(name, null);
  }

  @Override
  public JSONArray getJSONArray(String name) {
    return getJSONArray(name, null);
  }

  @Override
  public String getString(String name) {
    return getString(name, "");
  }

  @Override
  public long getLong(String name) {
    return getLong(name, 0L);
  }

  @Override
  public int getInt(String name) {
    return getInt(name, 0);
  }

  @Override
  public double getDouble(String name) {
    return getDouble(name, 0.0);
  }

  @Override
  public boolean getBoolean(String name) {
    return getBoolean(name, false);
  }

  @Override
  public Object get(String name) {
    return get(name, null);
  }


  public JSONObject getJSONObject(String name, JSONObject defaultValue) {
    try {
      return super.getJSONObject(name);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }

  public JSONArray getJSONArray(String name, JSONArray defaultValue) {
    try {
      return super.getJSONArray(name);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }

  public String getString(String name, String defaultValue) {
    try {
      return super.getString(name);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }

  public long getLong(String name, long defaultValue) {
    try {
      return super.getLong(name);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }

  public int getInt(String name, int defaultValue) {
    try {
      return super.getInt(name);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }

  public double getDouble(String name, double defaultValue) {
    try {
      return super.getDouble(name);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }

  public boolean getBoolean(String name, boolean defaultValue) {
    try {
      return super.getBoolean(name);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }

  public Object get(String name, Object defaultValue) {
    try {
      return super.get(name);
    } catch (JSONException exception) {
      return defaultValue;
    }
  }
}
