package com.smartaurant_kmutt.smartaurant.manager;

import com.smartaurant_kmutt.smartaurant.dao.OrderMenuListDao;

/**
 * Created by LB on 21/3/2561.
 */

public class OrderMenuOnlyManager {
    OrderMenuListDao orderMenuDao;

    public OrderMenuOnlyManager(OrderMenuListDao orderMenuListDao) {
        this.orderMenuDao = orderMenuListDao;
    }

    public OrderMenuOnlyManager() {
    }

    public OrderMenuListDao getOrderMenuDao() {
        return orderMenuDao;
    }

    public void setOrderMenuDao(OrderMenuListDao orderMenuDao) {
        this.orderMenuDao = orderMenuDao;
    }
}
