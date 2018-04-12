package com.smartaurant_kmutt.smartaurant.fragment.kitchen;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.CustomerOrderListAdapter;
import com.smartaurant_kmutt.smartaurant.adapter.KitchenOrderListAdapter;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.kitchen.KitchenOrderDialog;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("unused")
public class KitchenFragment extends Fragment implements KitchenOrderDialog.KitchenOrderDialogListener {
    Toolbar toolbar;
    OrderMenuKitchenManager orderMenuKitchenManager;
    ListView lvAllMenuKitchen;
    KitchenOrderListAdapter orderListAdapter;
    ArrayList<OrderMenuKitchenItemDao> orderKitchenList;
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
        orderListAdapter= new KitchenOrderListAdapter();
        setOrderRealTime();
        lvAllMenuKitchen.setAdapter(orderListAdapter);
        lvAllMenuKitchen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
                    Log.e("Kitchen fragment","note = " +orderMenuKitchenItemDao.getNote());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("orderMenuKitchenItemDao",orderMenuKitchenItemDao);
                    KitchenOrderDialog kitchenOrderDialog = KitchenOrderDialog.newInstance(bundle);
                    kitchenOrderDialog.setTargetFragment(KitchenFragment.this,123);
                    kitchenOrderDialog.show(getFragmentManager(),"kitchenOrder");
                }
            }
        });

    }



    private void updateStatus(String orderKitchenId, String status) {
        Map<String,Object> updateChild = new HashMap<>();
        updateChild.put("order_kitchen/"+orderKitchenId+"/status",status);

        DatabaseReference databaseReference = UtilDatabase.getDatabase();
        databaseReference.updateChildren(updateChild);
    }

    private void initToolbar(View rootView) {
        toolbar=rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Kitchen");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    void setOrderRealTime(){
            final DatabaseReference orderMenuKitchenDatabase = UtilDatabase.getDatabase().child("order_kitchen");
            Query orderMenuKitchenQuery = orderMenuKitchenDatabase.orderByChild("status").equalTo("cooking");
            orderMenuKitchenQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.e("123",dataSnapshot.toString());
//                    MyUtil.showText(dataSnapshot.toString());
//                    Log.e("123","data = "+dataSnapshot.getChildrenCount()+"");
//                    MyUtil.showText(dataSnapshot.getChildrenCount()+"");
                    orderKitchenList = new ArrayList<>();
                    for (DataSnapshot test : dataSnapshot.getChildren()){
                        OrderMenuKitchenItemDao orderMenuKitchenItemDao = new OrderMenuKitchenItemDao();

                        orderMenuKitchenItemDao.setNote(test.child("note").getValue(String.class));
                        orderMenuKitchenItemDao.setOrderId(test.child("orderId").getValue(String.class));
                        orderMenuKitchenItemDao.setMenuName(test.child("menuName").getValue(String.class));
                        orderMenuKitchenItemDao.setQuantity(test.child("quantity").getValue(Integer.class));
                        orderMenuKitchenItemDao.setStatus(test.child("status").getValue(String.class));
                        orderMenuKitchenItemDao.setOrderKitchenId(test.getKey());

                        orderKitchenList.add(orderMenuKitchenItemDao);
                    }
                    DatabaseReference orderMenuKitchenDatabase = UtilDatabase.getDatabase().child("order_kitchen");
                    Query orderMenuKitchenQuery = orderMenuKitchenDatabase.orderByChild("status").equalTo("in queue");
                    orderMenuKitchenQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot test : dataSnapshot.getChildren()){
                                OrderMenuKitchenItemDao orderMenuKitchenItemDao = new OrderMenuKitchenItemDao();
                                orderMenuKitchenItemDao.setNote(test.child("note").getValue(String.class));
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

    void setZero(OrderMenuKitchenItemDao orderMenuKitchenItemDao){
        Map<String,Object> updateZero = new HashMap<>();
        String path = "order_kitchen/"+orderMenuKitchenItemDao.getOrderKitchenId()+"/status";
        updateZero.put(path,"Ready to serve");
        DatabaseReference zeroDatabase = UtilDatabase.getDatabase();
        zeroDatabase.updateChildren(updateZero).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    MyUtil.showText("can't set status");
                }
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

    @Override
    public void onCookButtonClickInKitchenOrderDialog(OrderMenuKitchenItemDao orderMenuKitchenItemDao) {
        setZero(orderMenuKitchenItemDao);
//        MyUtil.showText(orderMenuKitchenItemDao.getStatus());
//        MyUtil.showText(orderMenuKitchenItemDao.getOrderKitchenId());
    }

    @Override
    public void onCloseButtonClickInKitchenOrderDialog() {

    }
}
