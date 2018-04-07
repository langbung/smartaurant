package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderMenuKitchenItemDao implements Parcelable {
    private String menuName;
    private int quantity;
    private String status;
    private String orderId;
    private String orderKitchenId;

    public OrderMenuKitchenItemDao() {
    }

    public OrderMenuKitchenItemDao(String menuName, int quantity, String status, String orderId,String orderKitchenId) {
        this.menuName = menuName;
        this.quantity = quantity;
        this.status = status;
        this.orderId = orderId;
        this.orderKitchenId = orderKitchenId;
    }

    protected OrderMenuKitchenItemDao(Parcel in) {
        menuName = in.readString();
        quantity = in.readInt();
        status = in.readString();
        orderId = in.readString();
        orderKitchenId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(menuName);
        dest.writeInt(quantity);
        dest.writeString(status);
        dest.writeString(orderId);
        dest.writeString(orderKitchenId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderMenuKitchenItemDao> CREATOR = new Creator<OrderMenuKitchenItemDao>() {
        @Override
        public OrderMenuKitchenItemDao createFromParcel(Parcel in) {
            return new OrderMenuKitchenItemDao(in);
        }

        @Override
        public OrderMenuKitchenItemDao[] newArray(int size) {
            return new OrderMenuKitchenItemDao[size];
        }
    };

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderKitchenId() {
        return orderKitchenId;
    }

    public void setOrderKitchenId(String orderKitchenId) {
        this.orderKitchenId = orderKitchenId;
    }
}
