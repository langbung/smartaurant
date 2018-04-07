package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by LB on 4/3/2561.
 */

public class MenuListDao implements Parcelable{
    ArrayList<MenuItemDao> menuList = new ArrayList<>();

    public MenuListDao() {

    }

    protected MenuListDao(Parcel in) {
        menuList = in.createTypedArrayList(MenuItemDao.CREATOR);
    }

    public static final Creator<MenuListDao> CREATOR = new Creator<MenuListDao>() {
        @Override
        public MenuListDao createFromParcel(Parcel in) {
            return new MenuListDao(in);
        }

        @Override
        public MenuListDao[] newArray(int size) {
            return new MenuListDao[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(menuList);
    }

    public ArrayList<MenuItemDao> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<MenuItemDao> menuList) {
        this.menuList = menuList;
    }
}
