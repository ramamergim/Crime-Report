package com.mergimrama.criminalintent.database;

import java.util.UUID;

/**
 * Created by Mergim on 03-Feb-18.
 */

public class CrimeDbSchema {
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }
    }

    public static final class SuspectTable {
        public static final String NAME = "suspect";

        public static final class Cols {
            private static final String ID = "_id";
            public static final String UUID = "uuid";
            public static final String CONTACT_ID = "contact_id";
            public static final String NAME = "name";
            public static final String PHONE_NUMBER = "phoneNumber";
            public static final String CRIME_COUNT = "crime_count";
        }
    }
}
