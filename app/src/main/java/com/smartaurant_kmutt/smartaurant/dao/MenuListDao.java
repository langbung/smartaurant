package com.smartaurant_kmutt.smartaurant.dao;

import com.smartaurant_kmutt.smartaurant.adapter.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LB on 4/3/2561.
 */

public class MenuListDao {
    List<MenuIdDao> menuList;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuIdDao> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuIdDao> menuList) {
        this.menuList = menuList;
    }
}
