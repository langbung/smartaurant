package com.smartaurant_kmutt.smartaurant.activity.owner;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.owner.OptionsOwnerMenuDialog;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.owner.OptionsStaffDialog;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.PopupLogout;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerListMenuFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerRevenueListFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerSettingFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerStaffManagementFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerVoucherFragment;

public class OwnerActivity extends AppCompatActivity implements PopupLogout.OnPopupLogoutClicked
        , OwnerListMenuFragment.OnOwnerListMenuFragmentListener
        , OptionsOwnerMenuDialog.OnOptionsMenuDialogListener
        , OwnerStaffManagementFragment.OnOwnerStaffManagementListener
        , OptionsStaffDialog.OnOptionsStaffDialogListener {
    DrawerLayout drawerLayout;
    Button btRevenue;
    Button btMenuSetting;
    Button btStaffManagement;
    Button btLogout;
    Button btSetting;
    Button btVoucher;
    android.support.v7.widget.Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FrameLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        initInstance();
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, OwnerRevenueListFragment.newInstance()).commit();
    }

    private void initInstance() {
        initToolbar();
        initButton();
        initFrameLayout();
    }

    private void initFrameLayout() {
        loading = findViewById(R.id.loading);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                OwnerActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initButton() {
        btRevenue = findViewById(R.id.btRevenue);
        btMenuSetting = findViewById(R.id.btMenuSetting);
        btStaffManagement = findViewById(R.id.btStaffManagement);
        btLogout = findViewById(R.id.btLogOut);
        btSetting = findViewById(R.id.btSetting);
        btVoucher = findViewById(R.id.btVoucher);

        btLogout.setOnClickListener(onClickListener);
        btRevenue.setOnClickListener(onClickListener);
        btMenuSetting.setOnClickListener(onClickListener);
        btStaffManagement.setOnClickListener(onClickListener);
        btSetting.setOnClickListener(onClickListener);
        btVoucher.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v == btRevenue) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, OwnerRevenueListFragment.newInstance()).commit();
                drawerLayout.closeDrawers();

            } else if (v == btMenuSetting) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, OwnerListMenuFragment.newInstance()).commit();
                drawerLayout.closeDrawers();

            } else if (v == btStaffManagement) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, OwnerStaffManagementFragment.newInstance()).commit();
                drawerLayout.closeDrawers();

            } else if (v == btSetting) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, OwnerSettingFragment.newInstance()).commit();
                drawerLayout.closeDrawers();

            }
            else if (v == btVoucher) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, OwnerVoucherFragment.newInstance()).commit();
                drawerLayout.closeDrawers();
            }
            if (v == btLogout) {
                PopupLogout popupLogout = new PopupLogout();
                popupLogout.show(getSupportFragmentManager(), "popup");
            }
        }
    };

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
            Intent intent = new Intent(OwnerActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBtAddMenuFloatClick(Bundle bundle) {
        Intent intent = new Intent(OwnerActivity.this, OwnerEditMenuActivity.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    @Override
    public void onSelectEditMenuOptionDialog(Bundle bundle) {
        Intent intent = new Intent(OwnerActivity.this, OwnerEditMenuActivity.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    @Override
    public void onAddStaffButtonClick(Bundle bundle) {
        Intent intent = new Intent(OwnerActivity.this, OwnerEditStaffActivity.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    @Override
    public void onSelectEditStaffOptionDialog(Bundle bundle) {
        Intent intent = new Intent(OwnerActivity.this, OwnerEditStaffActivity.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    public void setLoading(boolean enabled) {
        if (enabled)
            loading.setVisibility(View.VISIBLE);
        else
            loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
