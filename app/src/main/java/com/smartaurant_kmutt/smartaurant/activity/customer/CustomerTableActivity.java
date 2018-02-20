package com.smartaurant_kmutt.smartaurant.activity.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerTableFragment;

public class CustomerTableActivity extends AppCompatActivity implements CustomerTableFragment.FragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_table);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, CustomerTableFragment.newInstance()).commit();
        }
    }

    @Override
    public void onTableItemClicked(String table) {
        Intent intent = new Intent(CustomerTableActivity.this,CustomerActivity.class);
        intent.putExtra("table",table);
        startActivity(intent);


    }

}
