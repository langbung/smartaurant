package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class OrderKitchenItemDao implements Parcelable{
    private String OrderId;
    private ArrayList<OrderMenuKitchenItemDao> menuKitchenList = new ArrayList<>();

    public OrderKitchenItemDao() {
    }

    public OrderKitchenItemDao(String orderId, ArrayList<OrderMenuKitchenItemDao> menuKitchenList) {
        OrderId = orderId;
        this.menuKitchenList = menuKitchenList;
    }

    protected OrderKitchenItemDao(Parcel in) {
        OrderId = in.readString();
        menuKitchenList = in.createTypedArrayList(OrderMenuKitchenItemDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(OrderId);
        dest.writeTypedList(menuKitchenList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderKitchenItemDao> CREATOR = new Creator<OrderKitchenItemDao>() {
        @Override
        public OrderKitchenItemDao createFromParcel(Parcel in) {
            return new OrderKitchenItemDao(in);
        }

        @Override
        public OrderKitchenItemDao[] newArray(int size) {
            return new OrderKitchenItemDao[size];
        }
    };

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public ArrayList<OrderMenuKitchenItemDao> getMenuKitchenList() {
        return menuKitchenList;
    }

    public void setMenuKitchenList(ArrayList<OrderMenuKitchenItemDao> menuKitchenList) {
        this.menuKitchenList = menuKitchenList;
    }
}
