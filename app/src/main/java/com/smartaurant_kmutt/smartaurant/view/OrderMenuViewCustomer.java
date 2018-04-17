package com.smartaurant_kmutt.smartaurant.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.smartaurant_kmutt.smartaurant.R;

import java.util.Locale;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class OrderMenuViewCustomer extends BaseCustomViewGroup {
    TextView tvOrderId;
    TextView tvPrice;
    TextView tvDate;
    TextView tvTable;

    public OrderMenuViewCustomer(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public OrderMenuViewCustomer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public OrderMenuViewCustomer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public OrderMenuViewCustomer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    private void initInflate() {
        inflate(getContext(), R.layout.custom_customer_orderlist, this);
    }

    private void initInstances() {
        // findViewById here
        tvOrderId =findViewById(R.id.tvName);
        tvPrice=findViewById(R.id.tvPrice);
        tvDate =findViewById(R.id.tvQuantity);
        tvTable = findViewById(R.id.tvTable);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }
    public void setName(String name){
        tvOrderId.setText(name);
    }

    public void setOrderId(String orderId){
        tvOrderId.setText(orderId);
    }

    public void setPrice(float price){
        String textPrice=String.format(Locale.ENGLISH,"%.2f",price);
        tvPrice.setText(textPrice);
    }
    public void setTotal(float total){
        String textPrice=String.format(Locale.ENGLISH,"%.2f",total);
        tvPrice.setText(textPrice);
    }

    public void setTable(int table){
       String tableText="table "+table;
        tvTable.setText(tableText);
    }


    public void setQuantity(int quantity){
        String quantityText = "x "+quantity;
        tvDate.setText(quantityText);
    }

    public void setDate(String date){
        tvDate.setText(date);
    }



    public void setStatus(String status){
        tvPrice.setText(status);
    }

}
