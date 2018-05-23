package com.smartaurant_kmutt.smartaurant.fragment.owner;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.smartaurant_kmutt.smartaurant.activity.owner.OwnerDetailOrderListActivity;
import com.smartaurant_kmutt.smartaurant.adapter.CustomerOrderListAdapter;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.util.Loading;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;


@SuppressWarnings("unused")
public class OwnerRevenueOrderListFragment extends Fragment implements YesNoDialog.OnYesNoDialogListener {
    TextView tvOrderId;
    TextView tvTime;
    TextView tvTable;
    TextView tvTotal;
    ListView lvOrderList;
    String posText = "";
    OrderItemDao orderItemDao;
    OrderMenuKitchenItemDao orderMenuItem;
    ArrayList<OrderMenuKitchenItemDao> orderKitchenList;
    double vat;
    int countDatabase;
    float total;
    long posNum;
    Loading loading = Loading.newInstance();
    android.support.v7.widget.Toolbar toolbar;
    OrderMenuKitchenManager orderMenuManager;
    CustomerOrderListAdapter orderMenuAdapter;
    int countChild;
    OwnerDetailOrderListActivity activity;

    public OwnerRevenueOrderListFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OwnerRevenueOrderListFragment newInstance(Bundle bundle) {
        OwnerRevenueOrderListFragment fragment = new OwnerRevenueOrderListFragment();
        Bundle args = new Bundle();
        args.putBundle("bundle", bundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        Bundle bundle = getArguments().getBundle("bundle");
        orderItemDao = bundle.getParcelable("orderItemDao");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_revenue_order, container, false);
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
        initTextView(rootView);
        initListViewOrderMenu(rootView);
    }

    private void initListViewOrderMenu(View rootView) {
        lvOrderList = rootView.findViewById(R.id.lvOrderList);
        orderMenuAdapter = new CustomerOrderListAdapter();
        orderMenuManager = new OrderMenuKitchenManager();
        lvOrderList.setAdapter(orderMenuAdapter);
        readOrder();
    }

