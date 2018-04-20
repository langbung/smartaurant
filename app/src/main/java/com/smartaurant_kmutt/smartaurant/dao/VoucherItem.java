package com.smartaurant_kmutt.smartaurant.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class VoucherItem implements Parcelable{
    private String id;
    private boolean alreadyUsed;
    private String dateEnd;
    private int discount;

    public VoucherItem() {
    }

    protected VoucherItem(Parcel in) {
        id = in.readString();
        alreadyUsed = in.readByte() != 0;
        dateEnd = in.readString();
        discount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (alreadyUsed ? 1 : 0));
        dest.writeString(dateEnd);
        dest.writeInt(discount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VoucherItem> CREATOR = new Creator<VoucherItem>() {
        @Override
        public VoucherItem createFromParcel(Parcel in) {
            return new VoucherItem(in);
        }

        @Override
        public VoucherItem[] newArray(int size) {
            return new VoucherItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }

    public void setAlreadyUsed(boolean alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
