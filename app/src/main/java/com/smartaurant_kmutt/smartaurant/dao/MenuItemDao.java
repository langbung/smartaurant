package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;

/**
 * Created by LB on 2/3/2561.
 */

public class MenuItemDao implements Parcelable {
    String name;
    float price;
    String imageUri;

    public MenuItemDao() {
    }


    public MenuItemDao(String name,float price,String imageUri) {
        this.name = name;
        this.price = price;
        this.imageUri = imageUri;
    }

    protected MenuItemDao(Parcel in) {
        name = in.readString();
        price = in.readFloat();
        imageUri = in.readString();
    }

    public static final Creator<MenuItemDao> CREATOR = new Creator<MenuItemDao>() {
        @Override
        public MenuItemDao createFromParcel(Parcel in) {
            return new MenuItemDao(in);
        }

        @Override
        public MenuItemDao[] newArray(int size) {
            return new MenuItemDao[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeString(imageUri);
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
