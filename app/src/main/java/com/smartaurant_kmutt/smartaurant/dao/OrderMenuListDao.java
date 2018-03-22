package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by LB on 21/3/2561.
 */

public class OrderMenuListDao implements Parcelable {
    ArrayList<OrderMenuItemDao> orderList = new ArrayList<>();

    public OrderMenuListDao() {
    }

    public OrderMenuListDao(ArrayList<OrderMenuItemDao> orderList) {
        this.orderList = orderList;
    }

    protected OrderMenuListDao(Parcel in) {
        orderList = in.createTypedArrayList(OrderMenuItemDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(orderList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderMenuListDao> CREATOR = new Creator<OrderMenuListDao>() {
        @Override
        public OrderMenuListDao createFromParcel(Parcel in) {
            return new OrderMenuListDao(in);
        }

        @Override
        public OrderMenuListDao[] newArray(int size) {
            return new OrderMenuListDao[size];
        }
    };

    public ArrayList<OrderMenuItemDao> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<OrderMenuItemDao> orderList) {
        this.orderList = orderList;
    }
}
