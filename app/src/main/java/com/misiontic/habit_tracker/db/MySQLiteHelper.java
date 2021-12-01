package com.misiontic.habit_tracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

import com.misiontic.habit_tracker.db.DatesHabits.Date;
import com.misiontic.habit_tracker.db.DatesHabits.Habit;


public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="app_05_db.sqlite";

    private static final int DB_VERSION=5;

    private final Context context;


    interface Tables{
        String HABIT="habits";
        String DATE="dates";
    }

    interface References{
        String ID_HABIT=String.format("REFERENCES %s(%s) ON DELETE CASCADE", Tables.HABIT, Habit.ID);
        String ID_DATE=String.format("REFERENCES %s(%s)",Tables.DATE,Date.ID); //No se requeriría
    }

    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    //Básico una sola tabla
    /*
    private static final String HABITS_TABLE_CREATE="CREATE TABLE habits(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                    "name TEXT, description TEXT, category TEXT)";
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        /* Con interfaces y Strinf.format
        /db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT,%s TEXT NOT NULL %s)",
                Tables.HABIT, BaseColumns._ID,
                DatesHabits.ColumnsHabits.NAME,DatesHabits.ColumnsHabits.DESCRIPTION,
                DatesHabits.ColumnsHabits.CATEGORY));

        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s DATETIME,%s TEXT NOT NULL %s)",
                Tables.DATE, BaseColumns._ID,
                DatesHabits.ColumnsDates.DATE,
                DatesHabits.ColumnsDates.ID_HABIT,References.ID_HABIT));
        */
        db.execSQL("CREATE TABLE habits(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, description TEXT, category TEXT)");
        db.execSQL("CREATE TABLE dates(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date DATETIME, id_habit INTEGER)");
    }

    //Básico una sola tabla
    /*
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HABITS_TABLE_CREATE);
    }
    */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.HABIT);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.DATE);
        onCreate(db);
    }

    public boolean insertData(String sentence){
        boolean success=false;
        try {
            SQLiteDatabase db= getWritableDatabase();
            db.execSQL(sentence);
            success=true;
        } catch (Exception e){
            success=false;
        }
        return success;
    }

    public Cursor getData(String sentence, String[] params){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cu=db.rawQuery(sentence, params);
        return cu;

    }

    public int deleteData(String table, String whereClause, String[] params) {
        SQLiteDatabase db = getWritableDatabase();
        int nRows = db.delete(table, whereClause, params);
        return nRows;
    }

    public int updateData(String table, ContentValues cv, String whereClause, String[] params) {
        SQLiteDatabase db = getWritableDatabase();
        int nRows = db.update(table, cv, whereClause, params);
        return  nRows;
    }
}
