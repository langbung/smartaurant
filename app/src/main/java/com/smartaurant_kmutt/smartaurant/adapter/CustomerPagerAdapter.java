package com.smartaurant_kmutt.smartaurant.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerCheckBillFragment;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerMenuFragment;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerOrderListFragment;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerPromotionFragment;

/**
 * Created by LB on 19/2/2561.
 */

public class CustomerPagerAdapter extends FragmentStatePagerAdapter {

    public CustomerPagerAdapter(FragmentManager fm) {
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
                return CustomerPromotionFragment.newInstance();
            }
            case 1:{
                return CustomerMenuFragment.newInstance();
            }
            case 2:{
                return CustomerOrderListFragment.newInstance();
            }
            case 3:{
                return CustomerCheckBillFragment.newInstance();
            }
            default:
                return null;

        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:{
                return "Promotion";
            }
            case 1:{
                return "Menu";
            }
            case 2:{
                return "Order list";
            }
            case 3:{
                return "Check bill";
            }
            default:{
                return "";
            }

        }
    }
}
