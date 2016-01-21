package com.birdlabs.starter.json;

/**
 * Json Field Annotation Inteface
 * Created by bijoy on 1/21/16.
 */
public @interface JsonField {
    enum Type {
        INTEGER("INTEGER"),
        TIMESTAMP("TIMESTAMP"),
        JSON("JSON"),
        STRING("STRING"),
        REAL("REAL");

        private final String name;

        private Type(String s) {
            name = s;
        }

        public String toString() {
            return this.name;
        }
    }

    String field() default "";

    /**
     * Is the object an array of the type
     *
     * @return is the object an array?
     */
    boolean isArray() default false;

    /**
     * In case the object is a JSON and you want to map it to an object,
     * this is the class which extends JsonModel into which the object should go.
     *
     * @return The JsonModel extending class
     */
    Class jsonModel() default Object.class;

}
