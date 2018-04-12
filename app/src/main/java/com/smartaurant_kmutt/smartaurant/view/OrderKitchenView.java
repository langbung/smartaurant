package com.smartaurant_kmutt.smartaurant.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.smartaurant_kmutt.smartaurant.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class OrderKitchenView extends BaseCustomViewGroup {
    TextView tvName;
    TextView tvQuantity;
    TextView tvNote;
    TextView tvStatus;
    LinearLayout backGround;
    public OrderKitchenView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public OrderKitchenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public OrderKitchenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public OrderKitchenView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.custom_kitchen_order_view, this);
    }

    private void initInstances() {
        // findViewById here
        initTextView();
        initLinearLayout();
    }

    private void initLinearLayout() {
        backGround = findViewById(R.id.backGround);
    }

    private void initTextView() {
        tvName = findViewById(R.id.tvName);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvNote = findViewById(R.id.tvNote);
        tvStatus = findViewById(R.id.tvStatus);

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
    public void setName(String name){
        tvName.setText(name);
    }
    public void setQuantity(int quantity){
        String quantityText = "x"+quantity;
        tvQuantity.setText(quantityText);
    }
    public void setStatus(String status){
        tvStatus.setText(status);
    }
    public void setNote(String note){
        tvNote.setText(note);
    }

    public void setZeroIndex(){
        backGround.setBackgroundColor(Color.parseColor("#ffd12121"));

        tvName.setTextColor(Color.parseColor("#ffffffff"));
        tvQuantity.setTextColor(Color.parseColor("#ffffffff"));
        tvStatus.setTextColor(Color.parseColor("#ffffffff"));
        tvNote.setTextColor(Color.parseColor("#ffffffff"));

    }
    public void setBackground(){
        backGround.setBackgroundColor(Color.parseColor("#ffffffff"));

        tvName.setTextColor(Color.parseColor("#ff222222"));
        tvQuantity.setTextColor(Color.parseColor("#ff222222"));
        tvStatus.setTextColor(Color.parseColor("#ff222222"));
        tvNote.setTextColor(Color.parseColor("#ff222222"));
    }


}
