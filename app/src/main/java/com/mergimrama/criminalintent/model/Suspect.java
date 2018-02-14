package com.mergimrama.criminalintent.model;

import java.util.UUID;

/**
 * Created by Mergim on 14-Feb-18.
 */

public class Suspect {
    private UUID mId;
    private String mContactId;
    private String mName;
    private String mPhoneNumber;
    private int mCrimeCount;

    public Suspect(UUID id) {
        mId = id;
    }

    public Suspect() {
        this(UUID.randomUUID());
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    public String getContactId() {
        return mContactId;
    }

    public void setContactId(String mContactId) {
        this.mContactId = mContactId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public int getCrimeCount() {
        return mCrimeCount;
    }

    public void setCrimeCount(int mCrimeCount) {
        this.mCrimeCount = mCrimeCount;
    }
}
