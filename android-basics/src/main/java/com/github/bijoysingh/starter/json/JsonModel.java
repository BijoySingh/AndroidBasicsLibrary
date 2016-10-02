package com.github.bijoysingh.starter.json;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The JSON model base class
 * Created by bijoy on 1/21/16.
 */
public abstract class JsonModel {
  // TODO: Test this class

  List<JsonFieldItem> jsonFields;

  private JsonModel() {
  }

  /**
   * Constructor for the JsonModel
   *
   * @param source the source string
   * @throws Exception exception while creating object
   */
  public JsonModel(String source) throws Exception {
    this(new JSONObject(source));
  }

  /**
   * Constructor for the JsonModel
   *
   * @param json json object
   * @throws Exception exception while creating object
   */
  public JsonModel(JSONObject json) throws Exception {
    setFields();
    for (JsonFieldItem jsonField : jsonFields) {
      try {
        if (jsonField.type.equals(JsonField.Type.INTEGER)) {
          jsonField.field.set(this, json.getInt(jsonField.fieldName));
        } else if (jsonField.type.equals(JsonField.Type.REAL)) {
          jsonField.field.set(this, json.getDouble(jsonField.fieldName));
        } else if (jsonField.type.equals(JsonField.Type.BOOLEAN)) {
          jsonField.field.set(this, json.getBoolean(jsonField.fieldName));
        } else if (jsonField.type.equals(JsonField.Type.STRING)) {
          jsonField.field.set(this, json.getString(jsonField.fieldName));
        } else if (jsonField.type.equals(JsonField.Type.JSON)) {
          jsonField.field.set(this, json.getJSONObject(jsonField.fieldName));
        } else if (jsonField.type.equals(JsonField.Type.JSON_MODEL)) {
          jsonField.field.set(this, jsonField.jsonField.getConstructor(JSONObject.class)
              .newInstance(json.getJSONObject(jsonField.fieldName)));
        }
      } catch (JSONException exception) {
        if (jsonField.optional) {
          Log.e(JsonModel.class.getSimpleName(), exception.getMessage(), exception);
        } else {
          throw exception;
        }
      }
    }
  }

  /**
   * Create a json object from the class
   *
   * @return the JSON Object
   * @throws Exception the field not found
   */
  public JSONObject serialize() throws Exception {
    setFields();
    Map<String, Object> values = new HashMap<>();
    for (JsonFieldItem field : jsonFields) {
      if (field.type.equals(JsonField.Type.JSON_MODEL)) {
        values.put(field.fieldName, ((JsonModel) field.field.get(this)).serialize());
      } else {
        values.put(field.fieldName, field.field.get(this));
      }
    }
    return new JSONObject(values);
  }

  /**
   * Sets the fields of the JsonModel
   *
   * @throws JsonModelException Model Exception
   */
  private void setFields() throws JsonModelException {
    if (jsonFields != null) {
      return;
    }

    jsonFields = new ArrayList<>();
    Field[] classFields = getClass().getDeclaredFields();
    for (Field field : classFields) {
      JsonField annotation = field.getAnnotation(JsonField.class);
      if (annotation != null) {
        JsonFieldItem item = new JsonFieldItem(annotation)
            .setFieldName(field.getName())
            .setFieldType(field.getType())
            .setField(field);
        jsonFields.add(item);
      }
    }
  }


}
