package com.smartaurant_kmutt.smartaurant.activity.owner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerEditStaffFragment;

public class OwnerEditStaffActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_edit_staff);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        initInstance();

        if(savedInstanceState==null)
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, OwnerEditStaffFragment.newInstance(bundle)).commit();
    }

    private void initInstance() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
