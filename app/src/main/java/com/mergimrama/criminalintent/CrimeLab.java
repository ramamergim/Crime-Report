package com.mergimrama.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mergimrama.criminalintent.database.CrimeBaseHelper;
import com.mergimrama.criminalintent.database.CrimeCursorWrapper;
import com.mergimrama.criminalintent.database.CrimeDbSchema;
import com.mergimrama.criminalintent.database.CrimeDbSchema.CrimeTable;
import com.mergimrama.criminalintent.database.SuspectCursorWrapper;
import com.mergimrama.criminalintent.model.Crime;
import com.mergimrama.criminalintent.model.Suspect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mergimrama.criminalintent.database.CrimeDbSchema.*;

/**
 * Created by Mergim on 22-Dec-17.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab getInstance(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();
    }

    /*
        The insert(String, String, ContentValues) method has two important arguments, and one that is
        rarely used. The first argument is the table you want to insert into – here, CrimeTable.NAME. The last
        argument is the data you want to put in.
        The second argument is called nullColumnHack. Say that you decided to call insert(…) with an empty ContentValues. SQLite does not allow
        this, so your insert(…) call would fail. f you passed in a value of uuid for nullColumnHack, though, it would ignore that empty
        ContentValues. Instead, it would pass in a ContentValues with uuid set to null. This would allow
        your insert(…) to succeed and create a new row.
     */
    public void addCrime(Crime crime) {
        ContentValues values = getContentValues(crime);

        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?",
                new String[] {
                        uuidString
                });
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        if (crime.getSuspect() != null) {
            values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect().getContactId());
        }

        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, //Columns - selects all columns
                whereClause,
                whereArgs,
                null, //groupBy
                null, // having
                null // orderBy
        );

        return new CrimeCursorWrapper(cursor, mContext);
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
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

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursorWrapper = queryCrimes(null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                crimes.add(cursorWrapper.getCrime());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return crimes;
    }

    public boolean deleteCrime(Crime crime) {

        Suspect suspect = crime.getSuspect();

        if (suspect != null) {
            suspect.setCrimeCount(suspect.getCrimeCount() - 1);
            updateSuspect(suspect);
        }

        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + " = ?", new String[] { crime.getId().toString()});

        return false;
    }

    public void addSuspect(Suspect suspect) {
        ContentValues contentValues = getContentValues(suspect);
        mDatabase.insert(SuspectTable.NAME, null, contentValues);
    }

    public void updateSuspect(Suspect suspect) {
        String uuidString = suspect.getId().toString();
        ContentValues contentValues = getContentValues(suspect);
        mDatabase.update(SuspectTable.NAME, contentValues,
                SuspectTable.Cols.UUID + " = ? ", new String[]{uuidString});
        if (suspect.getCrimeCount() == 0) {
            deleteSuspect(suspect);
        }
    }

    public SuspectCursorWrapper querySuspects(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(SuspectTable.NAME, null, whereClause, whereArgs,
                null, null, null);
        return new SuspectCursorWrapper(cursor, mContext);
    }

    public void deleteSuspect(Suspect suspect) {
        mDatabase.delete(SuspectTable.NAME, SuspectTable.Cols.UUID + " = ? ",
                new String[] { suspect.getId().toString() });
    }

    public Suspect getSuspect(String contactId) {

        if (contactId == null) {
            return null;
        }

        SuspectCursorWrapper suspectCursorWrapper = querySuspects(
                SuspectTable.Cols.CONTACT_ID + " = ? ", new String[]{contactId});

        try {
            if (suspectCursorWrapper.getCount() == 0) {
                return null;
            }

            suspectCursorWrapper.moveToFirst();
            return suspectCursorWrapper.getSuspect();
        } finally {
            suspectCursorWrapper.close();
        }
    }

    private static ContentValues getContentValues(Suspect suspect) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SuspectTable.Cols.UUID, suspect.getId().toString());
        contentValues.put(SuspectTable.Cols.CONTACT_ID, suspect.getContactId());
        contentValues.put(SuspectTable.Cols.NAME, suspect.getName());
        contentValues.put(SuspectTable.Cols.PHONE_NUMBER, suspect.getPhoneNumber());
        contentValues.put(SuspectTable.Cols.CRIME_COUNT, suspect.getCrimeCount());

        return contentValues;
    }
}
