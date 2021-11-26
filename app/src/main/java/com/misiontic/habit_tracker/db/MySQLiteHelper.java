package com.misiontic.habit_tracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="app_05_db.sqlite";
    private static final int DB_VERSION=1;

    private static final String HABITS_TABLE_CREATE="CREATE TABLE habits(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                    "name TEXT, description TEXT, category TEXT)";

    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HABITS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String sentence){
        SQLiteDatabase db= getWritableDatabase();
        db.execSQL(sentence);
    }
}
