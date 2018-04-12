package com.smartaurant_kmutt.smartaurant.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerListMenuFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerRevenueFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerSettingFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerStaffManagementFragment;

/**
 * Created by LB on 19/2/2561.
 */

public class OwnerPagerAdapter extends FragmentStatePagerAdapter {

    public OwnerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        switch (position){
            case 0:{
                return OwnerRevenueFragment.newInstance();
            }
            case 1:{
                return OwnerListMenuFragment.newInstance();
            }
            case 2:{
                return OwnerStaffManagementFragment.newInstance();
            }
            case 3:{
                return OwnerSettingFragment.newInstance();
            }

            default:
                return null;

        }
    }

}
