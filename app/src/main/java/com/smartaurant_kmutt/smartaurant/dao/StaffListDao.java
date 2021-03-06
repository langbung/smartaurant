package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by LB on 16/3/2561.
 */

public class StaffListDao implements Parcelable {
    ArrayList<StaffItemDao> staffList = new ArrayList<>();

    public StaffListDao() {

    }

    public StaffListDao(ArrayList<StaffItemDao> staffList) {
        this.staffList = staffList;
    }

    protected StaffListDao(Parcel in) {
        staffList = in.createTypedArrayList(StaffItemDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(staffList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StaffListDao> CREATOR = new Creator<StaffListDao>() {
        @Override
        public StaffListDao createFromParcel(Parcel in) {
            return new StaffListDao(in);
        }

        @Override
        public StaffListDao[] newArray(int size) {
            return new StaffListDao[size];
        }
    };

    public ArrayList<StaffItemDao> getStaffList() {
        return staffList;
    }

    public void setStaffList(ArrayList<StaffItemDao> staffList) {
        this.staffList = staffList;
    }
}
