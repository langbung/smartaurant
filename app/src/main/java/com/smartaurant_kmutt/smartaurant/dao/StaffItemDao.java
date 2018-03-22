package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LB on 16/3/2561.
 */

public class StaffItemDao implements Parcelable{
    String id;
    String name;
    String email;
    String password;
    String role;

    public StaffItemDao() {
    }

    public StaffItemDao(String id, String name, String email, String password,String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    protected StaffItemDao(Parcel in) {
        name = in.readString();
        email = in.readString();
        password = in.readString();
        id = in.readString();
        role = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(id);
        dest.writeString(role);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StaffItemDao> CREATOR = new Creator<StaffItemDao>() {
        @Override
        public StaffItemDao createFromParcel(Parcel in) {
            return new StaffItemDao(in);
        }

        @Override
        public StaffItemDao[] newArray(int size) {
            return new StaffItemDao[size];
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
