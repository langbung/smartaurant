package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LB on 16/3/2561.
 */

public class StaffDao implements Parcelable{
    String id;
    String name;
    String email;
    String password;

    public StaffDao() {
    }

    public StaffDao(String id,String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    protected StaffDao(Parcel in) {
        name = in.readString();
        email = in.readString();
        password = in.readString();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StaffDao> CREATOR = new Creator<StaffDao>() {
        @Override
        public StaffDao createFromParcel(Parcel in) {
            return new StaffDao(in);
        }

        @Override
        public StaffDao[] newArray(int size) {
            return new StaffDao[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
