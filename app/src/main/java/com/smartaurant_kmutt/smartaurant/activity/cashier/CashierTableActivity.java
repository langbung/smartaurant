package com.smartaurant_kmutt.smartaurant.activity.cashier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.fragment.cashier.CashierTableFragment;

public class CashierTableActivity extends AppCompatActivity implements CashierTableFragment.FragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_table);

        if(savedInstanceState==null)
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, CashierTableFragment.newInstance()).commit();

    }

    @Override
    public void onItemClicked(String table) {
        Intent intent = new Intent(CashierTableActivity.this,CashierActivity.class);
        intent.putExtra("table",table);
        startActivity(intent);
    }
}
