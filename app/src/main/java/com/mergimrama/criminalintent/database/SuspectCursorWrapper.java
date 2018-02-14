package com.mergimrama.criminalintent.database;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.mergimrama.criminalintent.model.Suspect;

import java.util.UUID;

import static com.mergimrama.criminalintent.database.CrimeDbSchema.*;

/**
 * Created by Mergim on 14-Feb-18.
 */

public class SuspectCursorWrapper extends CursorWrapper {

    private Context mContext;

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public SuspectCursorWrapper(Cursor cursor, Context context) {
        super(cursor);
        this.mContext = context;
    }

    public Suspect getSuspect() {
        String uuidString = getString(getColumnIndex(SuspectTable.Cols.UUID));
        String contactId = getString(getColumnIndex(SuspectTable.Cols.CONTACT_ID));
        String displayName = getString(getColumnIndex(SuspectTable.Cols.NAME));
        String phoneNumber = getString(getColumnIndex(SuspectTable.Cols.PHONE_NUMBER));
        int crimeCount = getInt(getColumnIndex(SuspectTable.Cols.CRIME_COUNT));

        Suspect suspect = new Suspect(UUID.fromString(uuidString));
        suspect.setContactId(contactId);
        suspect.setName(displayName);
        suspect.setPhoneNumber(phoneNumber);
        suspect.setCrimeCount(crimeCount);

        return suspect;
    }
}
