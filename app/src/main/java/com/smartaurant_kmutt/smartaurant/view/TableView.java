package com.smartaurant_kmutt.smartaurant.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.smartaurant_kmutt.smartaurant.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class TableView extends BaseCustomViewGroup {

    TextView tvTableText;
    ImageView imageView;
    ImageView ivCallWaiter;

    public TableView(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public TableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public TableView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.custom_table_item, this);
    }

    private void initInstances() {
        // findViewById here
        tvTableText = findViewById(R.id.tvTableText);
        imageView =  findViewById(R.id.imageView);
        ivCallWaiter = findViewById(R.id.ivCallWaiter);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width*2/3;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
        setMeasuredDimension(width,height);
    }
    public void setTable(int table){
        tvTableText.setText("Table "+table);
    }

    public void setImage(int idDrawableResource){
        imageView.setImageResource(idDrawableResource);
    }

    public void setBackground(int resId){
        imageView.setBackgroundResource(resId);
    }

    public void setBackground(String color){
        imageView.setBackgroundColor(Color.parseColor(color));
    }
    public void setCallWaiter(boolean isCallWaiter){
        if(isCallWaiter)
            ivCallWaiter.setVisibility(VISIBLE);
        else
            ivCallWaiter.setVisibility(INVISIBLE);
    }

}
