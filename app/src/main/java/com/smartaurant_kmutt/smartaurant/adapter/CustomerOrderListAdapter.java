package com.smartaurant_kmutt.smartaurant.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuOnlyManager;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.view.OrderAndCheckBillMenuViewCustomer;
import com.smartaurant_kmutt.smartaurant.view.OrderMenuViewCustomer;

/**
 * Created by LB on 21/3/2561.
 */

public class CustomerOrderListAdapter extends BaseAdapter {
    private OrderMenuOnlyManager orderMenuOnlyManager;
    private OrderMenuKitchenManager orderMenuKitchenManager;
    int mode;
    public static final int MODE_ORDER_KITCHEN_CUSTOMER = 1;
    public static final int MODE_ORDER_KITCHEN_STAFF = 2;
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
        if (orderMenuKitchenManager == null)
            return 0;
        if (orderMenuKitchenManager.getOrderMenuKitchenDao().size() <= 0)
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
        OrderAndCheckBillMenuViewCustomer item;
        if (convertView != null)
            item = (OrderAndCheckBillMenuViewCustomer) convertView;
        else
            item = new OrderAndCheckBillMenuViewCustomer(parent.getContext());

        if (mode == MODE_ORDER_KITCHEN_CUSTOMER) {
            OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
//            MyUtil.showText(orderMenuKitchenItemDao.getMenuName());
            item.setName(orderMenuKitchenItemDao.getMenuName());
            item.setQuantity(orderMenuKitchenItemDao.getQuantity());
            item.setStatus(orderMenuKitchenItemDao.getStatus());
            item.setPrice(orderMenuKitchenItemDao.getPrice());

        }else if(mode==MODE_ORDER_KITCHEN_STAFF) {
            OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
            item.setName(orderMenuKitchenItemDao.getMenuName());
            item.setQuantity(orderMenuKitchenItemDao.getQuantity());
            item.setStatusAtRight(orderMenuKitchenItemDao.getStatus());
        }
        else {
            OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
            item.setName(orderMenuKitchenItemDao.getMenuName());
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
