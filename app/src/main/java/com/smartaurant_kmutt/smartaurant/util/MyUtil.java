package com.smartaurant_kmutt.smartaurant.util;

import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by LB on 16/3/2561.
 */

public class MyUtil {
    public static boolean checkText(String text){
        if(!text.equals("")){
            return true;
        }
        return false;
    }
    public static void showText(String text){
        Toast.makeText(Contextor.getInstance().getContext(),text,Toast.LENGTH_LONG).show();
    }


    /**************
     *  String menuID = String.format(Locale.ENGLISH,"MN%03d",maxMenuId+1);
     */


    /******************************
     *      MenuListDao menuListDao = new MenuListDao();
            ArrayList<MenuItemDao> menuList = new ArrayList<>();
            for (DataSnapshot menuChild : dataSnapshot.getChildren()) {
            MenuItemDao menuItemDao = menuChild.getValue(MenuItemDao.class);
            menuList.add(menuItemDao);

     *
     */
}
