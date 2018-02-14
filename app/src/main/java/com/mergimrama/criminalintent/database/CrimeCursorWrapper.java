package com.mergimrama.criminalintent.database;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.mergimrama.criminalintent.CrimeLab;
import com.mergimrama.criminalintent.model.Crime;
import com.mergimrama.criminalintent.database.CrimeDbSchema.CrimeTable;
import com.mergimrama.criminalintent.model.Suspect;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Mergim on 04-Feb-18.
 */

public class CrimeCursorWrapper extends CursorWrapper {
    private Context mContext;

    public CrimeCursorWrapper(Cursor cursor, Context context) {
        super(cursor);
        mContext = context;
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String contactId = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setSuspect(CrimeLab.getInstance(mContext).getSuspect(contactId));

        return crime;
    }
}
