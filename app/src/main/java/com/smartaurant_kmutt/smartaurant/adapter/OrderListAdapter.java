package com.smartaurant_kmutt.smartaurant.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.manager.OrderListManager;
import com.smartaurant_kmutt.smartaurant.view.OrderMenuViewCustomer;

public class OrderListAdapter extends BaseAdapter {
    OrderListManager orderListManager;
    @Override
    public int getCount() {
        if(orderListManager==null)
            return 0;
        if(orderListManager.getOrderList().size()<=0)
            return 0;
        return orderListManager.getOrderList().size();
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
            item = (OrderMenuViewCustomer) convertView;
        else
            item = new OrderMenuViewCustomer(parent.getContext());
        OrderItemDao orderItemDao = orderListManager.getOrderList().get(position);
        item.setName(orderItemDao.getOrderId());
        item.setTable(orderItemDao.getTable());
        item.setTotal(orderItemDao.getTotal());
        return item;
    }

    public void setOrderListManager(OrderListManager orderListManager) {
        this.orderListManager = orderListManager;
    }
}
