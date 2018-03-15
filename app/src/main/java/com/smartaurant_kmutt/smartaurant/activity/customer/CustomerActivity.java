package com.smartaurant_kmutt.smartaurant.activity.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerFragment;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.PopupLogout;

public class CustomerActivity extends AppCompatActivity implements PopupLogout.OnPopupLogoutClicked {
    String table;
    Button btLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        setTable(getIntent().getStringExtra("table"));
        initInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, CustomerFragment.newInstance(getTable())).commit();
        }
    }

    private void initInstance() {
        btLogOut = (Button) findViewById(R.id.btLogOut);
        btLogOut.setOnClickListener(onClickListener);
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


    public void writUserOut(Boolean result) {
        SharedPreferences prefs = CustomerActivity.this.getSharedPreferences("checkUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("userOut", result);
        editor.putString("table", table);
        editor.apply();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btLogOut) {
                PopupLogout popupLogout = new PopupLogout();
                popupLogout.show(getSupportFragmentManager(),"ok");
            }
        }
    };


    @Override
    public void onPopupLogoutClick(String email, String password) {
        writUserOut(false);
        Intent intent = new Intent(CustomerActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
