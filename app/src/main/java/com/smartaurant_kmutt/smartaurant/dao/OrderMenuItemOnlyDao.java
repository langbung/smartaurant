package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LB on 21/3/2561.
 */

public class OrderMenuItemOnlyDao implements Parcelable {
    String name;
    int quantity;
    float price;

    public OrderMenuItemOnlyDao() {
    }

    public OrderMenuItemOnlyDao(String name, int quantity, float price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    protected OrderMenuItemOnlyDao(Parcel in) {
        name = in.readString();
        quantity = in.readInt();
        price = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(quantity);
        dest.writeFloat(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderMenuItemOnlyDao> CREATOR = new Creator<OrderMenuItemOnlyDao>() {
        @Override
        public OrderMenuItemOnlyDao createFromParcel(Parcel in) {
            return new OrderMenuItemOnlyDao(in);
        }

        @Override
        public OrderMenuItemOnlyDao[] newArray(int size) {
            return new OrderMenuItemOnlyDao[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
