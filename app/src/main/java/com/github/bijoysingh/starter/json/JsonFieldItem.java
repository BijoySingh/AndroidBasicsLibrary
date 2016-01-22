package com.github.bijoysingh.starter.json;

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

    public JsonFieldItem(JsonField field) {
        this.fieldName = field.field();
        this.type = field.type();
        this.isArray = field.isArray();
        this.jsonField = field.jsonModel();
    }

}
