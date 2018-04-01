package com.smartaurant_kmutt.smartaurant.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartaurant_kmutt.smartaurant.dao.MenuListDao;
import com.smartaurant_kmutt.smartaurant.manager.MenuManager;
import com.smartaurant_kmutt.smartaurant.view.MenuViewCustomer;
import com.smartaurant_kmutt.smartaurant.view.MenuViewList;

/**
 * Created by LB on 4/3/2561.
 */

public class CustomerMenuAdapter extends BaseAdapter {
    MenuManager menuManager;
    @Override
    public int getCount() {
        if(menuManager==null)
            return 0;
        if(menuManager.getMenuDao().getMenuList().size()<=0)
            return 0;
        return menuManager.getMenuDao().getMenuList().size();
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
        MenuViewCustomer item;
        if(convertView == null)
            item = new MenuViewCustomer(parent.getContext());
        else
            item = (MenuViewCustomer) convertView;
//        MenuItemDao menuItemDao =(MenuItemDao) getItem(position);
        item.setImage(menuManager.getMenuDao().getMenuList().get(position).getImageUri());
        item.setName(menuManager.getMenuDao().getMenuList().get(position).getName());
        item.setPrice(menuManager.getMenuDao().getMenuList().get(position).getPrice());
        return item;
    }
    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }
}
