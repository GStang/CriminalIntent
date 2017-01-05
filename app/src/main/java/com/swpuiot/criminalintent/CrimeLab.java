package com.swpuiot.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.swpuiot.criminalintent.CrimeDbSchema.*;

/**
 * Created by DELL on 2016/12/8.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static CrimeLab getsCrimeLab(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context;
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

//    public List<Crime> getCrimes() {
//        return new ArrayList<>();
//    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getmId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getmTitle());
        values.put(CrimeTable.Cols.DATE, crime.getmDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.ismSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getmSuspect());

        return values;
    }

    public Crime getCrime(UUID id) {
//        return null;
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + "=?",
                new String[]{
                        id.toString()
                }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getmId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + "=?", new String[]{uuidString});
    }

    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);

    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new CrimeCursorWrapper(cursor);
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);
        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }
}
