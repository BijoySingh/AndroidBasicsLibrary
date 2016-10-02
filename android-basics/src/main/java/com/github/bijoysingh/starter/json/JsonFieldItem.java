package com.github.bijoysingh.starter.json;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Json Field Item
 * Created by bijoy on 1/21/16.
 */
public class JsonFieldItem {
  public JsonField.Type type;
  public String fieldName;
  public Field field;
  public Class jsonField;
  public Boolean optional;

  /**
   * JsonFieldItem Constructor
   *
   * @param annotation the annotation on the Field
   */
  public JsonFieldItem(JsonField annotation) {
    this.fieldName = annotation.field();
    this.type = annotation.type();
    this.jsonField = annotation.jsonModel();
    this.optional = annotation.isOptional();
  }

  /**
   * Set the Field name
   *
   * @param fieldName the field name
   * @return this object
   */
  public JsonFieldItem setFieldName(String fieldName) {
    if (this.fieldName.isEmpty()) {
      this.fieldName = fieldName;
    }
    return this;
  }

  /**
   * Set the Field Type
   *
   * @param classType the class type
   * @return this object
   */
  public JsonFieldItem setFieldType(Class classType) {
    if (this.type == JsonField.Type.AUTO) {
      this.type = getType(classType);
      if (this.type == JsonField.Type.UNKNOWN) {
        throw new JsonModelException(JsonModelException.ErrorResponse.UNKNOWN_AUTO.toString());
      }
    }
    if (type.equals(JsonField.Type.JSON_MODEL)
        && !JsonModel.class.isAssignableFrom(classType)) {
      throw new JsonModelException(
          JsonModelException.ErrorResponse.JSON_FIELD_WRONG_CLASS.toString());
    }

    return this;
  }

  /**
   * Set the Field
   *
   * @param field the field
   * @return this object
   */
  public JsonFieldItem setField(Field field) {
    this.field = field;
    return this;
  }

  /**
   * Gets the field from the base type
   *
   * @param classType the class type
   * @return Type
   */
  private static JsonField.Type getType(Class classType) {
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
    } else if (classType.equals(Boolean.class)) {
      return JsonField.Type.BOOLEAN;
    } else if (classType.equals(JSONObject.class)) {
      return JsonField.Type.JSON;
    } else {
      return JsonField.Type.UNKNOWN;
    }
  }

}
