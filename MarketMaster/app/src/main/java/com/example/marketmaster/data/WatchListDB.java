/*
database for the watchlist.
 */
package com.example.marketmaster.data;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashSet;
import java.util.Set;


public class WatchListDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "watch.db";
    public static final String TABLE_NAME = "watchlist";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Name";
    Set<String> added = new HashSet<>();

    public WatchListDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //Drop older table if exists
        onCreate(db);
    }

    /*
    method to add data
     */
    public boolean addData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (added.contains(name)) {
            return false;
        }
        contentValues.put(COL_2, name);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    /*
    method to show all data in the database
     */
    public Cursor showData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
}