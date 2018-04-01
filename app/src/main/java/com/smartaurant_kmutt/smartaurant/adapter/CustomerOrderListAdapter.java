package com.smartaurant_kmutt.smartaurant.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartaurant_kmutt.smartaurant.manager.OrderMenuOnlyManager;
import com.smartaurant_kmutt.smartaurant.view.OrderMenuViewCustomer;

/**
 * Created by LB on 21/3/2561.
 */

public class CustomerOrderListAdapter extends BaseAdapter {
    OrderMenuOnlyManager orderMenuOnlyManager;
    @Override
    public int getCount() {
        if(orderMenuOnlyManager ==null)
            return 0;
        if(orderMenuOnlyManager.getOrderMenuDao().getOrderList().size()<=0)
            return 0;
        return orderMenuOnlyManager.getOrderMenuDao().getOrderList().size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderMenuViewCustomer item;
        if(convertView!=null)
            item = (OrderMenuViewCustomer)convertView;
        else
            item = new OrderMenuViewCustomer(parent.getContext());

        item.setName(orderMenuOnlyManager.getOrderMenuDao().getOrderList().get(position).getName());
        item.setQuantity(orderMenuOnlyManager.getOrderMenuDao().getOrderList().get(position).getQuantity());
        item.setPrice(orderMenuOnlyManager.getOrderMenuDao().getOrderList().get(position).getPrice());
        return item;
    }

    public void setOrderMenuOnlyManager(OrderMenuOnlyManager orderMenuOnlyManager) {
        this.orderMenuOnlyManager = orderMenuOnlyManager;
    }
}
