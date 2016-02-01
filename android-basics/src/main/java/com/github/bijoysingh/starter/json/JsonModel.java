package com.github.bijoysingh.starter.json;

import android.util.Log;

import com.github.bijoysingh.starter.json.JsonModelException.ErrorResponse;

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

    List<JsonFieldItem> fields;

    public JsonModel() {
    }

    public JsonModel(String response) throws Exception {
        this(new JSONObject(response));
    }

    public JsonModel(JSONObject json) throws Exception {
        setFields();
        for (JsonFieldItem field : fields) {
            if (!field.isArray) {
                /*
                Log.d(JsonModel.class.getSimpleName(), "FIELD " +
                        field.field.getName() + ":" + field.fieldName + "=>"
                        + json.get(field.fieldName));
                */

                try {

                    if (field.type.equals(JsonField.Type.INTEGER)) {
                        field.field.set(this, json.getInt(field.fieldName));
                    } else if (field.type.equals(JsonField.Type.REAL)) {
                        field.field.set(this, json.getDouble(field.fieldName));
                    } else if (field.type.equals(JsonField.Type.BOOLEAN)) {
                        field.field.set(this, json.getBoolean(field.fieldName));
                    } else if (field.type.equals(JsonField.Type.STRING)) {
                        field.field.set(this, json.getString(field.fieldName));
                    } else if (field.type.equals(JsonField.Type.JSON)) {
                        field.field.set(this, json.getJSONObject(field.fieldName));
                    } else if (field.type.equals(JsonField.Type.JSON_MODEL)) {
                        field.field.set(this, field.jsonField.getConstructor(JSONObject.class)
                                .newInstance(json.getJSONObject(field.fieldName)));
                    }
                } catch (JSONException exception) {
                    if (field.optional) {
                        Log.e(JsonModel.class.getSimpleName(), exception.getMessage(), exception);
                        continue;
                    }
                    throw exception;
                }
            } else {
                // TODO: Handle the array case
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
        for (JsonFieldItem field : fields) {
            if (!field.isArray) {
                if (field.type.equals(JsonField.Type.JSON_MODEL)) {
                    values.put(field.fieldName, ((JsonModel) field.field.get(this)).serialize());
                } else {
                    values.put(field.fieldName, field.field.get(this));
                }
            } else {
                // TODO: Handle the array case
            }
        }
        return new JSONObject(values);
    }

    public void setFields() throws JsonModelException {
        if (fields != null) {
            return;
        }

        fields = new ArrayList<>();
        Field[] classFields = getClass().getDeclaredFields();
        for (Field field : classFields) {
            JsonField annotation = field.getAnnotation(JsonField.class);
            if (annotation != null) {
                JsonFieldItem item = new JsonFieldItem(annotation);
                item.fieldName = annotation.field().isEmpty() ? field.getName() : annotation.field();
                item.type = annotation.type().equals(JsonField.Type.AUTO) ? JsonFieldItem.getType(field.getType()) : annotation.type();
                if (item.type == JsonField.Type.AUTO) {
                    throw new JsonModelException(ErrorResponse.UNKNOWN_AUTO.toString());
                }
                item.field = field;
                if (item.type.equals(JsonField.Type.JSON_MODEL)
                        && !JsonModel.class.isAssignableFrom(field.getType())) {
                    throw new JsonModelException(ErrorResponse.JSON_FIELD_WRONG_CLASS.toString());
                }
                fields.add(item);
            }
        }
    }


}
