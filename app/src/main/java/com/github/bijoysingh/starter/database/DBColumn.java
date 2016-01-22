package com.github.bijoysingh.starter.database;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by bijoy on 1/13/16.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DBColumn {

    String fieldName() default "";

    Type fieldType() default Type.DEFAULT;

    boolean primaryKey() default false;

    boolean autoIncrement() default false;

    boolean unique() default false;

    public enum Type {
        INTEGER("INTEGER"),
        TEXT("TEXT"),
        DEFAULT("DEFAULT"),
        REAL("REAL");

        private final String name;

        private Type(String s) {
            name = s;
        }

        public String toString() {
            return this.name;
        }
    }
}
