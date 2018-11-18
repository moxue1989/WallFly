package com.example.mo.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbConnection extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_INSERT_ENTRY = new StringBuilder("INSERT INTO ")
            .append(user_schema.columns.TABLE_NAME)
            .append(" (")
            .append(user_schema.columns.COLUMN_USERNAME)
            .append(",")
            .append(user_schema.columns.COLUMN_PASSWORD_HASHED)
            .append(") VALUES (?,?)").toString();

    private static final String SQL_CREATE_ENTRIES = new StringBuilder("CREATE TABLE ")
            .append(user_schema.columns.TABLE_NAME)
            .append(" (")
            .append(user_schema.columns._ID)
            .append(" INTEGER PRIMARY KEY,")
            .append(user_schema.columns.COLUMN_USERNAME)
            .append(" TEXT,")
            .append(user_schema.columns.COLUMN_PASSWORD_HASHED)
            .append(" TEXT)").toString();

    private static final String SQL_DELETE_ENTRIES = new StringBuilder("DROP TABLE IF EXISTS ")
            .append(user_schema.columns.TABLE_NAME).toString();


    public DbConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
