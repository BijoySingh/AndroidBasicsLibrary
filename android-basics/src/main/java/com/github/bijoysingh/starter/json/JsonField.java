package com.github.bijoysingh.starter.json;

/**
 * Json Field Annotation Interface
 * Created by bijoy on 1/21/16.
 */
public @interface JsonField {
  /**
   * The Datatype of the json item
   *
   * @return the type
   */
  Type type() default Type.AUTO;

  /**
   * The Json Field Name
   *
   * @return the name
   */
  String field() default "";

  /**
   * Is the object optional
   *
   * @return is the object optional
   */
  boolean isOptional() default false;

  /**
   * In case the object is a JSON and you want to map it to an object,
   * this is the class which extends JsonModel into which the object should go.
   *
   * @return The JsonModel extending class
   */
  Class jsonModel() default Object.class;

  enum Type {
    AUTO("AUTO"),
    BOOLEAN("BOOLEAN"),
    INTEGER("INTEGER"),
    JSON("JSON"),
    JSON_MODEL("JSON_MODEL"),
    STRING("STRING"),
    REAL("REAL"),
    UNKNOWN("UNKNOWN");

    private final String name;

    private Type(String s) {
      name = s;
    }

    public String toString() {
      return this.name;
    }
  }

}
