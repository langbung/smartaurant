package com.smartaurant_kmutt.smartaurant.fragment.customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.inthecheesefactory.thecheeselibrary.view.SlidingTabLayout;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;
import com.smartaurant_kmutt.smartaurant.activity.customer.CustomerActivity;
import com.smartaurant_kmutt.smartaurant.adapter.CustomerPagerAdapter;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.customer.OrderDialogFragment;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.Locale;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CustomerFragment extends Fragment implements YesNoDialog.OnYesNoDialogListener {
    boolean userOut;
    CustomerPagerAdapter customerPagerAdapter;
    int numTable;
    SlidingTabLayout slidingTab;
    android.support.v7.widget.Toolbar toolbar;
    ViewPager viewPager;
    Boolean checkUserLogout;
    Button btLogOut;
    OrderItemDao orderItemDao;
    OrderKitchenItemDao orderKitchenItemDao;
    boolean countRefresh;
    Menu callWaiter;
    CustomerActivity activity;

    public CustomerFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CustomerFragment newInstance(Bundle bundle) {
        CustomerFragment fragment = new CustomerFragment();
        Bundle args = new Bundle();
        args.putBundle("bundle", bundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numTable = getArguments().getBundle("bundle").getInt("table");
        writUserOut(true);
        init(savedInstanceState);
        //Toast.makeText(getContext(),getTable(),Toast.LENGTH_SHORT).show();
        setHasOptionsMenu(true);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        initToolbar(rootView);
        initViewPager(rootView);

    }

    private void initToolbar(View rootView) {
        toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Table: " + numTable);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initViewPager(View rootView) {
        customerPagerAdapter = new CustomerPagerAdapter(getChildFragmentManager());
        customerPagerAdapter.setTable(numTable);
        viewPager = rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(customerPagerAdapter);
        slidingTab = rootView.findViewById(R.id.slidingTab);
        setSlidingTab(slidingTab, rootView);
        viewPager.setCurrentItem(1);
    }

    private void setSlidingTab(SlidingTabLayout slidingTab, View rootView) {
        slidingTab.setSelectedIndicatorColors(Color.parseColor("#ffffffff"));
        slidingTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        slidingTab.setDistributeEvenly(true);
        slidingTab.setViewPager(viewPager);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }


    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void writUserOut(Boolean result) {
        SharedPreferences prefs = getContext().getSharedPreferences("checkUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("userOut", result);
        editor.putInt("numTable", numTable);
        editor.apply();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.customer_menu, menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (CustomerActivity) getActivity();
    }

    private void checkPay() {
        if (countRefresh) {
            String tableId = String.format(Locale.ENGLISH, "TB%03d", numTable);
            DatabaseReference tableDatabase = UtilDatabase.getDatabase().child("table/" + tableId + "/availableTable");
            tableDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean checkPay = dataSnapshot.getValue(Boolean.class);
                    Log.e("checkpay",checkPay+"");
                    if(checkPay){
                        CustomerFragmentListener customerFragmentListener = activity;
                        customerFragmentListener.onPaidInCustomerFragment();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.waiterCalling) {
            Bundle bundle = new Bundle();
            bundle.putString("title", "Call waiter");
            bundle.putString("detail", "Do you want to call waiter?");
            YesNoDialog yesNoDialog = YesNoDialog.newInstance(bundle);
            yesNoDialog.setTargetFragment(CustomerFragment.this, 2);
            yesNoDialog.show(getFragmentManager(), "yesNoDialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setOrderItemDao(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
        if (!countRefresh) {
            customerPagerAdapter.setOrderItemDao(orderItemDao);
            int page = viewPager.getCurrentItem();
            viewPager.setAdapter(customerPagerAdapter);
            viewPager.setCurrentItem(page);
            countRefresh = true;
            checkPay();
//            MyUtil.showText("checkPay run");
        }
    }

    @Override
    public void onYesButtonClickInYesNODialog(Bundle bundle, int requestCode) {
        String tableId = String.format(Locale.ENGLISH, "TB%03d", numTable);
        DatabaseReference tableDatabase = UtilDatabase.getDatabase().child("table/").child(tableId + "/availableToCallWaiter");
        tableDatabase.setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    MyUtil.showText("please wait for waiter.");
                }
            }
        });
    }

    @Override
    public void onNoButtonClickInYesNODialog(Bundle bundle, int requestCode) {
        MyUtil.showText("cancel call waiter");
    }
    public interface CustomerFragmentListener{
        void onPaidInCustomerFragment();
    }
}
