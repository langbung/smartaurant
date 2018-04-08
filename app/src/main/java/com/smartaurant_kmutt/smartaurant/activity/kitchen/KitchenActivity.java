package com.smartaurant_kmutt.smartaurant.activity.kitchen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.fragment.kitchen.KitchenFragment;

public class KitchenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        if(savedInstanceState==null)
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, KitchenFragment.newInstance()).commit();
    }
}