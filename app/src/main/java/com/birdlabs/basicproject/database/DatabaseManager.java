package com.birdlabs.basicproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The database maanger
 * Created by bijoy on 1/13/16.
 */
public abstract class DatabaseManager extends SQLiteOpenHelper {

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

    public void add(DatabaseModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = model.getValues();
        db.insert(model.getTableName(), null, values);
        db.close();
    }

}
