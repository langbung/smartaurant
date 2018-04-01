package com.smartaurant_kmutt.smartaurant.manager;

import com.smartaurant_kmutt.smartaurant.dao.OrderMenuListOnlyDao;

/**
 * Created by LB on 21/3/2561.
 */

public class OrderMenuOnlyManager {
    OrderMenuListOnlyDao orderMenuDao;

    public OrderMenuOnlyManager(OrderMenuListOnlyDao orderMenuListOnlyDao) {
        this.orderMenuDao = orderMenuListOnlyDao;
    }

    public OrderMenuOnlyManager() {
    }

    public OrderMenuListOnlyDao getOrderMenuDao() {
        return orderMenuDao;
    }

    public void setOrderMenuDao(OrderMenuListOnlyDao orderMenuDao) {
        this.orderMenuDao = orderMenuDao;
    }
}
