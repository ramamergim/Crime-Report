package com.mergimrama.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Mergim on 22-Dec-17.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
