package com.smartaurant_kmutt.smartaurant.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.customer.CustomerActivity;
import com.smartaurant_kmutt.smartaurant.activity.customer.CustomerTableActivity;
import com.smartaurant_kmutt.smartaurant.fragment.MenuFragment;

public class MenuActivity extends AppCompatActivity implements MenuFragment.FragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        checkUserOut();
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer,MenuFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onBtCustomerClicked() {
        Intent intent = new Intent(MenuActivity.this,CustomerTableActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBtStaffClicked() {
        Intent intent = new Intent(MenuActivity.this,StaffLoginActivity.class);
        startActivity(intent);


    }
    private void checkUserOut(){
        SharedPreferences prefs=MenuActivity.this.getSharedPreferences("checkUser", Context.MODE_PRIVATE);
        Boolean userOut = prefs.getBoolean("userOut",false);
        if(userOut==true){
            int numTable = prefs.getInt("numTable",0);
            Intent intent = new Intent(MenuActivity.this,CustomerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("numTable",numTable);
            intent.putExtra("bundle",bundle);
            startActivity(intent);

        }
    }
}
