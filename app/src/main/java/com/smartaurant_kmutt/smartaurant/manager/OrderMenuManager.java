package com.smartaurant_kmutt.smartaurant.manager;

import com.smartaurant_kmutt.smartaurant.dao.OrderMenuListDao;

/**
 * Created by LB on 21/3/2561.
 */

public class OrderMenuManager {
    OrderMenuListDao orderMenuDao;

    public OrderMenuManager(OrderMenuListDao orderMenuListDao) {
        this.orderMenuDao = orderMenuListDao;
    }

    public OrderMenuManager() {
    }

    public OrderMenuListDao getOrderMenuDao() {
        return orderMenuDao;
    }

    public void setOrderMenuDao(OrderMenuListDao orderMenuDao) {
        this.orderMenuDao = orderMenuDao;
    }
}
