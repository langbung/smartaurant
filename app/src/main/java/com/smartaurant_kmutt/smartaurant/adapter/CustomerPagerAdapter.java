package com.smartaurant_kmutt.smartaurant.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerCategoryFragment;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerMenuFragment;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerOrderListFragment;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerPromotionFragment;

/**
 * Created by LB on 19/2/2561.
 */

public class CustomerPagerAdapter extends FragmentStatePagerAdapter {
    private int table;
    private OrderItemDao orderItemDao;
    OrderKitchenItemDao orderKitchenItemDao;

    public CustomerPagerAdapter(FragmentManager fm) {
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
                return CustomerPromotionFragment.newInstance();
            }
            case 1:{
                Bundle bundle = new Bundle();
                bundle.putInt("table",table);
                if(orderItemDao!=null)
                    bundle.putParcelable("orderItemDao",orderItemDao);
                return CustomerCategoryFragment.newInstance(bundle);
//                Bundle bundle = new Bundle();
//                bundle.putInt("table",table);
//                bundle.putParcelable("orderItemDao",orderItemDao);
//                bundle.putParcelable("orderKitchenItemDao",orderKitchenItemDao);
//                return CustomerMenuFragment.newInstance(bundle);
            }
            case 2:{
                Bundle bundle = new Bundle();
                bundle.putInt("table",table);
                bundle.putParcelable("orderItemDao",orderItemDao);
                return CustomerOrderListFragment.newInstance(bundle);
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
            default:{
                return "";
            }

        }
    }

    public void setTable(int table) {
        this.table = table;
    }

    public void setOrderItemDao(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    public void setOrderKitchenItemDao(OrderKitchenItemDao orderKitchenItemDao) {
        this.orderKitchenItemDao = orderKitchenItemDao;
    }
}
