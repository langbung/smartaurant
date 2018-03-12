package com.smartaurant_kmutt.smartaurant.manager;

import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.MenuListDao;

import java.util.ArrayList;

/**
 * Created by LB on 4/3/2561.
 */

public class MenuManager {
    ArrayList<MenuItemDao> menuList= new ArrayList<>();

    public MenuManager() {
    }

    public ArrayList<MenuItemDao> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<MenuItemDao> menuList) {
        this.menuList = menuList;
    }
}
