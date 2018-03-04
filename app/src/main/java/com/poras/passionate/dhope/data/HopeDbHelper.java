package com.poras.passionate.dhope.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by purus on 3/4/2018.
 */

public class HopeDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hope.db";

    private static final int DATABASE_VERSION = 1;

    public HopeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_HOPE_TABLE =

                "CREATE TABLE " + HopeContract.HopeEntry.TABLE_NAME + " (" +

                        HopeContract.HopeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        HopeContract.HopeEntry.COLUMN_TYPE + " INTEGER NOT NULL, " +

                        HopeContract.HopeEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +

                        HopeContract.HopeEntry.COLUMN_QUANTITY + " TEXT NOT NULL, " +
                        HopeContract.HopeEntry.COLUMN_LAT + " TEXT NOT NULL, " +
                        HopeContract.HopeEntry.COLUMN_LANG + " TEXT NOT NULL, " +
                        HopeContract.HopeEntry.COLUMN_TIME + " TIMESTAMP NOT NULL);";

        db.execSQL(SQL_CREATE_HOPE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HopeContract.HopeEntry.TABLE_NAME);
        onCreate(db);
    }
}

