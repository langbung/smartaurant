package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;

/**
 * Created by LB on 2/3/2561.
 */

public class MenuItemDao implements Parcelable {
    private String name;
    private float price;
    private String imageUri;
    private boolean enable;

    public MenuItemDao() {
    }


    public MenuItemDao(String name,float price,String imageUri,boolean enable) {
        this.name = name;
        this.price = price;
        this.imageUri = imageUri;
        this.enable = enable;
    }


    protected MenuItemDao(Parcel in) {
        name = in.readString();
        price = in.readFloat();
        imageUri = in.readString();
        enable = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeString(imageUri);
        dest.writeByte((byte) (enable ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
