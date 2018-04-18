package com.smartaurant_kmutt.smartaurant.activity.kitchen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;
import com.smartaurant_kmutt.smartaurant.activity.owner.OwnerActivity;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.PopupLogout;
import com.smartaurant_kmutt.smartaurant.fragment.kitchen.KitchenFragment;

public class KitchenActivity extends AppCompatActivity implements PopupLogout.OnPopupLogoutClicked {
    Button btLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        initInstance();
        if(savedInstanceState==null)
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, KitchenFragment.newInstance()).commit();
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
            Intent intent = new Intent(KitchenActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
