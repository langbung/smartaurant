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
    ArrayList<MenuItemDao> menuList = new ArrayList<>();

    public ArrayList<MenuItemDao> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<MenuItemDao> menuList) {
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        if(menuList==null)
            return 0;
        if(menuList.size()<=0)
            return 0;
        return menuList.size();
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
        item.setId(getMenuList().get(position).getName());
        item.setName(String.valueOf(getMenuList().get(position).getPrice()));
        return item;
    }
}
