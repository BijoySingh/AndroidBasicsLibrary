package com.birdlabs.starter.database;

import java.lang.reflect.Field;

/**
 * Created by bijoy on 1/13/16.
 */
public class DatabaseColumn {
    public String fieldName;
    public DBColumn.Type fieldType;
    public Boolean primaryKey;
    public Boolean autoIncrement;
    public Boolean unique;
    public Field field;

    public DatabaseColumn(DBColumn column) {
        fieldName = column.fieldName();
        fieldType = column.fieldType();
        primaryKey = column.primaryKey();
        autoIncrement = column.autoIncrement();
        unique = column.unique();
    }

    public DatabaseColumn(String fieldName, DBColumn.Type fieldType, Boolean primaryKey, Boolean autoIncrement, Boolean unique) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.primaryKey = primaryKey;
        this.autoIncrement = autoIncrement;
        this.unique = unique;
    }

    public String getCreateQuery() {
        String sql = fieldName + " " + fieldType.toString();
        if (primaryKey) {
            sql += " PRIMARY KEY";
        }
        if (autoIncrement) {
            sql += " AUTOINCREMENT";
        }
        if (unique) {
            sql += " UNIQUE";
        }
        return sql;
    }

    public static DBColumn.Type getType(Class classType) {
        if (classType.equals(Integer.class)
                || classType.equals(Short.class)
                || classType.equals(Long.class)) {
            return DBColumn.Type.INTEGER;
        } else if (classType.equals(String.class)
                || classType.equals(Character.class)
                || classType.equals(CharSequence.class)) {
            return DBColumn.Type.TEXT;
        } else if (classType.equals(Double.class)
                || classType.equals(Float.class)) {
            return DBColumn.Type.REAL;
        } else {
            return DBColumn.Type.TEXT;
        }
    }
}
