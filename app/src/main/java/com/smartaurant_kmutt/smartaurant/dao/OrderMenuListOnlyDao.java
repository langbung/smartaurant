package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by LB on 21/3/2561.
 */

public class OrderMenuListOnlyDao implements Parcelable {
    ArrayList<OrderMenuItemOnlyDao> orderList = new ArrayList<>();

    public OrderMenuListOnlyDao() {
    }

    public OrderMenuListOnlyDao(ArrayList<OrderMenuItemOnlyDao> orderList) {
        this.orderList = orderList;
    }

    protected OrderMenuListOnlyDao(Parcel in) {
        orderList = in.createTypedArrayList(OrderMenuItemOnlyDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(orderList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderMenuListOnlyDao> CREATOR = new Creator<OrderMenuListOnlyDao>() {
        @Override
        public OrderMenuListOnlyDao createFromParcel(Parcel in) {
            return new OrderMenuListOnlyDao(in);
        }

        @Override
        public OrderMenuListOnlyDao[] newArray(int size) {
            return new OrderMenuListOnlyDao[size];
        }
    };

    public ArrayList<OrderMenuItemOnlyDao> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<OrderMenuItemOnlyDao> orderList) {
        this.orderList = orderList;
    }
}
