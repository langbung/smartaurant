package com.smartaurant_kmutt.smartaurant.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.MenuListDao;
import com.smartaurant_kmutt.smartaurant.manager.MenuManager;
import com.smartaurant_kmutt.smartaurant.view.MenuViewList;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by LB on 4/3/2561.
 */

public class MenuAdapter extends BaseAdapter {
    MenuListDao menuDao;


    @Override
    public int getCount() {
        if(menuDao==null)
            return 0;
        if(menuDao.getMenuList().size()<=0)
            return 0;
        return menuDao.getMenuList().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuViewList item;
        if(convertView == null)
            item = new MenuViewList(parent.getContext());
        else
            item = (MenuViewList) convertView;
//        MenuItemDao menuItemDao =(MenuItemDao) getItem(position);
        item.setId(menuDao.getMenuList().get(position).getName());
        item.setName(String.valueOf(getMenuDao().getMenuList().get(position).getPrice()));
        return item;
    }

    public MenuListDao getMenuDao() {
        return menuDao;
    }

    public void setMenuDao(MenuListDao menuList) {
        this.menuDao = menuList;
    }
}
