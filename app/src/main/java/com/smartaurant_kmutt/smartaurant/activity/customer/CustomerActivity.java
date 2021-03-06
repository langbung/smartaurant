package com.smartaurant_kmutt.smartaurant.activity.customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerCategoryFragment;
import com.smartaurant_kmutt.smartaurant.fragment.customer.CustomerFragment;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.PopupLogout;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.customer.OrderDialogFragment;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.Map;

public class CustomerActivity extends AppCompatActivity implements PopupLogout.OnPopupLogoutClicked
        , OrderDialogFragment.OnOrderDialogListener
        , CustomerCategoryFragment.CustomerCategoryFragmentListener
        , CustomerFragment.CustomerFragmentListener {
    int numTable;
    Button btLogOut;
    private OrderItemDao orderItemDao;
    Button btTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        numTable = bundle.getInt("table");
        initInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, CustomerFragment.newInstance(bundle)).commit();
        }
    }

    private void initInstance() {
        btLogOut = findViewById(R.id.btLogOut);
        btLogOut.setOnClickListener(onClickListener);
        btTest = findViewById(R.id.btTest);
        btTest.setOnClickListener(onClickListener);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
//        MyUtil.showText(orderItemDao+"");
    }


    public void writUserOut(Boolean result) {
        SharedPreferences prefs = CustomerActivity.this.getSharedPreferences("checkUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("userOut", result);
        editor.putInt("table", numTable);
        editor.apply();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btLogOut) {
                PopupLogout popupLogout = new PopupLogout();
                popupLogout.show(getSupportFragmentManager(), "ok");
            } else if (v == btTest) {
                if (orderItemDao == null)
                    MyUtil.showText("NO ORDERITEMDAO");
                else
                    MyUtil.showText(orderItemDao.getOrderId());
            }
        }
    };


    @Override
    public void onPopupLogoutClick(String staffRole) {
        if (!staffRole.equals("none")) {
            writUserOut(false);
            exitToMenuActivity();
        }
    }

    void exitToMenuActivity() {
        Intent intent = new Intent(CustomerActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void setOrderItemDao(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    @Override
    public void onOrderClick(Bundle bundle, int requestCode) {
    }

    @Override
    public void onClickCategoryButtonCustomerCategoryFragment(Bundle bundle) {
        Intent intent = new Intent(CustomerActivity.this, CustomerMenuActivity.class);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getBundleExtra("bundle");
                orderItemDao = bundle.getParcelable("orderItemDao");
//                Log.e("CustomerAct",orderItemDao.getDateTime().toString());
                Map<String, String> date = orderItemDao.getDateTime();
//                Log.e("CustomerAct",date.toString());
                CustomerFragment customerFragment = (CustomerFragment) getSupportFragmentManager().findFragmentById(R.id.contentContainer);
                customerFragment.setOrderItemDao(orderItemDao);
            }
        }
    }

    @Override
    public void onPaidInCustomerFragment() {
        Intent intent = new Intent(CustomerActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
