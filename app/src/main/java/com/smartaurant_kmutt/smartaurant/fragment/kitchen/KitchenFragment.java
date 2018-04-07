package com.smartaurant_kmutt.smartaurant.fragment.kitchen;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.CustomerOrderListAdapter;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("unused")
public class KitchenFragment extends Fragment {
    Toolbar toolbar;
    OrderMenuKitchenManager orderMenuKitchenManager;
    ListView lvAllMenuKitchen;
    CustomerOrderListAdapter orderListAdapter;
    public KitchenFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static KitchenFragment newInstance() {
        KitchenFragment fragment = new KitchenFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_kitchen, container, false);
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
        initListViewOrderMenuKitchen(rootView);
    }

    private void initListViewOrderMenuKitchen(View rootView) {
        lvAllMenuKitchen = rootView.findViewById(R.id.lvAllMenuKitchen);
        orderMenuKitchenManager = new OrderMenuKitchenManager();
        orderListAdapter= new CustomerOrderListAdapter(CustomerOrderListAdapter.MODE_ORDER_KITCHEN_CUSTOMER);
        setOrderRealTime();
        lvAllMenuKitchen.setAdapter(orderListAdapter);
        lvAllMenuKitchen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
                String orderKitchenId = orderMenuKitchenItemDao.getOrderKitchenId();
                String status = orderMenuKitchenItemDao.getStatus();
                if(status.equals("in queue"))
                    status="cooking";
                else if(status.equals("cooking"))
                    status="cooked";
                else if(status.equals("cooked"))
                    status="in queue";

                Map<String,Object> updateChild = new HashMap<>();
                updateChild.put("order_kitchen/"+orderKitchenId+"/status",status);

                DatabaseReference databaseReference = UtilDatabase.getDatabase();
                databaseReference.updateChildren(updateChild);
            }
        });

    }

    private void initToolbar(View rootView) {
        toolbar=rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Kitchen");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    void setOrderRealTime(){

            DatabaseReference orderMenuKitchenDatabase = UtilDatabase.getDatabase().child("order_kitchen");
            orderMenuKitchenDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.e("123",dataSnapshot.toString());
//                    MyUtil.showText(dataSnapshot.toString());

//                    Log.e("123","data = "+dataSnapshot.getChildrenCount()+"");
//                    MyUtil.showText(dataSnapshot.getChildrenCount()+"");
                    ArrayList<OrderMenuKitchenItemDao> orderKitchenList = new ArrayList<>();
                    for (DataSnapshot test : dataSnapshot.getChildren()){

                        OrderMenuKitchenItemDao orderMenuKitchenItemDao = new OrderMenuKitchenItemDao();

                        orderMenuKitchenItemDao.setOrderId(test.child("orderId").getValue(String.class));
                        orderMenuKitchenItemDao.setMenuName(test.child("menuName").getValue(String.class));
                        orderMenuKitchenItemDao.setQuantity(test.child("quantity").getValue(Integer.class));
                        orderMenuKitchenItemDao.setStatus(test.child("status").getValue(String.class));
                        orderMenuKitchenItemDao.setOrderKitchenId(test.getKey());

                        orderKitchenList.add(orderMenuKitchenItemDao);
                    }
                    orderMenuKitchenManager.setOrderMenuKitchenDao(orderKitchenList);
                    orderListAdapter.setOrderMenuKitchenManager(orderMenuKitchenManager);
                    orderListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

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

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

}