    private void initTextView(View rootView) {
        tvTotal = rootView.findViewById(R.id.tvTotal);
        tvOrderId = rootView.findViewById(R.id.tvOrderId);
        tvTable = rootView.findViewById(R.id.tvTable);
        tvTime = rootView.findViewById(R.id.tvTime);

        DatabaseReference databaseReference = UtilDatabase.getDatabase().child("order/" + orderItemDao.getOrderId() + "/dateTime");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loading.show(getFragmentManager(), "l");
                String orderId= "Order: "+orderItemDao.getOrderId();
                tvOrderId.setText(orderId);
                String tableText = "Table " + orderItemDao.getTable();
                tvTable.setText(tableText);
                Map<String, String> date = (Map) dataSnapshot.getValue();
                String dateText = date.get("day") + "/" + date.get("month") + "/" + date.get("year") + " " + date.get("time");
                tvTime.setText(dateText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (OwnerDetailOrderListActivity) getActivity();
    }

    private void initToolbar(View rootView) {
        toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(orderItemDao.getOrderId());
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

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    void readOrder() {
        DatabaseReference orderMenuKitchenDatabase = UtilDatabase.getDatabase().child("order_kitchen");
        Query orderListQuery = orderMenuKitchenDatabase.orderByChild("orderId").equalTo(orderItemDao.getOrderId());

        orderListQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.e("123",dataSnapshot.toString());
//                    MyUtil.showText(dataSnapshot.toString());
//                    Log.e("123","data = "+dataSnapshot.getChildrenCount()+"");
//                    if(dataSnapshot.exists()){

                if (dataSnapshot.exists()) {
                    countChild = (int) dataSnapshot.getChildrenCount();
//                    Log.e("OrderListFragment", "data exist");
                    countDatabase = 0;
                    total = 0;
                    orderKitchenList = new ArrayList<>();
                    for (DataSnapshot test : dataSnapshot.getChildren()) {
                        OrderMenuKitchenItemDao orderMenuKitchenItemDao = test.getValue(OrderMenuKitchenItemDao.class);
                        orderKitchenList.add(orderMenuKitchenItemDao);

                        DatabaseReference priceMenuDatabase = UtilDatabase.getMenu()
                                .child(orderMenuKitchenItemDao.getMenuName());
//                        Log.e("123",priceMenuDatabase.toString());
                        priceMenuDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot menuItemDatabase) {
                                MenuItemDao menuItem = menuItemDatabase.getValue(MenuItemDao.class);
                                if (menuItem == null || menuItem.getName() == null) {
                                    YesNoDialog yesNoDialog = YesNoDialog.newInstance("Sorry", "There are some menu were removed.");
                                    yesNoDialog.setTargetFragment(OwnerRevenueOrderListFragment.this,555);
                                    yesNoDialog.show(getActivity().getSupportFragmentManager(), "5");
                                } else {
                                    Log.e("menuItemDao", menuItem.getName() + " " + menuItem.getPrice());
                                    String size = orderKitchenList.get(countDatabase).getSize();
                                    float price = getRealPrice(menuItem.getPrice(), size);
                                    price = price * (1 - menuItem.getPromotion() / 100);
                                    orderKitchenList.get(countDatabase).setPrice(orderKitchenList.get(countDatabase).getQuantity() * price);

//                                Log.e("123", orderKitchenList.get(countDatabase).getMenuName() + " จำนวน " + orderKitchenList.get(countDatabase).getQuantity() + " ราคา " + orderKitchenList.get(countDatabase).getPrice());
                                    total += orderKitchenList.get(countDatabase).getPrice();
                                    DecimalFormat df = new DecimalFormat("###,##0.00");
                                    tvTotal.setText(df.format(total));
                                    countDatabase++;

//                                Log.e("OrderListFragment", "orderKitchen list = " + orderKitchenList.size());
                                    if (countDatabase == countChild) {
                                        DatabaseReference vatDatabase = UtilDatabase.getUtilDatabase();
                                        vatDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                vat = dataSnapshot.child("vat").getValue(double.class);
                                                orderMenuAdapter.setVat((float) vat);
                                                float vatValue = (float) (total * vat / 100.0);

                                                String vatValueText = String.format(Locale.ENGLISH, "%.2f", vatValue);
                                                orderMenuAdapter.setVatValue(Float.parseFloat(vatValueText));
                                                total = (float) (total * (1 + vat / 100.0));
                                                Log.e("total", total + "");

                                                DataSnapshot discounts = dataSnapshot.child("discount");
                                                ArrayList<Float> keyDiscounts = new ArrayList<>();
                                                ArrayList<Integer> valueDiscounts = new ArrayList<>();

                                                for (DataSnapshot discount : discounts.getChildren()) {
                                                    keyDiscounts.add(Float.parseFloat(discount.getKey()));
                                                    valueDiscounts.add(discount.getValue(Integer.class));
                                                }

                                                DecimalFormat df = new DecimalFormat("###,##0.00");
                                                tvTotal.setText(df.format(total));


                                                for (int i = keyDiscounts.size() - 1; i >= 0; i--) {
                                                    if (total > keyDiscounts.get(i)) {
                                                        orderMenuAdapter.setCheckDiscount(true);
                                                        String discountVal = String.format(Locale.ENGLISH, "%.2f", (float) (total * valueDiscounts.get(i) / 100.0));
                                                        String discountCon = String.format(Locale.ENGLISH, "%.0f", keyDiscounts.get(i));
                                                        orderMenuAdapter.setDiscountCondition(Integer.parseInt(discountCon));
                                                        orderMenuAdapter.setDiscountValue(Float.parseFloat("-" + discountVal));
//                                                            Log.e("discountCon",discountCon);
                                                        total = (float) (total * (1 - valueDiscounts.get(i) / 100.0));

                                                        tvTotal.setText(df.format(total));
                                                        break;
                                                    }
                                                }
                                                orderMenuManager.setOrderMenuKitchenDao(orderKitchenList);
                                                orderMenuAdapter.setOrderMenuKitchenManager(orderMenuManager);
                                                orderMenuAdapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                MyUtil.showText("can't get vat.");
                                            }
                                        });
                                    }

                                }
                            }//at this

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                } else {
                    Log.e("OrderListFragment", "data not exist");
                    orderKitchenList = new ArrayList<>();
                    orderMenuManager.setOrderMenuKitchenDao(orderKitchenList);
                    orderMenuAdapter.setOrderMenuKitchenManager(orderMenuManager);
                    orderMenuAdapter.notifyDataSetChanged();
                }
                loading.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
    public void onYesButtonClickInYesNODialog(Bundle bundle, int requestCode) {
        activity.finish();
    }

    @Override
    public void onNoButtonClickInYesNODialog(Bundle bundle, int requestCode) {
        activity.finish();
    }
}
