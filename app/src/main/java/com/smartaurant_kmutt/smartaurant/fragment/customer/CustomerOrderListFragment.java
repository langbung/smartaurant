package com.smartaurant_kmutt.smartaurant.fragment.customer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.CustomerOrderListAdapter;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CustomerOrderListFragment extends Fragment {
    OrderItemDao orderItemDao;
    ListView lvOrder;
    OrderMenuKitchenManager orderMenuKitchenManager;
    CustomerOrderListAdapter customerOrderListAdapter;
    DatabaseReference orderMenuKitchenDatabase;
    ArrayList<OrderMenuItemDao> orderList;
    public CustomerOrderListFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CustomerOrderListFragment newInstance(Bundle bundle) {
        CustomerOrderListFragment fragment = new CustomerOrderListFragment();
        Bundle args = new Bundle();
        args.putBundle("bundle",bundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        orderItemDao = getArguments().getBundle("bundle").getParcelable("orderItemDao");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_order_list, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
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

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        lvOrder= rootView.findViewById(R.id.lvOrder);

        customerOrderListAdapter = new CustomerOrderListAdapter(CustomerOrderListAdapter.MODE_ORDER_KITCHEN_CUSTOMER);
        orderMenuKitchenManager = new OrderMenuKitchenManager();
        setOrderRealTime();
        lvOrder.setAdapter(customerOrderListAdapter);

    }
    void initial(){
        orderList = new ArrayList<>();

    }
    void setOrderRealTime(){
        if(orderItemDao!=null) {
            orderMenuKitchenDatabase = UtilDatabase.getDatabase().child("order_kitchen");
            Query orderListQuery = orderMenuKitchenDatabase.orderByChild("orderId").equalTo(orderItemDao.getOrderId());
            orderListQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.e("123",dataSnapshot.toString());
//                    MyUtil.showText(dataSnapshot.toString());

//                    Log.e("123","data = "+dataSnapshot.getChildrenCount()+"");
                    initial();
                    ArrayList<OrderMenuKitchenItemDao> orderKitchenList = new ArrayList<>();
                    for (DataSnapshot test : dataSnapshot.getChildren()){
                        orderKitchenList.add(test.getValue(OrderMenuKitchenItemDao.class));
                    }

                    orderMenuKitchenManager.setOrderMenuKitchenDao(orderKitchenList);
                    customerOrderListAdapter.setOrderMenuKitchenManager(orderMenuKitchenManager);
                    customerOrderListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


}
