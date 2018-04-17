package com.smartaurant_kmutt.smartaurant.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.manager.MenuManager;
import com.smartaurant_kmutt.smartaurant.view.MenuViewCustomer;

import java.util.ArrayList;

/**
 * Created by LB on 4/3/2561.
 */

public class MenuAdapter extends BaseAdapter {
    MenuManager menuManager;
    int mode;
    public static final int CUSTOMER_MODE = 1;
    public static final int STAFF_MODE = 2;
    public static final int OWNER_MODE = 3;


    public MenuAdapter(int mode) {
        this.mode = mode;
    }

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
        if (convertView == null)
            item = new MenuViewCustomer(parent.getContext());
        else
            item = (MenuViewCustomer) convertView;
        MenuItemDao menuItemDao = menuManager.getMenuDao().getMenuList().get(position);
        String allergen = menuItemDao.getAllergen();
        item.setImage(menuItemDao.getImageUri());
        item.setName(menuItemDao.getName());
        item.setPrice(menuItemDao.getPrice());
        item.unsetVisibility();
        ArrayList<Integer> resIdList = new ArrayList<>();
        if(allergen.contains("egg"))
            resIdList.add(R.drawable.ic_allergens_eggs);
        if(allergen.contains("milk"))
            resIdList.add(R.drawable.ic_allergens_milk);
        if(allergen.contains("bean"))
            resIdList.add(R.drawable.ic_allergens_pea);
        if(allergen.contains("seafood"))
            resIdList.add(R.drawable.ic_allergens_mollusc);
        item.setAllergen(resIdList);
        if (mode == STAFF_MODE){
//            MyUtil.showText("in staff");
            item.setEnable(menuItemDao.isEnable());
        }
        return item;
    }
    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }
}
