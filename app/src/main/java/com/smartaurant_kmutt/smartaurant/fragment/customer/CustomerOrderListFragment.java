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
import android.widget.Button;
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
import com.smartaurant_kmutt.smartaurant.util.Loading;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;
import com.smartaurant_kmutt.smartaurant.util.Voucher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class  CustomerOrderListFragment extends Fragment implements YesNoDialog.OnYesNoDialogListener {
    OrderItemDao orderItemDao;
    ListView lvOrder;
    TextView tvTotal;
    OrderMenuKitchenManager orderMenuKitchenManager;
    CustomerOrderListAdapter customerOrderListAdapter;
    DatabaseReference orderMenuKitchenDatabase;
    ArrayList<OrderMenuKitchenItemDao> orderKitchenList;
    MenuItemDao menuItem;
    ValueEventListener valueEventListener;
    Loading loading;
    boolean payStatus = false;
    int countQuery;
    int pos;
    int countDatabase;
    int table;
    float total;
    Button btPay;
    final int DELETE_MENU_REQUEST_CODE = 1;
    final int CALL_PAY_REQUEST_CODE = 2;

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
        table = getArguments().getBundle("bundle").getInt("table");
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
        Log.e("set order real time", "on start");
//        setOrderRealTime();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("set order real time", "on stop");
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
        initButton(rootView);
        initLoading();
        Voucher voucher = new Voucher();
        String voucherCode=voucher.getVoucher(100,7);
        MyUtil.print("voucherCode = "+voucherCode);
        String sale = voucher.decryptVoucher(voucherCode)+"";
        MyUtil.print("sale = "+sale);

    }

    private void initLoading() {
        loading = Loading.newInstance();
    }

    private void initButton(View rootView) {
        btPay = rootView.findViewById(R.id.btPay);
        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YesNoDialog yesNoDialog;
                if (!payStatus) {
                    payStatus = true;
                    yesNoDialog = YesNoDialog.newInstance("Check bill", "Do you want to check bill?");
                } else {
                    payStatus = false;
                    yesNoDialog = YesNoDialog.newInstance("Check bill", "Do you want to cancel check bill?");
                }
                yesNoDialog.setTargetFragment(CustomerOrderListFragment.this, CALL_PAY_REQUEST_CODE);
                yesNoDialog.show(getFragmentManager(), "customerCheckbill");
            }
        });
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
                    YesNoDialog yesNoDialog = YesNoDialog.newInstance("Cancel " + orderMenuKitchenItemDao.getMenuName(), "Do you want to cancel " + orderMenuKitchenItemDao.getMenuName());
                    yesNoDialog.setTargetFragment(CustomerOrderListFragment.this, DELETE_MENU_REQUEST_CODE);
                    yesNoDialog.show(getFragmentManager(), "cancelMenu");
                }
            }
        });
    }


    void setOrderRealTime() {

        if (orderItemDao != null) {
            countQuery = 0;
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
                            orderMenuKitchenItemDao.setSize(test.child("size").getValue(String.class));
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
                                    String size = orderKitchenList.get(countDatabase).getSize();
                                    float price = getRealPrice(menuItem.getPrice(), size);
//                                    Log.e("CustomerOrderListFrag","price = "+price);
                                    price = price * (1 - menuItem.getPromotion() / 100);
//                                    Log.e("set order real time", "countDatabase = "+countDatabase );
                                    orderKitchenList.get(countDatabase).setPrice(orderKitchenList.get(countDatabase).getQuantity() * price);
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

                                    DatabaseReference vatDatabase = UtilDatabase.getUtilDatabase().child("vat");
                                    vatDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            double vat = dataSnapshot.getValue(double.class);
                                            vat = total * (1 + vat / 100.0);
                                            String textTotal = String.format(Locale.ENGLISH, "%.2f", vat);
                                            tvTotal.setText(textTotal);
                                            setTotalToDatabase(vat);
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
        } else {
            Log.e("set order real time", "not queried ");
        }
    }

    private void setTotalToDatabase(double total) {
        DatabaseReference orderDatabase = UtilDatabase.getDatabase().child("order/" + orderItemDao.getOrderId() + "/total");
        orderDatabase.setValue(total).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    MyUtil.showText("not success on save total to database");
                }
            }
        });
    }

    private float getRealPrice(float price, String size) {
        float test = 0;
        switch (size) {
            case "S": {
                test = price;
                break;
            }
            case "M": {
                test = price + 10;
                break;
            }
            case "L": {
                test = price + 15;
                break;
            }
        }
        return test;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("set order real time", "on pause");

    }


    @Override
    public void onYesButtonClickInYesNODialog(Bundle bundle, int requestCode) {
        if (requestCode == DELETE_MENU_REQUEST_CODE) {
            OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(pos);
//        MyUtil.showText(orderMenuKitchenItemDao.getOrderId()+" "+orderMenuKitchenItemDao.getOrderKitchenId());
            DatabaseReference orderDatabase = UtilDatabase.getDatabase()
                    .child("order/" + orderMenuKitchenItemDao.getOrderId() + "/orderList/" + orderMenuKitchenItemDao.getOrderKitchenId());
            orderDatabase.removeValue();
            DatabaseReference orderKitchenDatabase = UtilDatabase.getDatabase().child("order_kitchen/" + orderMenuKitchenItemDao.getOrderKitchenId());
            orderKitchenDatabase.removeValue();
        } else if (requestCode == CALL_PAY_REQUEST_CODE) {
            loading.show(getFragmentManager(), "load");
            String tableId = String.format(Locale.ENGLISH, "TB%03d", table);
            Log.e("CustomerOrderList", "tableId = " + tableId);
            String path = "table/" + tableId + "/availableCheckBill";
            Map<String, Object> update = new HashMap<>();
            update.put(path, !payStatus);
            DatabaseReference databaseReference = UtilDatabase.getDatabase();
            databaseReference.updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        String btPayText = (payStatus)?"Cancel pay":"Pay";
                        btPay.setText(btPayText);
                        loading.dismiss();
                    }
                }
            });
        }
    }

    @Override
    public void onNoButtonClickInYesNODialog(Bundle bundle, int requestCode) {

    }

    public void setOrderItemDao(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }
}
