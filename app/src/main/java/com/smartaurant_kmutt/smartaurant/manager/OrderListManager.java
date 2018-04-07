package com.smartaurant_kmutt.smartaurant.manager;

import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;

import java.util.ArrayList;

public class OrderListManager {
    ArrayList<OrderItemDao> orderList = new ArrayList<>();

    public OrderListManager() {
    }

    public ArrayList<OrderItemDao> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<OrderItemDao> orderList) {
        this.orderList = orderList;
    }
}
