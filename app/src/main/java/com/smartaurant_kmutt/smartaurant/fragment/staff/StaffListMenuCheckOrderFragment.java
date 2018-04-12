package com.smartaurant_kmutt.smartaurant.fragment.staff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.CustomerOrderListAdapter;
import com.smartaurant_kmutt.smartaurant.adapter.OrderMenuOnlyAdapter;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuListDao;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuOnlyManager;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.Locale;


@SuppressWarnings("unused")
public class StaffListMenuCheckOrderFragment extends Fragment {
    Toolbar toolbar;
    TextView tvTotal;
    int table;
    ListView lvListMenu;
    OrderMenuKitchenManager orderMenuManager;
    CustomerOrderListAdapter orderMenuOnlyAdapter;
    String orderId;
    ArrayList<OrderMenuItemDao> orderList;
    int countDatabase;
    float total;

    public StaffListMenuCheckOrderFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static StaffListMenuCheckOrderFragment newInstance(Bundle bundle) {
        StaffListMenuCheckOrderFragment fragment = new StaffListMenuCheckOrderFragment();
        Bundle args = new Bundle();
        args.putBundle("bundle", bundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        table = getArguments().getBundle("bundle").getInt("table");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_staff_check_order, container, false);
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
        initListView(rootView);
        tvTotal = rootView.findViewById(R.id.tvTotal);
    }

    private void initListView(View rootView) {
        lvListMenu = rootView.findViewById(R.id.lvListMenu);
        orderMenuManager = new OrderMenuKitchenManager();
        orderMenuOnlyAdapter = new CustomerOrderListAdapter(CustomerOrderListAdapter.MODE_ORDER_KITCHEN_STAFF);
        lvListMenu.setAdapter(orderMenuOnlyAdapter);
        setOrderRealTime();
    }

    private void initToolbar(View rootView) {
        toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Order table: " + table);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
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

    void setOrderRealTime() {
        String tableID = String.format(Locale.ENGLISH,"TB%03d",table);
        Log.e("setOrderRealTime",tableID);
        DatabaseReference tableDatabase = UtilDatabase.getDatabase().child("table/"+tableID+"/orderId");
        tableDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderId = dataSnapshot.getValue(String.class);
                DatabaseReference orderMenuListDatabase = UtilDatabase.getDatabase().child("order_kitchen");
                Query query = orderMenuListDatabase.orderByChild("orderId").equalTo(orderId);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<OrderMenuKitchenItemDao> orderKitchenList = new ArrayList<>();
                        for (DataSnapshot test : dataSnapshot.getChildren()){
                            orderKitchenList.add(test.getValue(OrderMenuKitchenItemDao.class));
                        }
                        orderMenuManager.setOrderMenuKitchenDao(orderKitchenList);
                        orderMenuOnlyAdapter.setOrderMenuKitchenManager(orderMenuManager);
                        orderMenuOnlyAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

}
