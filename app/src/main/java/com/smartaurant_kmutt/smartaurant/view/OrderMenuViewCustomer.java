package com.smartaurant_kmutt.smartaurant.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.smartaurant_kmutt.smartaurant.R;

import java.util.Locale;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class OrderMenuViewCustomer extends BaseCustomViewGroup {
    TextView tvName;
    TextView tvPrice;
    TextView tvQuantity;

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
        tvName=findViewById(R.id.tvName);
        tvPrice=findViewById(R.id.tvPrice);
        tvQuantity=findViewById(R.id.tvQuantity);
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
        tvName.setText(name);
    }
    public void setPrice(float price){
        String textPrice=String.format(Locale.ENGLISH,"%.2f",price);
        tvPrice.setText(textPrice);
    }
    public void setQuantity(int quantity){
        tvQuantity.setText(String.valueOf(quantity));
    }

}
