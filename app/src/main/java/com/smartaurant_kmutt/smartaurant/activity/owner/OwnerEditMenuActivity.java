package com.smartaurant_kmutt.smartaurant.activity.owner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerEditMenuFragment;

public class OwnerEditMenuActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_edit_menu);
        initInstance();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if(savedInstanceState==null)
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, OwnerEditMenuFragment.newInstance(bundle)).commit();
    }

    private void initInstance() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
