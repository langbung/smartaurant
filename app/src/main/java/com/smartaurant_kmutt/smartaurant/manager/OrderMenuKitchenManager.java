package com.smartaurant_kmutt.smartaurant.manager;

import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;

import java.util.ArrayList;

public class OrderMenuKitchenManager {

    ArrayList<OrderMenuKitchenItemDao> orderMenuKitchenDao = new ArrayList<>();

    public OrderMenuKitchenManager() {

    }

    public ArrayList<OrderMenuKitchenItemDao> getOrderMenuKitchenDao() {
        return orderMenuKitchenDao;
    }

    public void setOrderMenuKitchenDao(ArrayList<OrderMenuKitchenItemDao> orderMenuKitchenDao) {
        this.orderMenuKitchenDao = orderMenuKitchenDao;
    }
}
