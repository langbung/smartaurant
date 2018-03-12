package com.smartaurant_kmutt.smartaurant.dao;

import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;

/**
 * Created by LB on 2/3/2561.
 */

public class MenuItemDao {
    String name;
    String id;
    int price;

    public MenuItemDao() {
    }


    public MenuItemDao(String id,String name,int price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
