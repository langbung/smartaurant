package com.smartaurant_kmutt.smartaurant.fragment.owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.owner.OwnerDetailOrderListActivity;
import com.smartaurant_kmutt.smartaurant.adapter.OrderListAdapter;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.manager.OrderListManager;
import com.smartaurant_kmutt.smartaurant.util.Loading;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@SuppressWarnings("unused")
public class OwnerRevenueListFragment extends Fragment {
    ListView lvRevenue;
    OrderListManager orderListManager;
    OrderListAdapter orderListAdapter;
    TextView tvTotal;
    float total;
    Loading loading = Loading.newInstance();
    OrderItemDao orderItemDao;
    public OwnerRevenueListFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OwnerRevenueListFragment newInstance() {
        OwnerRevenueListFragment fragment = new OwnerRevenueListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_revenue, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        getActivity().setTitle("Revenue");
        initListViewRevenue(rootView);
        tvTotal = rootView.findViewById(R.id.tvTotal);

    }

    private void initListViewRevenue(View rootView) {
        lvRevenue = rootView.findViewById(R.id.lvRevenue);
        orderListAdapter = new OrderListAdapter();
        orderListManager = new OrderListManager();
        lvRevenue.setAdapter(orderListAdapter);
        setOrderRealTime();
        lvRevenue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orderItemDao = orderListManager.getOrderList().get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("orderItemDao",orderItemDao);
                Intent intent = new Intent(getActivity(), OwnerDetailOrderListActivity.class);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
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

    private void setOrderRealTime() {
        loading.show(getFragmentManager(), "l");
        DatabaseReference orderDatabase = UtilDatabase.getDatabase().child("order");
        orderDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<OrderItemDao> orderList = new ArrayList<>();
                for (DataSnapshot orderItem : dataSnapshot.getChildren()) {
//                    OrderItemDao orderItemDao = orderItemDao.getValue(OrderItemDao.class);
                    Map<String, String> date = new HashMap<>();
                    DataSnapshot dateData = orderItem.child("dateTime");
                    date.put("day", dateData.child("day").getValue(String.class));
                    date.put("month", dateData.child("month").getValue(String.class));
                    date.put("time", dateData.child("time").getValue(String.class));
                    date.put("year", dateData.child("year").getValue(String.class));
//
                    orderItemDao = new OrderItemDao();
                    orderItemDao.setOrderId(orderItem.child("orderId").getValue(String.class));
                    orderItemDao.setTable(orderItem.child("table").getValue(Integer.class));
                    orderItemDao.setTotal(orderItem.child("total").getValue(Float.class));
                    orderItemDao.setDateTime(date);
                    total += orderItemDao.getTotal();
                    orderList.add(orderItemDao);
                }
                String totalText = String.format(Locale.ENGLISH, "%.2f", total);
                tvTotal.setText(totalText);
                orderListManager.setOrderList(orderList);
                orderListAdapter.setOrderListManager(orderListManager);
                orderListAdapter.notifyDataSetChanged();
                loading.dismiss();
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
