package com.smartaurant_kmutt.smartaurant.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.smartaurant_kmutt.smartaurant.fragment.staff.StaffCallWaiter;
import com.smartaurant_kmutt.smartaurant.fragment.staff.StaffMenuSettingFragment;
import com.smartaurant_kmutt.smartaurant.fragment.staff.StaffTableCheckOrderFragment;

/**
 * Created by LB on 19/2/2561.
 */

public class StaffPagerAdapter extends FragmentStatePagerAdapter {
    public StaffPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return StaffTableCheckOrderFragment.newInstance();
            }
            case 1: {
                return StaffMenuSettingFragment.newInstance();
            }

            default:
                return null;
        }
    }

}
