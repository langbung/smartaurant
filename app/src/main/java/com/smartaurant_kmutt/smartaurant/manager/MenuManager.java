package com.smartaurant_kmutt.smartaurant.manager;

import android.os.Bundle;

import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.MenuListDao;

import java.util.ArrayList;

/**
 * Created by LB on 4/3/2561.
 */

public class MenuManager {
    MenuListDao menuDao;

    public MenuManager() {
    }

    public MenuListDao getMenuDao() {
        return menuDao;
    }

    public void setMenuDao(MenuListDao menuList) {
        this.menuDao = menuList;
    }
}
