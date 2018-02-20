package com.smartaurant_kmutt.smartaurant.fragment.customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.SlidingTabLayout;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.CustomerPagerAdapter;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CustomerFragment extends Fragment {
    boolean userOut;
    CustomerPagerAdapter customerPagerAdapter;
    String table;
    SlidingTabLayout slidingTab;
    android.support.v7.widget.Toolbar toolbar;
    ViewPager viewPager;
    Boolean checkUserLogout;
    Button btLogOut;

    public CustomerFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CustomerFragment newInstance(String table) {
        CustomerFragment fragment = new CustomerFragment();
        Bundle args = new Bundle();
//        Toast.makeText(Contextor.getInstance().getContext(),table,Toast.LENGTH_SHORT).show();
        args.putString("table",table);
        fragment.setArguments(args);
        return fragment;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTable(getArguments().getString("table"));
        writUserOut(true);
        init(savedInstanceState);
        //Toast.makeText(getContext(),getTable(),Toast.LENGTH_SHORT).show();

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
        toolbar= (android.support.v7.widget.Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getTable());
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        TextView tv=new TextView(rootView.getContext());
        customerPagerAdapter=new CustomerPagerAdapter(getChildFragmentManager());
        viewPager=(ViewPager)rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(customerPagerAdapter);
        slidingTab=(SlidingTabLayout)rootView.findViewById(R.id.slidingTab);
        setSlidingTab(slidingTab,rootView);


    }

    private void setSlidingTab(SlidingTabLayout slidingTab,View rootView){

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
    public void writUserOut(Boolean result){
        SharedPreferences prefs=getContext().getSharedPreferences("checkUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= prefs.edit();
        editor.putBoolean("userOut",result);
        editor.putString("table",table);
        editor.apply();
    }



}
