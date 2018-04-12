package com.smartaurant_kmutt.smartaurant.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuOnlyManager;
import com.smartaurant_kmutt.smartaurant.view.OrderMenuViewCustomer;

/**
 * Created by LB on 21/3/2561.
 */

public class OrderMenuOnlyAdapter extends BaseAdapter {
    OrderMenuKitchenManager orderMenuKitchenManager;

    @Override
    public int getCount() {
        if(orderMenuKitchenManager ==null)
            return 0;
        if(orderMenuKitchenManager.getOrderMenuKitchenDao().size()<=0)
            return 0;
        return orderMenuKitchenManager.getOrderMenuKitchenDao().size();

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

        item.setName(orderMenuKitchenManager.getOrderMenuKitchenDao().get(position).getMenuName());
        item.setQuantity(orderMenuKitchenManager.getOrderMenuKitchenDao().get(position).getQuantity());
        item.setPrice(orderMenuKitchenManager.getOrderMenuKitchenDao().get(position).getPrice());
        return item;
    }

    public void setOrderMenuKitchenManager(OrderMenuKitchenManager orderMenuKitchenManager) {
        this.orderMenuKitchenManager = orderMenuKitchenManager;
    }
}
