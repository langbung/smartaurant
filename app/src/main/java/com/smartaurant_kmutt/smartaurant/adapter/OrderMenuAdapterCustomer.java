package com.smartaurant_kmutt.smartaurant.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartaurant_kmutt.smartaurant.manager.OrderMenuManager;
import com.smartaurant_kmutt.smartaurant.view.OrderMenuViewCustomer;

/**
 * Created by LB on 21/3/2561.
 */

public class OrderMenuAdapterCustomer extends BaseAdapter {
    OrderMenuManager orderMenuManager;
    @Override
    public int getCount() {
        if(orderMenuManager ==null)
            return 0;
        if(orderMenuManager.getOrderMenuDao().getOrderList().size()<=0)
            return 0;
        return orderMenuManager.getOrderMenuDao().getOrderList().size();

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

        item.setName(orderMenuManager.getOrderMenuDao().getOrderList().get(position).getName());
        item.setQuantity(orderMenuManager.getOrderMenuDao().getOrderList().get(position).getQuantity());
        item.setPrice(orderMenuManager.getOrderMenuDao().getOrderList().get(position).getPrice());
        return item;
    }

    public void setOrderMenuManager(OrderMenuManager orderMenuManager) {
        this.orderMenuManager = orderMenuManager;
    }
}
