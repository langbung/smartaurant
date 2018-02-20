package com.smartaurant_kmutt.smartaurant.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.cashier.CashierTableActivity;
import com.smartaurant_kmutt.smartaurant.fragment.StaffLoginFragment;

public class StaffLoginActivity extends AppCompatActivity implements StaffLoginFragment.FragmentListener {
    String cashierEmail = "cashier";
    String password = "0000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, StaffLoginFragment.newInstance()).commit();
        }
    }

    @Override
    public void onSubmitClicked(Intent intent) {
        startActivity(intent);
        finish();
    }


//        Toast.makeText(StaffLoginActivity.this,
//                this.cashierEmail +" == "+ email +"\n" +this.password +" == " +password,
//                Toast.LENGTH_LONG ).show();

}
