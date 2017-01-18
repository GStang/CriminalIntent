package com.swpuiot.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by DELL on 2016/12/8.
 * 这个Activity是用来放一个Crime的列表，
 * 并且将作为活动的启动Activity
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
