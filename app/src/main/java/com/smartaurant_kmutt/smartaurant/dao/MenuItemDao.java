package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LB on 2/3/2561.
 */

public class MenuItemDao implements Parcelable {
    private String name;
    private String imageUri;
    private String allergen;
    private String type;
    private float price;
    private float promotion;
    private boolean recommended;
    private boolean enable;
    private boolean enableRecommended;
    private boolean enablePromotion;
    private boolean enableAppetizer;
    private boolean enableMainDish;
    private boolean enableDessert;
    private boolean enableDrinks;

    public MenuItemDao() {
    }

    protected MenuItemDao(Parcel in) {
        name = in.readString();
        imageUri = in.readString();
        allergen = in.readString();
        type = in.readString();
        price = in.readFloat();
        promotion = in.readFloat();
        recommended = in.readByte() != 0;
        enable = in.readByte() != 0;
        enableRecommended = in.readByte() != 0;
        enablePromotion = in.readByte() != 0;
        enableAppetizer = in.readByte() != 0;
        enableMainDish = in.readByte() != 0;
        enableDessert = in.readByte() != 0;
        enableDrinks = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUri);
        dest.writeString(allergen);
        dest.writeString(type);
        dest.writeFloat(price);
        dest.writeFloat(promotion);
        dest.writeByte((byte) (recommended ? 1 : 0));
        dest.writeByte((byte) (enable ? 1 : 0));
        dest.writeByte((byte) (enableRecommended ? 1 : 0));
        dest.writeByte((byte) (enablePromotion ? 1 : 0));
        dest.writeByte((byte) (enableAppetizer ? 1 : 0));
        dest.writeByte((byte) (enableMainDish ? 1 : 0));
        dest.writeByte((byte) (enableDessert ? 1 : 0));
        dest.writeByte((byte) (enableDrinks ? 1 : 0));
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

    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPromotion() {
        return promotion;
    }

    public void setPromotion(float promotion) {
        this.promotion = promotion;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnableRecommended() {
        return enableRecommended;
    }

    public void setEnableRecommended(boolean enableRecommended) {
        this.enableRecommended = enableRecommended;
    }

    public boolean isEnablePromotion() {
        return enablePromotion;
    }

    public void setEnablePromotion(boolean enablePromotion) {
        this.enablePromotion = enablePromotion;
    }

    public boolean isEnableAppetizer() {
        return enableAppetizer;
    }

    public void setEnableAppetizer(boolean enableAppetizer) {
        this.enableAppetizer = enableAppetizer;
    }

    public boolean isEnableMainDish() {
        return enableMainDish;
    }

    public void setEnableMainDish(boolean enableMainDish) {
        this.enableMainDish = enableMainDish;
    }

    public boolean isEnableDessert() {
        return enableDessert;
    }

    public void setEnableDessert(boolean enableDessert) {
        this.enableDessert = enableDessert;
    }

    public boolean isEnableDrinks() {
        return enableDrinks;
    }

    public void setEnableDrinks(boolean enableDrinks) {
        this.enableDrinks = enableDrinks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }
}
