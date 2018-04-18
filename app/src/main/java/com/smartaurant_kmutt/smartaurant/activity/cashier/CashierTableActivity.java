package com.smartaurant_kmutt.smartaurant.activity.cashier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;
import com.smartaurant_kmutt.smartaurant.activity.StaffLoginActivity;
import com.smartaurant_kmutt.smartaurant.activity.kitchen.KitchenActivity;
import com.smartaurant_kmutt.smartaurant.fragment.cashier.CashierTableFragment;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.PopupLogout;

public class CashierTableActivity extends AppCompatActivity implements CashierTableFragment.CashierTableFragmentListener,PopupLogout.OnPopupLogoutClicked {
    Button btLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_table);
        initInstance();
        if(savedInstanceState==null)
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, CashierTableFragment.newInstance()).commit();

    }

    @Override
    public void onTableItemClickInCashierTableFragment(Bundle bundle) {
        Intent intent = new Intent(CashierTableActivity.this,CashierActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
    private void initInstance() {
        btLogout = findViewById(R.id.btLogOut);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btLogout) {
                    PopupLogout popupLogout = new PopupLogout();
                    popupLogout.show(getSupportFragmentManager(), "popup");
                }
            }
        });
    }

    @Override
    public void onPopupLogoutClick(String staffRole) {
        if (!staffRole.equals("none")) {
            Intent intent = new Intent(CashierTableActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
