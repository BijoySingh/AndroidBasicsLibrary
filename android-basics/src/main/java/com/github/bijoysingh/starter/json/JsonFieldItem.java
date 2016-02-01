package com.github.bijoysingh.starter.json;

import com.github.bijoysingh.starter.database.DBColumn;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by bijoy on 1/21/16.
 */
public class JsonFieldItem {
    public JsonField.Type type;
    public String fieldName;
    public Field field;
    public Boolean isArray;
    public Class jsonField;
    public Boolean optional;

    public JsonFieldItem(JsonField field) {
        this.fieldName = field.field();
        this.type = field.type();
        this.isArray = field.isArray();
        this.jsonField = field.jsonModel();
        this.optional = field.isOptional();
    }

    public static JsonField.Type getType(Class classType) {
        if (classType.equals(Integer.class)
                || classType.equals(Short.class)
                || classType.equals(Long.class)) {
            return JsonField.Type.INTEGER;
        } else if (classType.equals(String.class)
                || classType.equals(CharSequence.class)) {
            return JsonField.Type.STRING;
        } else if (classType.equals(Double.class)
                || classType.equals(Float.class)) {
            return JsonField.Type.REAL;
        } else if (classType.equals(Boolean.class)){
            return JsonField.Type.BOOLEAN;
        } else if (classType.equals(JSONObject.class)){
            return JsonField.Type.JSON;
        } else {
            return JsonField.Type.AUTO;
        }
    }

}
