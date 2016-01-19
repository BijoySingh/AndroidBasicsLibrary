package com.birdlabs.starter.database;

import android.content.ContentValues;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * The model base class for Database
 * Created by bijoy on 1/13/16.
 */
public abstract class DatabaseModel {

    List<DatabaseColumn> keys = null;

    public enum ErrorResponse {
        OK("OK"),
        MULTIPLE_PRIMARY_KEYS("Multiple Primary Keys Defined"),
        NON_INTEGER_AUTO_INCREMENT_PRIMARY_KEY("Autoincrement must a Integer Key"),
        AUTO_INCREMENT_NOT_PRIMARY("Auto Incremented Key must be Primary");

        private final String errorMessage;

        ErrorResponse(String s) {
            errorMessage = s;
        }

        public String toString() {
            return this.errorMessage;
        }
    }


    public DatabaseModel() {
    }

    public String getTableName() {
        return getClass().getName().replace(".", "_");
    }

    public List<DatabaseColumn> getKeys() {
        setKeys();
        return keys;
    }

    public DatabaseColumn getGeneratePrimaryKey() {
        return new DatabaseColumn("primary_key", DBColumn.Type.INTEGER, true, true, false);
    }

    public ErrorResponse isValid(List<DatabaseColumn> keys) {
        Boolean hasPrimary = false;
        for (DatabaseColumn key : keys) {
            if (key.primaryKey && hasPrimary) {
                return ErrorResponse.MULTIPLE_PRIMARY_KEYS;
            } else if (key.primaryKey && key.autoIncrement && !key.fieldType.equals(DBColumn.Type.INTEGER)) {
                return ErrorResponse.NON_INTEGER_AUTO_INCREMENT_PRIMARY_KEY;
            } else if (key.primaryKey) {
                hasPrimary = true;
            } else if (key.autoIncrement) {
                return ErrorResponse.AUTO_INCREMENT_NOT_PRIMARY;
            }
        }
        return ErrorResponse.OK;
    }

    public Boolean hasPrimaryKey(List<DatabaseColumn> keys) {
        for (DatabaseColumn key : keys) {
            if (key.primaryKey) {
                return true;
            }
        }
        return false;
    }

    public String createTable() throws DatabaseException {
        setKeys();

        ErrorResponse validity = isValid(keys);
        if (!validity.equals(ErrorResponse.OK)) {
            throw new DatabaseException(validity.toString());
        }

        if (!hasPrimaryKey(keys)) {
            keys.add(getGeneratePrimaryKey());
        }

        String sql = "CREATE TABLE " + getTableName() + "(";
        Integer position = 0;
        for (DatabaseColumn key : keys) {
            sql += key.getCreateQuery();
            position++;
            if (position != keys.size()) {
                sql += ",";
            }
        }
        sql += ");";
        return sql;
    }

    public void setKeys() {
        if (keys != null) {
            return;
        }

        keys = new ArrayList<>();
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            DBColumn annotation = field.getAnnotation(DBColumn.class);
            DatabaseColumn column = new DatabaseColumn(annotation);
            column.fieldName = annotation.fieldName().isEmpty() ? field.getName() : annotation.fieldName();
            column.fieldType = annotation.fieldType().equals(DBColumn.Type.DEFAULT) ? DatabaseColumn.getType(field.getType()) : annotation.fieldType();
            column.field = field;
            keys.add(column);
        }
    }

    public ContentValues getValues() {
        setKeys();

        ContentValues values = new ContentValues();
        for (DatabaseColumn column: keys) {
            column.field.setAccessible(true);
            try {
                if (column.fieldType.equals(DBColumn.Type.INTEGER)) {
                    values.put(column.fieldName, (Integer) column.field.get(this));
                } else if (column.fieldType.equals(DBColumn.Type.TEXT)) {
                    values.put(column.fieldName, (String) column.field.get(this));
                } else {
                    values.put(column.fieldName, (Double) column.field.get(this));
                }
            } catch (IllegalAccessException exception) {
                Log.e(DatabaseModel.class.getSimpleName(), exception.getMessage(), exception);
            }
        }
        return values;
    }
}
