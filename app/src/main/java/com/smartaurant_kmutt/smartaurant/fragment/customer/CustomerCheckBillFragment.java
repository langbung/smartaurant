package com.smartaurant_kmutt.smartaurant.fragment.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;
import com.smartaurant_kmutt.smartaurant.adapter.CustomerOrderListAdapter;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuListDao;
import com.smartaurant_kmutt.smartaurant.dao.TableItemDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.customer.OrderDialogFragment;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuOnlyManager;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CustomerCheckBillFragment extends Fragment implements YesNoDialog.OnYesNoDialogListener,OrderDialogFragment.OnOrderDialogListener {
    OrderItemDao orderItemDao;
    ListView lvOrder;
    TextView tvTotal;
    OrderMenuOnlyManager orderMenuOnlyManager;
    CustomerOrderListAdapter customerOrderListAdapter;
    DatabaseReference orderListDatabase;
    OrderMenuItemDao orderItem;
    ArrayList<OrderMenuItemDao> orderList;
    int countDatabase;
    float total;
    int table;
    DatabaseReference tableDatabase;
    public CustomerCheckBillFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CustomerCheckBillFragment newInstance(Bundle bundle) {
        CustomerCheckBillFragment fragment = new CustomerCheckBillFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_customer_check_bill, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
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
//            MyUtil.showText(orderItemDao.getOrderId());
            orderListDatabase = UtilDatabase.getDatabase().child("order/" + orderItemDao.getOrderId())
                    .child("orderList");
            orderListDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.e("123",dataSnapshot.toString());
//                    MyUtil.showText(dataSnapshot.toString());

//                    Log.e("123","data = "+dataSnapshot.getChildrenCount()+"");
                    initial();
                    for(DataSnapshot order:dataSnapshot.getChildren()){
                        Log.e("check bill",dataSnapshot.getChildrenCount()+"");
                        orderItem = new OrderMenuItemDao();
                        orderItem.setName(order.child("name").getValue(String.class));
                        orderItem.setQuantity(order.child("quantity").getValue(Integer.class));
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
                                OrderMenuListDao orderMenuListDao = new OrderMenuListDao();
                                orderMenuListDao.setOrderList(orderList);
                                orderMenuOnlyManager.setOrderMenuDao(orderMenuListDao);
                                customerOrderListAdapter.setOrderMenuOnlyManager(orderMenuOnlyManager);
                                customerOrderListAdapter.notifyDataSetChanged();
                                DatabaseReference orderDatabase = UtilDatabase.getDatabase().child("order/"+orderItemDao.getOrderId()+"/total");
                                orderDatabase.setValue(total);
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

    @Override
    public void onYesButtonClickInYesNODialog(Bundle bundle) {
        MyUtil.showText("Check bill success");
        changeMaxOrderAndStartActivityAgain();

    }

    private void startThisActivity() {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        startActivity(intent);
    }

    private void changeMaxOrderAndStartActivityAgain() {
        DatabaseReference maxOrderDatabase = UtilDatabase.getUtilDatabase().child("maxOrderId");
        maxOrderDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference maxOrderDatabase = UtilDatabase.getUtilDatabase().child("maxOrderId");
                int maxOrder = dataSnapshot.getValue(Integer.class);
                maxOrder++;
                maxOrderDatabase.setValue(maxOrder).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            setTableToNoOrder();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void setTableToNoOrder(){
        String tableIDText = String.format(Locale.ENGLISH,"TB%03d",table);
        tableDatabase = UtilDatabase.getDatabase().child("table/"+tableIDText);

        tableDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TableItemDao tableItemDao = dataSnapshot.getValue(TableItemDao.class);
                tableItemDao.setAvailableTable(true);
                tableItemDao.setOrderId("none");
                tableDatabase.setValue(tableItemDao).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startThisActivity();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNoButtonClickInYesNODialog(Bundle bundle) {
        MyUtil.showText("Cancel check bill");
    }

    @Override
    public void onOrderClick(Bundle bundle) {

    }
}
