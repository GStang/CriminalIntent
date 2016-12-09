package com.swpuiot.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by DELL on 2016/12/8.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
