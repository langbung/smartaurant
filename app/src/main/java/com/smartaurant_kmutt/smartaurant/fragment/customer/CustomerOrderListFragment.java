package com.smartaurant_kmutt.smartaurant.fragment.customer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.CustomerOrderListAdapter;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuListDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CustomerOrderListFragment extends Fragment implements YesNoDialog.OnYesNoDialogListener {
    OrderItemDao orderItemDao;
    ListView lvOrder;
    TextView tvTotal;
    OrderMenuKitchenManager orderMenuKitchenManager;
    CustomerOrderListAdapter customerOrderListAdapter;
    DatabaseReference orderMenuKitchenDatabase;
    ArrayList<OrderMenuKitchenItemDao> orderKitchenList;
    MenuItemDao menuItem;
    ValueEventListener valueEventListener;
    int countQuery;
    int pos;
    int countDatabase;
    float total;

    public CustomerOrderListFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CustomerOrderListFragment newInstance(Bundle bundle) {
        CustomerOrderListFragment fragment = new CustomerOrderListFragment();
        Bundle args = new Bundle();
        args.putBundle("bundle", bundle);
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
        Log.e("set order real time","on start");
//        setOrderRealTime();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("set order real time","on stop");
//        if(valueEventListener !=null)
//            UtilDatabase.getDatabase().child("order_kitchen").orderByChild("orderId").equalTo(orderItemDao.getOrderId()).removeEventListener(valueEventListener);
//        Log.e("set order real time","on stop removed listener");
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
     *Restore Instance State Here
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
        initListViewOrder(rootView);
        initTextView(rootView);
    }

    private void initTextView(View rootView) {
        tvTotal = rootView.findViewById(R.id.tvTotal);
    }

    private void initListViewOrder(View rootView) {
        lvOrder = rootView.findViewById(R.id.lvOrder);
        customerOrderListAdapter = new CustomerOrderListAdapter(CustomerOrderListAdapter.MODE_ORDER_KITCHEN_CUSTOMER);
        orderMenuKitchenManager = new OrderMenuKitchenManager();
        setOrderRealTime();
        lvOrder.setAdapter(customerOrderListAdapter);
        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
                if (orderMenuKitchenItemDao.getStatus().equals("in queue")) {
                    YesNoDialog yesNoDialog = YesNoDialog.newInstance("Cancel " + orderMenuKitchenItemDao.getMenuName(), "Do you want to CANCLE " + orderMenuKitchenItemDao.getMenuName());
                    yesNoDialog.setTargetFragment(CustomerOrderListFragment.this, 128);
                    yesNoDialog.show(getFragmentManager(), "cancelMenu");
                }
            }
        });
    }


    void setOrderRealTime() {

        if (orderItemDao != null) {
            countQuery =0;
            orderMenuKitchenDatabase = UtilDatabase.getDatabase().child("order_kitchen");
            final Query orderListQuery = orderMenuKitchenDatabase.orderByChild("orderId").equalTo(orderItemDao.getOrderId());
            valueEventListener = orderListQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    countQuery++;
                    Log.e("set order real time", "queried " + dataSnapshot.toString());

//                    MyUtil.showText(dataSnapshot.toString());
//                    Log.e("123","data = "+dataSnapshot.getChildrenCount()+"");
//                    if(dataSnapshot.exists()){

                    if (dataSnapshot.exists()) {
//                        Log.e("OrderListFragment","data exist");
                        countDatabase = 0;
                        total = 0;
                        orderKitchenList = new ArrayList<>();
                        for (DataSnapshot test : dataSnapshot.getChildren()) {
                            OrderMenuKitchenItemDao orderMenuKitchenItemDao = new OrderMenuKitchenItemDao();
                            orderMenuKitchenItemDao.setNote(test.child("note").getValue(String.class));
                            orderMenuKitchenItemDao.setStatus(test.child("status").getValue(String.class));
                            orderMenuKitchenItemDao.setOrderKitchenId(test.getKey());
                            orderMenuKitchenItemDao.setQuantity(test.child("quantity").getValue(Integer.class));
                            orderMenuKitchenItemDao.setMenuName(test.child("menuName").getValue(String.class));
                            orderMenuKitchenItemDao.setOrderId(test.child("orderId").getValue(String.class));
                            orderKitchenList.add(orderMenuKitchenItemDao);

                            DatabaseReference priceMenuDatabase = UtilDatabase.getMenu()
                                    .child(orderMenuKitchenItemDao.getMenuName());

//                        Log.e("123",priceMenuDatabase.toString());
                            priceMenuDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot menuItemDatabase) {
                                    menuItem = menuItemDatabase.getValue(MenuItemDao.class);
//                                    String show= "menu = " +menuItem.getName() +" price = "+menuItem.getPrice() + " promotion = "+menuItem.getPromotion();
//                                    Log.e("customer order",show);
                                    float price = menuItem.getPrice() * (1 - menuItem.getPromotion() / 100);
//                                    Log.e("customer order",price+"");
                                    Log.e("set order real time", "countDatabase = "+countDatabase );
                                    orderKitchenList.get(countDatabase).setPrice(orderKitchenList.get(countDatabase).getQuantity() * price);
                                    Log.e("set order real time", orderKitchenList.get(countDatabase).getPrice()+"");
//                                Log.e("123", orderKitchenList.get(countDatabase).getMenuName() + " จำนวน " + orderKitchenList.get(countDatabase).getQuantity() + " ราคา " + orderKitchenList.get(countDatabase).getPrice());
                                    total += orderKitchenList.get(countDatabase).getPrice();
//                                    double vat = dataSnapshot.getValue(Double.class);
                                    double vat;
                                    vat = total * (1 + 7.0 / 100.0);
//                                    Log.e("test vat",vat+"");
                                    String textTotal = String.format(Locale.ENGLISH, "%.2f", vat);
                                    tvTotal.setText(textTotal);
                                    orderItemDao.setTotal(total);
                                    countDatabase++;
                                    orderMenuKitchenManager.setOrderMenuKitchenDao(orderKitchenList);
                                    customerOrderListAdapter.setOrderMenuKitchenManager(orderMenuKitchenManager);
                                    customerOrderListAdapter.notifyDataSetChanged();
                                    setTotalToDatabase(total);
                                    DatabaseReference vatDatabase = UtilDatabase.getUtilDatabase().child("vat");
                                    vatDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            double vat = dataSnapshot.getValue(double.class);
                                            vat = total * (1 + vat / 100.0);
                                            String textTotal = String.format(Locale.ENGLISH, "%.2f", vat);
                                            tvTotal.setText(textTotal);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            MyUtil.showText("can't get vat.");
                                        }
                                    });

//                                    Log.e("OrderListFragment", "orderKitchen list = " + orderKitchenList.size());
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    MyUtil.showText("can't get order item. ");
                                }
                            });
                        }
                    } else {
                        Log.e("OrderListFragment", "data not exist");
                        setTotalToDatabase(0);
                        tvTotal.setText("0.00");
                        orderKitchenList = new ArrayList<>();
                        orderMenuKitchenManager.setOrderMenuKitchenDao(orderKitchenList);
                        customerOrderListAdapter.setOrderMenuKitchenManager(orderMenuKitchenManager);
                        customerOrderListAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else{
            Log.e("set order real time","not queried ");
        }
    }

    private void setTotalToDatabase(final float total) {
        DatabaseReference orderDatabase = UtilDatabase.getDatabase().child("order/" + orderItemDao.getOrderId() + "/total");
        orderDatabase.setValue(total).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    MyUtil.showText("not success on save total to database");
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("set order real time","on pause");

    }


    @Override
    public void onYesButtonClickInYesNODialog(Bundle bundle) {
        OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(pos);
//        MyUtil.showText(orderMenuKitchenItemDao.getOrderId()+" "+orderMenuKitchenItemDao.getOrderKitchenId());
        DatabaseReference orderDatabase = UtilDatabase.getDatabase()
                .child("order/" + orderMenuKitchenItemDao.getOrderId() + "/orderList/" + orderMenuKitchenItemDao.getOrderKitchenId());
        orderDatabase.removeValue();
        DatabaseReference orderKitchenDatabase = UtilDatabase.getDatabase().child("order_kitchen/" + orderMenuKitchenItemDao.getOrderKitchenId());
        orderKitchenDatabase.removeValue();
    }

    @Override
    public void onNoButtonClickInYesNODialog(Bundle bundle) {

    }

    public void setOrderItemDao(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }
}
