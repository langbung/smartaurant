package com.smartaurant_kmutt.smartaurant.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerCheckBillFragment;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerMenuFragment;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerOrderListFragment;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerPromotionFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerMenuSettingFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerRevenueFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerStaffManagmentFragment;

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
                return OwnerMenuSettingFragment.newInstance();
            }
            case 2:{
                return OwnerStaffManagmentFragment.newInstance();
            }

            default:
                return null;

        }
    }

}
