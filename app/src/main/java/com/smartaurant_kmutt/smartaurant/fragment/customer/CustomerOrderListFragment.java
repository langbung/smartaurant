package com.smartaurant_kmutt.smartaurant.fragment.customer;

import android.os.Bundle;
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
import com.smartaurant_kmutt.smartaurant.adapter.CustomerOrderListAdapter;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemOnlyDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuListOnlyDao;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuOnlyManager;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CustomerOrderListFragment extends Fragment {
    OrderItemDao orderItemDao;
    ListView lvOrder;
    TextView tvTotal;
    OrderMenuOnlyManager orderMenuOnlyManager;
    CustomerOrderListAdapter customerOrderListAdapter;
    DatabaseReference orderListDatabase;
    OrderMenuItemOnlyDao orderItem;
    ArrayList<OrderMenuItemOnlyDao> orderList;
    int countDatabase;
    float total;
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

        customerOrderListAdapter = new CustomerOrderListAdapter();
        orderMenuOnlyManager = new OrderMenuOnlyManager();
        setOrderRealTime();
        lvOrder.setAdapter(customerOrderListAdapter);

    }
    void initial(){
        orderList = new ArrayList<>();
        countDatabase = 0;
        total = 0;
    }
    void setOrderRealTime(){
        if(orderItemDao!=null) {
            orderListDatabase = UtilDatabase.getDatabase().child("order/" + orderItemDao.getOrderId())
                    .child("orderList");
            orderListDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.e("123",dataSnapshot.toString());
//                    MyUtil.showText(dataSnapshot.toString());

//                    Log.e("123","data = "+dataSnapshot.getChildrenCount()+"");
                    initial();
                    for(final DataSnapshot order:dataSnapshot.getChildren()){
                        orderItem = new OrderMenuItemOnlyDao();
                        orderItem.setName(order.getKey());
                        orderItem.setQuantity(order.getValue(Integer.class));
                        orderList.add(orderItem);
//                        Log.e("123",order.toString()+"    "+orderItem.getName()+ orderItem.getQuantity());
                        DatabaseReference priceMenuDatabase = UtilDatabase.getMenu()
                                .child(orderItem.getName())
                                .child("price");
//                        Log.e("123",priceMenuDatabase.toString());
                        priceMenuDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot price) {
                                orderList.get(countDatabase).setPrice(orderList.get(countDatabase).getQuantity()*price.getValue(Float.class));
                                Log.e("123",orderList.get(countDatabase).getName()+" จำนวน "+orderList.get(countDatabase).getQuantity()+" ราคา "+orderList.get(countDatabase).getPrice());
                                total+=orderList.get(countDatabase).getPrice();
                                String textTotal = String.format(Locale.ENGLISH,"%.2f",total);
                                tvTotal.setText(textTotal);
                                orderItemDao.setTotal(total);

                                countDatabase++;
                                OrderMenuListOnlyDao orderMenuListOnlyDao = new OrderMenuListOnlyDao();
                                orderMenuListOnlyDao.setOrderList(orderList);
                                orderMenuOnlyManager.setOrderMenuDao(orderMenuListOnlyDao);
                                customerOrderListAdapter.setOrderMenuOnlyManager(orderMenuOnlyManager);
                                customerOrderListAdapter.notifyDataSetChanged();
                                DatabaseReference orderDatabase = UtilDatabase.getDatabase().child("order/"+orderItemDao.getOrderId());
                                orderDatabase.setValue(orderItemDao);
                               }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


}
