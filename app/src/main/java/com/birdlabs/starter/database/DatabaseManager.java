package com.birdlabs.starter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * The database maanger
 * Created by bijoy on 1/13/16.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    Context context;
    DatabaseModel[] models;

    // The database version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "database";

    public DatabaseManager(Context context, DatabaseModel[] models) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.models = models;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for (DatabaseModel model : models) {
            String sql = model.createTable();
            sqLiteDatabase.execSQL(sql);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        for (DatabaseModel model : models) {
            db.execSQL("DROP TABLE IF EXISTS " + model.getTableName());
        }
        onCreate(db);
    }

    public <T extends DatabaseModel> void add(T model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = model.getValues();
        long returnCode = db.insert(model.getTableName(), null, values);
        Log.d(DatabaseManager.class.getSimpleName(), "Row added at " + returnCode + " of " + model.getTableName());
        db.close();
    }

    public <T extends DatabaseModel> List<T> get(Class<T> modelClass) {
        List<T> values = new ArrayList<>();

        try {
            T sampleModel = modelClass.newInstance();
            SQLiteDatabase db = this.getReadableDatabase();

            String sqlQuery = "SELECT *" + " FROM `" + sampleModel.getTableName() + "`";
            Log.d(DatabaseManager.class.getSimpleName(), sqlQuery);

            Cursor cursor = db.rawQuery(sqlQuery, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    List<DatabaseColumn> keys = sampleModel.getKeys();
                    T model = modelClass.newInstance();
                    Integer position = 0;
                    for (DatabaseColumn column : keys) {
                        if (column.fieldType.equals(DBColumn.Type.INTEGER)) {
                            column.field.set(model, cursor.getInt(position));
                        } else if (column.fieldType.equals(DBColumn.Type.TEXT)) {
                            column.field.set(model, cursor.getString(position));
                        } else if (column.fieldType.equals(DBColumn.Type.REAL)) {
                            column.field.set(model, cursor.getDouble(position));
                        }
                        position += 1;
                    }
                    values.add(model);
                }
                cursor.close();
            } else {
                Log.d(DatabaseManager.class.getSimpleName(), "Null cursor returned");
            }
        } catch (Exception exception) {
            Log.e(DatabaseManager.class.getSimpleName(), exception.getMessage(), exception);
            return values;
        }
        return values;
    }

}
