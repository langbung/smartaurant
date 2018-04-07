package com.smartaurant_kmutt.smartaurant.manager;

import com.smartaurant_kmutt.smartaurant.dao.OrderKitchenItemDao;

import java.util.ArrayList;

public class OrderKitchenManager {
    ArrayList<OrderKitchenItemDao> orderKitchenDao = new ArrayList<>();

    public OrderKitchenManager() {
    }

    public ArrayList<OrderKitchenItemDao> getOrderKitchenDao() {
        return orderKitchenDao;
    }

    public void setOrderKitchenDao(ArrayList<OrderKitchenItemDao> orderKitchenDao) {
        this.orderKitchenDao = orderKitchenDao;
    }
}
