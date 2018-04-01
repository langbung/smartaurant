package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by LB on 30/3/2561.
 */

public class TableListDao implements Parcelable{
    ArrayList<TableItemDao> tableList=new ArrayList<>();

    protected TableListDao(Parcel in) {
        tableList = in.createTypedArrayList(TableItemDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(tableList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TableListDao> CREATOR = new Creator<TableListDao>() {
        @Override
        public TableListDao createFromParcel(Parcel in) {
            return new TableListDao(in);
        }

        @Override
        public TableListDao[] newArray(int size) {
            return new TableListDao[size];
        }
    };

    public TableListDao() {

    }

    public ArrayList<TableItemDao> getTableList() {
        return tableList;
    }

    public void setTableList(ArrayList<TableItemDao> tableList) {
        this.tableList = tableList;
    }
}
