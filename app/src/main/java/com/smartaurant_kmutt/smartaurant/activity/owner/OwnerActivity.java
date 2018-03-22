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

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.owner.OptionsOwnerMenuDialog;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.owner.OptionsStaffDialog;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.PopupLogout;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerListMenuFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerRevenueFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerStaffManagementFragment;

public class OwnerActivity extends AppCompatActivity implements PopupLogout.OnPopupLogoutClicked
        ,OwnerListMenuFragment.OnOwnerListMenuFragmentListener
        ,OptionsOwnerMenuDialog.OnOptionsMenuDialogListener
        ,OwnerStaffManagementFragment.OnOwnerStaffManagementListener
        ,OptionsStaffDialog.OnOptionsStaffDialogListener{
    DrawerLayout drawerLayout;
    Button btRevenue;
    Button btMenuSetting;
    Button btStaffManagement;
    Button btLogout;
    android.support.v7.widget.Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        initInstance();
        if(savedInstanceState==null)
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, OwnerRevenueFragment.newInstance()).commit();
    }

    private void initInstance() {
        btRevenue=(Button)findViewById(R.id.btRevenue);
        btMenuSetting =(Button)findViewById(R.id.btMenuSetting);
        btStaffManagement=(Button)findViewById(R.id.btStaffManagement);
        btLogout=(Button)findViewById(R.id.btLogOut);
        btLogout.setOnClickListener(onClickListener);
        btRevenue.setOnClickListener(onClickListener);
        btMenuSetting.setOnClickListener(onClickListener);
        btStaffManagement.setOnClickListener(onClickListener);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(
                OwnerActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    View.OnClickListener onClickListener = new View.OnClickListener()  {
        @Override
        public void onClick(View v) {

            if(v==btRevenue){
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, OwnerRevenueFragment.newInstance()).commit();
                drawerLayout.closeDrawers();

            }else if(v== btMenuSetting){
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, OwnerListMenuFragment.newInstance()).commit();
                drawerLayout.closeDrawers();

            }
            else if(v==btStaffManagement){
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, OwnerStaffManagementFragment.newInstance()).commit();
                drawerLayout.closeDrawers();

            }if(v==btLogout){
                PopupLogout popupLogout = new PopupLogout();
                popupLogout.show(getSupportFragmentManager(),"popup");
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
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPopupLogoutClick(String email, String password) {
        Intent intent = new Intent(OwnerActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBtAddMenuFloatClick() {
        Intent intent = new Intent(OwnerActivity.this, OwnerEditMenuActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title","Add menu");
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    @Override
    public void onSelectEditOption(MenuItemDao menuItemDao) {
        Intent intent = new Intent(OwnerActivity.this, OwnerEditMenuActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("menu",menuItemDao);
        bundle.putString("title","Edit menu");
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    @Override
    public void onAddStaffButtonClick(Bundle bundle) {
        Intent intent = new Intent(OwnerActivity.this,OwnerEditStaffActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    @Override
    public void onSelectEditOption(Bundle bundle) {
        Intent intent = new Intent(OwnerActivity.this,OwnerEditStaffActivity.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
}
