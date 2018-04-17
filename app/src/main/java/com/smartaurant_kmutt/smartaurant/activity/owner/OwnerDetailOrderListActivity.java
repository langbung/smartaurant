package com.smartaurant_kmutt.smartaurant.activity.owner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerRevenueOrderListFragment;

public class OwnerDetailOrderListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_detail_order_list);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if(savedInstanceState==null)
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, OwnerRevenueOrderListFragment.newInstance(bundle)).commit();
    }
}
