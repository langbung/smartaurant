package com.smartaurant_kmutt.smartaurant.activity.staff;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;
import com.smartaurant_kmutt.smartaurant.activity.cashier.CashierTableActivity;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.PopupLogout;
import com.smartaurant_kmutt.smartaurant.fragment.staff.StaffFragment;

public class StaffActivity extends AppCompatActivity implements PopupLogout.OnPopupLogoutClicked {
    Button btCheckOrder;
    Button btMenuSetting;
    Button btLogOut;
    Button btCustomerDetail;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        initInstance();
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, StaffFragment.newInstance(bundle)).commit();
        }
    }

    private void initInstance() {
        initToolbar();
        initDrawerLayout();
        initButton();
    }

    private void initButton() {
        btCustomerDetail = findViewById(R.id.btCustomerDetail);
        btMenuSetting = findViewById(R.id.btMenuSetting);
        btLogOut = findViewById(R.id.btLogOut);
        btCustomerDetail.setOnClickListener(buttonOnClickListener);
        btMenuSetting.setOnClickListener(buttonOnClickListener);
        btLogOut.setOnClickListener(buttonOnClickListener);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void initDrawerLayout() {
        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(StaffActivity.this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void setCurrentPageInViewPager(int currentPage) {
        Bundle bundle = new Bundle();
        bundle.putInt("currentPage", currentPage);
//        if(currentPage==0)
//            bundle.putString("title","Customer call");
//        else
        if (currentPage == 0)
            bundle.putString("title", "Customer detail");
        else if (currentPage == 1)
            bundle.putString("title", "Menu setting");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, StaffFragment.newInstance(bundle))
                .commit();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPopupLogoutClick(String staffRole) {
        if (!staffRole.equals("none")) {
            Intent intent = new Intent(StaffActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if(v== btCheckOrder){
//                setCurrentPageInViewPager(0);
//                drawerLayout.closeDrawers();
//            }
//            else
            if (v == btMenuSetting) {
                setCurrentPageInViewPager(1);
                drawerLayout.closeDrawers();
            } else if (v == btCustomerDetail) {
                setCurrentPageInViewPager(0);
                drawerLayout.closeDrawers();
            }
            if (v == btLogOut) {
                PopupLogout popupLogout = new PopupLogout();
                popupLogout.show(getSupportFragmentManager(), "popup");
            }
        }
    };

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
