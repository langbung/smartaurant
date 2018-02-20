package com.smartaurant_kmutt.smartaurant.activity.owner;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerFragment;
import com.smartaurant_kmutt.smartaurant.util.PopupLogout;

public class OwnerActivity extends AppCompatActivity implements PopupLogout.OnSubmitClickListener {
    DrawerLayout drawerLayout;
    Button btRevenue;
    Button btMenusetting;
    Button btStaffManagement;
    Button btLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        initInstance();
        if(savedInstanceState==null)
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, OwnerFragment.newInstance(0)).commit();
    }

    private void initInstance() {
        btRevenue=(Button)findViewById(R.id.btRevenue);
        btMenusetting=(Button)findViewById(R.id.btMenuSetting);
        btStaffManagement=(Button)findViewById(R.id.btStaffManagement);
        btLogout=(Button)findViewById(R.id.btLogOut);
        btLogout.setOnClickListener(onClickListener);
        btRevenue.setOnClickListener(onClickListener);
        btMenusetting.setOnClickListener(onClickListener);
        btStaffManagement.setOnClickListener(onClickListener);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
    }

    View.OnClickListener onClickListener = new View.OnClickListener()  {
        @Override
        public void onClick(View v) {

            if(v==btRevenue){
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, OwnerFragment.newInstance(0)).commit();
                drawerLayout.closeDrawers();

            }else if(v==btMenusetting){
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, OwnerFragment.newInstance(1)).commit();
                drawerLayout.closeDrawers();

            }
            else if(v==btStaffManagement){
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, OwnerFragment.newInstance(2)).commit();
                drawerLayout.closeDrawers();

            }if(v==btLogout){
                PopupLogout popupLogout = new PopupLogout();
                popupLogout.show(getSupportFragmentManager(),"popup");
            }
        }
    };

    @Override
    public void onSubmitClicked(String email, String password) {
        Intent intent = new Intent(OwnerActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
