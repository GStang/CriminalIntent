package com.swpuiot.criminalintent;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.swpuiot.criminalintent.CrimeDbSchema.*;

/**
 * Created by DELL on 2016/12/17.
 */
public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    public CrimeBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public CrimeBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement," +
                CrimeTable.Cols.UUID + "," +
                CrimeTable.Cols.TITLE + "," +
                CrimeTable.Cols.DATE + "," +
                CrimeTable.Cols.SOLVED + "," +
                CrimeTable.Cols.SUSPECT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
