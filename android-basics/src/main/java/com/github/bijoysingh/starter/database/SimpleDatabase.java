package com.github.bijoysingh.starter.database;

import android.content.Context;

import com.github.bijoysingh.starter.util.FileManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple database
 * Created by bijoy on 4/10/17.
 */

public abstract class SimpleDatabase<M> {

  protected static final String MODELS_KEY = "models";

  protected Context context;
  private Comparator<M> comparator;

  public SimpleDatabase(Context context) {
    this.context = context;
    comparator = new Comparator<M>() {
      @Override
      public int compare(M model1, M model2) {
        return compareModels(model1, model2);
      }
    };
  }

  protected abstract String getDatabaseFilename();

  protected abstract void setCacheList(List<M> list);

  protected abstract List<M> getCacheList();

  protected abstract String getId(M object);

  protected abstract JSONObject serialise(M object);

  protected abstract M deSerialise(JSONObject serialised);

  protected abstract int compareModels(M model1, M model2);

  public void addOrUpdate(M object) {
    List<M> models = getList();
    boolean replaceModel = false;
    int index = 0;
    for (M model : models) {
      if (getId(model).equals(getId(object))) {
        replaceModel = true;
        break;
      }
      index++;
    }

    if (replaceModel) {
      models.set(index, object);
    } else {
      models.add(object);
    }

    setSortedCacheList(models);
    setAllIntoFile(models);
  }

  public void delete(M object) {
    delete(getId(object));
  }

  public void delete(String id) {
    List<M> models = getList();
    boolean replaceModel = false;
    int index = 0;
    for (M model : models) {
      if (getId(model).equals(id)) {
        replaceModel = true;
        break;
      }
      index++;
    }

    if (replaceModel) {
      models.remove(index);
      setSortedCacheList(models);
      setAllIntoFile(models);
    }
  }

  public void deleteAll() {
    List<M> models = new ArrayList<>();
    setSortedCacheList(models);
    setAllIntoFile(models);
  }

  public M get(String id) {
    for (M model : getList()) {
      if (getId(model).equals(id)) {
        return model;
      }
    }
    return null;
  }

  public List<M> getAll() {
    return getList();
  }

  private List<M> getList() {
    List<M> list = getCacheList();
    if (list == null) {
      list = getAllFromFile();
      list = setSortedCacheList(list);
    }
    return list;
  }

  private List<M> getAllFromFile() {
    List<M> models = new ArrayList<>();
    String read = FileManager.read(context, getDatabaseFilename());
    try {
      JSONObject json = new JSONObject(read);
      JSONArray array = json.getJSONArray(MODELS_KEY);
      for (int index = 0; index < array.length(); index++) {
        try {
          models.add(deSerialise(array.getJSONObject(index)));
        } catch (JSONException innerException) {
          // Ignore for maximum extraction
        }
      }
    } catch (JSONException exception) {
      // Do nothing
    }
    return models;
  }

  private void setAllIntoFile(List<M> models) {
    JSONArray array = new JSONArray();
    for (M model : models) {
      array.put(serialise(model));
    }

    Map<String, Object> cache = new HashMap<>();
    cache.put(MODELS_KEY, array);
    FileManager.writeAsync(
        context,
        getDatabaseFilename(),
        new JSONObject(cache).toString());
  }

  private List<M> setSortedCacheList(List<M> list) {
    Collections.sort(list, comparator);
    setCacheList(list);
    return list;
  }
}
