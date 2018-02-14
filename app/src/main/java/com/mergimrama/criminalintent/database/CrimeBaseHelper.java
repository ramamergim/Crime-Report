package com.mergimrama.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mergimrama.criminalintent.database.CrimeDbSchema.CrimeTable;

import static com.mergimrama.criminalintent.database.CrimeDbSchema.*;

/**
 * Created by Mergim on 03-Feb-18.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.SUSPECT + ")"
        );

        sqLiteDatabase.execSQL("create table " + SuspectTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                SuspectTable.Cols.UUID + ", " +
                SuspectTable.Cols.CONTACT_ID + ", " +
                SuspectTable.Cols.NAME + ", " +
                SuspectTable.Cols.PHONE_NUMBER + ", " +
                SuspectTable.Cols.CRIME_COUNT + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
