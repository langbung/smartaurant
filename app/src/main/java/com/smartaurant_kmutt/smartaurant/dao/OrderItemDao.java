package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LB on 20/3/2561.
 */

public class OrderItemDao implements Parcelable ,Serializable{
    String orderId;
    int table;
    boolean beginOrder;
    Map<String,Integer> orderList;

    public OrderItemDao() {

    }

    public OrderItemDao(String orderId, int table, boolean beginOrder,Map<String, Integer> orderList) {
        this.orderId = orderId;
        this.table = table;
        this.orderList = orderList;
    }

    protected OrderItemDao(Parcel in) {
        orderId = in.readString();
        table = in.readInt();
        beginOrder = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeInt(table);
        dest.writeByte((byte) (beginOrder ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderItemDao> CREATOR = new Creator<OrderItemDao>() {
        @Override
        public OrderItemDao createFromParcel(Parcel in) {
            return new OrderItemDao(in);
        }

        @Override
        public OrderItemDao[] newArray(int size) {
            return new OrderItemDao[size];
        }
    };

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public boolean isBeginOrder() {
        return beginOrder;
    }

    public void setBeginOrder(boolean beginOrder) {
        this.beginOrder = beginOrder;
    }

    public Map<String, Integer> getOrderList() {
        return orderList;
    }

    public void setOrderList(Map<String, Integer> orderList) {
        this.orderList = orderList;
    }
}
