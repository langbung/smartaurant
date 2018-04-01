package com.smartaurant_kmutt.smartaurant.activity.staff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.fragment.staff.StaffListMenuCheckOrderFragment;

public class StaffCheckOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_check_order);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if(savedInstanceState==null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentContainer, StaffListMenuCheckOrderFragment.newInstance(bundle))
                    .commit();
    }
}
