package com.smartaurant_kmutt.smartaurant.activity.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerMenuFragment;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.customer.OrderDialogFragment;

public class CustomerMenuActivity extends AppCompatActivity implements OrderDialogFragment.OnOrderDialogListener {
    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menu);
        initInstance();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if(savedInstanceState==null)
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer,CustomerMenuFragment.newInstance(bundle)).commit();
    }

    private void initInstance() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onOrderClick(Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.putExtra("bundle",bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
