package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LB on 30/3/2561.
 */

public class TableItemDao implements Parcelable {
    private int table;
    private boolean availableToCallWaiter;
    private boolean availableTable;
    private String orderId;

    public TableItemDao() {
    }

    public TableItemDao(int table, boolean availableToCallWaiter, boolean availableTable, String orderId) {
        this.table = table;
        this.availableToCallWaiter = availableToCallWaiter;
        this.availableTable = availableTable;
        this.orderId = orderId;
    }

    protected TableItemDao(Parcel in) {
        table = in.readInt();
        availableToCallWaiter = in.readByte() != 0;
        availableTable = in.readByte() != 0;
        orderId = in.readString();
    }

    public static final Creator<TableItemDao> CREATOR = new Creator<TableItemDao>() {
        @Override
        public TableItemDao createFromParcel(Parcel in) {
            return new TableItemDao(in);
        }

        @Override
        public TableItemDao[] newArray(int size) {
            return new TableItemDao[size];
        }
    };

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public boolean isAvailableToCallWaiter() {
        return availableToCallWaiter;
    }

    public void setAvailableToCallWaiter(boolean availableToCallWaiter) {
        this.availableToCallWaiter = availableToCallWaiter;
    }

    public boolean isAvailableTable() {
        return availableTable;
    }

    public void setAvailableTable(boolean availableTable) {
        this.availableTable = availableTable;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(table);
        dest.writeByte((byte) (availableToCallWaiter ? 1 : 0));
        dest.writeByte((byte) (availableTable ? 1 : 0));
        dest.writeString(orderId);
    }
}
