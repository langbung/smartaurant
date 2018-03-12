package com.smartaurant_kmutt.smartaurant.dao;

/**
 * Created by LB on 2/3/2561.
 */

public class DatabaseDao {
    String name;
    int price;

    public DatabaseDao(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
