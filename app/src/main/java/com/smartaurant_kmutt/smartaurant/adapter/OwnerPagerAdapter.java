package com.smartaurant_kmutt.smartaurant.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerListMenuFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerRevenueListFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerSettingFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerStaffManagementFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerVoucherFragment;

/**
 * Created by LB on 19/2/2561.
 */

public class OwnerPagerAdapter extends FragmentStatePagerAdapter {

    public OwnerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        switch (position){
            case 0:{
                return OwnerRevenueListFragment.newInstance();
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
            case 4:{
                return OwnerVoucherFragment.newInstance();
            }

            default:
                return null;

        }
    }

}
