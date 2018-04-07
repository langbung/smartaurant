package com.smartaurant_kmutt.smartaurant.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuOnlyManager;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.view.OrderMenuViewCustomer;

/**
 * Created by LB on 21/3/2561.
 */

public class CustomerOrderListAdapter extends BaseAdapter {
    private OrderMenuOnlyManager orderMenuOnlyManager;
    private OrderMenuKitchenManager orderMenuKitchenManager;
    int mode;
    public static final int MODE_ORDER_KITCHEN_CUSTOMER = 1;

    public CustomerOrderListAdapter() {

    }

    public CustomerOrderListAdapter(int mode) {
        this.mode = mode;
    }

    @Override
    public int getCount() {
        if (mode == MODE_ORDER_KITCHEN_CUSTOMER) {
            if (orderMenuKitchenManager == null)
                return 0;
            if (orderMenuKitchenManager.getOrderMenuKitchenDao().size() <= 0)
                return 0;
            return orderMenuKitchenManager.getOrderMenuKitchenDao().size();
        }
        if (orderMenuOnlyManager == null)
            return 0;
        if (orderMenuOnlyManager.getOrderMenuDao().getOrderList().size() <= 0)
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
        if (convertView != null)
            item = (OrderMenuViewCustomer) convertView;
        else
            item = new OrderMenuViewCustomer(parent.getContext());

        if (mode == MODE_ORDER_KITCHEN_CUSTOMER) {
            OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
//            MyUtil.showText(orderMenuKitchenItemDao.getMenuName());
            item.setName(orderMenuKitchenItemDao.getMenuName());
            item.setQuantity(orderMenuKitchenItemDao.getQuantity());
            item.setStatus(orderMenuKitchenItemDao.getStatus());

        } else {
            OrderMenuItemDao orderMenuKitchenItemDao = orderMenuOnlyManager.getOrderMenuDao().getOrderList().get(position);
            item.setName(orderMenuKitchenItemDao.getName());
            item.setQuantity(orderMenuKitchenItemDao.getQuantity());
            item.setPrice(orderMenuKitchenItemDao.getPrice());
        }
        return item;
    }

    public void setOrderMenuOnlyManager(OrderMenuOnlyManager orderMenuOnlyManager) {
        this.orderMenuOnlyManager = orderMenuOnlyManager;
    }

    public void setOrderMenuKitchenManager(OrderMenuKitchenManager orderMenuKitchenManager) {
        this.orderMenuKitchenManager = orderMenuKitchenManager;
    }
}
