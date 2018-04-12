package com.smartaurant_kmutt.smartaurant.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;

import java.util.ArrayList;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MenuViewCustomer extends BaseCustomViewGroup {
    ImageView ivDisable;
    ImageView ivMenuPicture;
    ImageView iv1;
    ImageView iv2;
    ImageView iv3;
    ImageView iv4;
    TextView tvMenuName;
    TextView tvMenuPrice;

    public MenuViewCustomer(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public MenuViewCustomer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public MenuViewCustomer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public MenuViewCustomer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.custom_menu_item_customer, this);
    }

    private void initInstances() {
        // findViewById here
        ivMenuPicture = findViewById(R.id.ivMenuPicture);
        tvMenuName = findViewById(R.id.tvMenuName);
        tvMenuPrice = findViewById(R.id.tvMenuPrice);
        ivDisable = findViewById(R.id.ivDisable);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
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

    public void setImage(String uri) {
        RequestOptions requestOptions = RequestOptions
                .placeholderOf(R.drawable.loading)
                .error(android.R.drawable.ic_menu_gallery);
        Glide.with(getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(uri)
                .into(ivMenuPicture);
    }


    public void setName(String name) {
        tvMenuName.setText(name);
    }

    public void setPrice(float price) {
        String priceTemp = String.valueOf(price);
        tvMenuPrice.setText(priceTemp);
    }

    public void setEnable(boolean enable) {
        if(enable)
            ivDisable.setVisibility(INVISIBLE);
        else
            ivDisable.setVisibility(VISIBLE);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * 3 / 4;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
        setMeasuredDimension(width, height);
    }
    public void setAllergen(ArrayList<Integer> resList)  {
        int size = resList.size();
//        MyUtil.showText(size+"");
        switch (size){
            case 1:{
                iv1.setImageResource(resList.get(0));
                iv1.setVisibility(VISIBLE);
                break;
            }
            case 2:{
                iv1.setImageResource(resList.get(0));
                iv1.setVisibility(VISIBLE);
                iv2.setImageResource(resList.get(1));
                iv2.setVisibility(VISIBLE);
                break;
            }
            case 3:{
                iv1.setImageResource(resList.get(0));
                iv1.setVisibility(VISIBLE);
                iv2.setImageResource(resList.get(1));
                iv2.setVisibility(VISIBLE);
                iv3.setImageResource(resList.get(2));
                iv3.setVisibility(VISIBLE);
                break;
            }
            case 4:{
                iv1.setImageResource(resList.get(0));
                iv1.setVisibility(VISIBLE);
                iv2.setImageResource(resList.get(1));
                iv2.setVisibility(VISIBLE);
                iv3.setImageResource(resList.get(2));
                iv3.setVisibility(VISIBLE);
                iv4.setImageResource(resList.get(3));
                iv4.setVisibility(VISIBLE);
                break;
            }
        }
    }
}
