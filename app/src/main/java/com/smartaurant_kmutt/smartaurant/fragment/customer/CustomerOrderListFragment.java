package com.smartaurant_kmutt.smartaurant.fragment.customer;

import android.os.Bundle;
import android.service.autofill.Dataset;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.OrderMenuAdapterCustomer;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuListDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.customer.OrderDialogFragment;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuManager;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CustomerOrderListFragment extends Fragment {
    OrderItemDao orderItemDao;
    ListView lvOrder;
    TextView tvTotal;
    OrderMenuManager orderMenuManager;
    OrderMenuAdapterCustomer orderMenuAdapterCustomer;

    OrderMenuItemDao orderItem;
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
        tvTotal=rootView.findViewById(R.id.tvTotal);
        lvOrder= rootView.findViewById(R.id.lvOrder);

        orderMenuAdapterCustomer = new OrderMenuAdapterCustomer();
        orderMenuManager = new OrderMenuManager();
        setOrderRealTime();
        lvOrder.setAdapter(orderMenuAdapterCustomer);

    }

    void setOrderRealTime(){
        if(orderItemDao!=null) {
            DatabaseReference orderDatabase = UtilDatabase.getDatabase().child("order/" + orderItemDao.getOrderId())
                    .child("orderList");
            orderDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.e("123",dataSnapshot.toString());
//                    MyUtil.showText(dataSnapshot.toString());
                    orderList = new ArrayList<>();
                    Log.e("123","data = "+dataSnapshot.getChildrenCount()+"");
                    for(DataSnapshot order:dataSnapshot.getChildren()){
                        orderItem = new OrderMenuItemDao();
                        orderItem.setName(order.getKey());
                        orderItem.setQuantity(order.getValue(Integer.class));
//                        orderList.add(orderItem);
                        Log.e("123",order.toString()+"    "+orderItem.getName()+ orderItem.getQuantity());
                        DatabaseReference priceMenuDatabase = UtilDatabase.getMenu()
                                .child(orderItem.getName())
                                .child("price");
                        Log.e("123",priceMenuDatabase.toString());
                        priceMenuDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot price) {
                                orderItem.setPrice(price.getValue(Float.class));
                                Log.e("123",orderItem.getName()+" จำนวน "+orderItem.getQuantity()+" ราคา "+orderItem.getPrice());
                                orderList.add(orderItem);
//                                MyUtil.showText(orderItem.getName()+" จำนวน "+orderItem.getQuantity()+" ราคา "+orderItem.getPrice());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    OrderMenuListDao orderMenuListDao = new OrderMenuListDao();
                    orderMenuListDao.setOrderList(orderList);
                    orderMenuManager.setOrderMenuDao(orderMenuListDao);
                    orderMenuAdapterCustomer.setOrderMenuManager(orderMenuManager);
                    orderMenuAdapterCustomer.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


}
