package com.smartaurant_kmutt.smartaurant.fragment.cashier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.smartaurant_kmutt.smartaurant.activity.cashier.CashierActivity;
import com.smartaurant_kmutt.smartaurant.activity.cashier.CashierTableActivity;
import com.smartaurant_kmutt.smartaurant.adapter.CustomerOrderListAdapter;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuListDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.cashier.DialogCashierVoucher;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuOnlyManager;
import com.smartaurant_kmutt.smartaurant.util.Loading;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@SuppressWarnings("unused")
public class CashierFragment extends Fragment implements YesNoDialog.OnYesNoDialogListener
        , DialogCashierVoucher.VoucherCashierDialogListener {
    int table;
    int countDatabase;
    long childCount;
    TextView tvTotal;
    ListView lvOrderMenu;
    String posText = "";
    String orderId;
    OrderItemDao orderItem;
    OrderMenuKitchenItemDao orderMenuItem;
    ArrayList<OrderMenuKitchenItemDao> orderKitchenList;
    double vat;
    CashierActivity cashierActivity;
    int sale;
    float total;
    long posNum;
    Button bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
    Button btPay;
    Button btVoucher;
    Button btBack;
    TextView tvCashierDisplay;
    android.support.v7.widget.Toolbar toolbar;
    OrderMenuKitchenManager orderMenuManager;
    CustomerOrderListAdapter orderMenuAdapter;
    final int ERROR_REQUEST_CODE = 1;
    final int OK_REQUEST_CODE = 2;
    final int VOUCHER_REQUEST_CODE = 3;
    final int VOUCHER_ERROR_REQUEST_CODE = 4;
    final int VOUCHER_OK_REQUEST_CODE = 5;
    Loading loading = Loading.newInstance();

    public CashierFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CashierFragment newInstance(Bundle bundle) {
        CashierFragment fragment = new CashierFragment();
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
        table = bundle.getInt("table");

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cashier, container, false);
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
        initButton(rootView);
        initTextView(rootView);
        initListViewOrderMenu(rootView);
    }

    private void initListViewOrderMenu(View rootView) {
        lvOrderMenu = rootView.findViewById(R.id.lvOrderMenu);
        orderMenuAdapter = new CustomerOrderListAdapter();
        orderMenuManager = new OrderMenuKitchenManager();
        lvOrderMenu.setAdapter(orderMenuAdapter);
        readOrder();
    }

    private void initTextView(View rootView) {
        tvTotal = rootView.findViewById(R.id.tvTotal);
        tvCashierDisplay = rootView.findViewById(R.id.tvCashierDisplay);
    }

    private void initButton(View rootView) {
        bt0 = rootView.findViewById(R.id.bt0);
        bt1 = rootView.findViewById(R.id.bt1);
        bt2 = rootView.findViewById(R.id.bt2);
        bt3 = rootView.findViewById(R.id.bt3);
        bt4 = rootView.findViewById(R.id.bt4);
        bt5 = rootView.findViewById(R.id.bt5);
        bt6 = rootView.findViewById(R.id.bt6);
        bt7 = rootView.findViewById(R.id.bt7);
        bt8 = rootView.findViewById(R.id.bt8);
        bt9 = rootView.findViewById(R.id.bt9);
        btBack = rootView.findViewById(R.id.btBack);
        btPay = rootView.findViewById(R.id.btPay);
        btVoucher = rootView.findViewById(R.id.btVoucher);

        bt0.setOnClickListener(onCashierButtonClick);
        bt1.setOnClickListener(onCashierButtonClick);
        bt2.setOnClickListener(onCashierButtonClick);
        bt3.setOnClickListener(onCashierButtonClick);
        bt4.setOnClickListener(onCashierButtonClick);
        bt5.setOnClickListener(onCashierButtonClick);
        bt6.setOnClickListener(onCashierButtonClick);
        bt7.setOnClickListener(onCashierButtonClick);
        bt8.setOnClickListener(onCashierButtonClick);
        bt9.setOnClickListener(onCashierButtonClick);
        btBack.setOnClickListener(onCashierButtonClick);
        btPay.setOnClickListener(onCashierButtonClick);
        btVoucher.setOnClickListener(onCashierButtonClick);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cashierActivity =(CashierActivity) getActivity();
    }

    private void initToolbar(View rootView) {
        toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Cashier > table" + table);
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

    void showCashierDisplay(long num) {
        tvCashierDisplay.setText(String.valueOf(num));
    }

    void setPosNum(String posText) {
        posNum = Integer.parseInt(posText);
    }

    void readOrder() {
        loading.show(getFragmentManager(), "l");
        orderItem = new OrderItemDao();
        String tableID = String.format(Locale.ENGLISH, "TB%03d", table);
        DatabaseReference tableDatabase = UtilDatabase.getDatabase().child("table/" + tableID + "/orderId");
        tableDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderId = dataSnapshot.getValue(String.class);
                DatabaseReference orderMenuKitchenDatabase = UtilDatabase.getDatabase().child("order_kitchen");
                Query orderListQuery = orderMenuKitchenDatabase.orderByChild("orderId").equalTo(orderId);

                orderListQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.e("123",dataSnapshot.toString());
//                    MyUtil.showText(dataSnapshot.toString());
//                    Log.e("123","data = "+dataSnapshot.getChildrenCount()+"");
//                    if(dataSnapshot.exists()){

                        if (dataSnapshot.exists()) {
//                            Log.e("OrderListFragment", "data exist");
                            childCount = dataSnapshot.getChildrenCount();
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
                                        MenuItemDao menuItem = menuItemDatabase.getValue(MenuItemDao.class);

                                        String size = orderKitchenList.get(countDatabase).getSize();
                                        float price = getRealPrice(menuItem.getPrice(), size);
                                        price = price * (1 - menuItem.getPromotion() / 100);
                                        orderKitchenList.get(countDatabase).setPrice(orderKitchenList.get(countDatabase).getQuantity() * price);

//                                Log.e("123", orderKitchenList.get(countDatabase).getMenuName() + " จำนวน " + orderKitchenList.get(countDatabase).getQuantity() + " ราคา " + orderKitchenList.get(countDatabase).getPrice());
                                        total += orderKitchenList.get(countDatabase).getPrice();
                                        String textTotal = String.format(Locale.ENGLISH, "%.2f", total);
                                        tvTotal.setText(textTotal);
                                        countDatabase++;

//                                        orderMenuManager.setOrderMenuKitchenDao(orderKitchenList);
//                                        orderMenuAdapter.setOrderMenuKitchenManager(orderMenuManager);
//                                        orderMenuAdapter.notifyDataSetChanged();


//                                        Log.e("CashFrag","countDatabase = "+countDatabase + "child count = "+childCount);
//                                        Log.e("OrderListFragment", "orderKitchen list = " + orderKitchenList.size());
                                        if (countDatabase == childCount) {
                                            DatabaseReference vatDatabase = UtilDatabase.getUtilDatabase();
                                            vatDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot utilDatabase) {
                                                    vat = utilDatabase.child("vat").getValue(double.class);
                                                    Log.e("vat",vat+"");
                                                    String vatString = String.format(Locale.ENGLISH,"%.0f",(float)vat);
                                                    String vatValue = String.format(Locale.ENGLISH,"%.2f",total*vat/100.0);
                                                    Log.e("vatString",vatString);


                                                    DataSnapshot discounts = utilDatabase.child("discount");
                                                    ArrayList<Float> keyDiscounts = new ArrayList<>();
                                                    ArrayList<Integer> valueDiscounts = new ArrayList<>();

                                                    for(DataSnapshot discount:discounts.getChildren()){
                                                        keyDiscounts.add(Float.parseFloat(discount.getKey()));
                                                        valueDiscounts.add(discount.getValue(Integer.class));
                                                    }
//
                                                    total = (float)(total * (1 + vat / 100.0));

                                                    String textTotal = String.format(Locale.ENGLISH, "%.2f", total);
                                                    tvTotal.setText(textTotal);
//
                                                    for(int i=keyDiscounts.size()-1;i>=0;i--){
                                                        if(total>keyDiscounts.get(i)){
                                                            orderMenuAdapter.setCheckDiscount(true);
                                                            String discountVal = String.format(Locale.ENGLISH,"%.2f",(float)(total*valueDiscounts.get(i)/100.0));
                                                            String discountCon = String.format(Locale.ENGLISH,"%.0f",keyDiscounts.get(i));
                                                            orderMenuAdapter.setDiscountCondition(Integer.parseInt(discountCon));
                                                            orderMenuAdapter.setDiscountValue(Float.parseFloat("-"+discountVal));
//                                                            Log.e("discountCon",discountCon);
                                                            total = (float)(total*(1-valueDiscounts.get(i)/100.0));
                                                            textTotal = String.format(Locale.ENGLISH, "%.2f", total);
                                                            tvTotal.setText(textTotal);
//                                                            orderMenuAdapter.notifyDataSetChanged();
                                                            break;
                                                        }
                                                    }
                                                    orderMenuAdapter.setVat(Float.parseFloat(vatString));
                                                    orderMenuAdapter.setVatValue(Float.parseFloat(vatValue));
                                                    orderMenuManager.setOrderMenuKitchenDao(orderKitchenList);
                                                    orderMenuAdapter.setOrderMenuKitchenManager(orderMenuManager);
                                                    orderMenuAdapter.notifyDataSetChanged();
                                                    loading.dismiss();

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    MyUtil.showText("can't get vat.");
                                                }
                                            });
                                        }
                                    }

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
                            loading.dismiss();
                        }
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

    void startCashierActivity() {
        Intent intent = new Intent(cashierActivity, CashierTableActivity.class);
        startActivity(intent);
        cashierActivity.finish();
    }

    View.OnClickListener onCashierButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == bt0) {
                if (posText.length() <= 6) {
                    posText += "0";
                    setPosNum(posText);
                    showCashierDisplay(posNum);
                }
            } else if (v == bt1) {
                if (posText.length() <= 6) {
                    posText += "1";
                    setPosNum(posText);
                    showCashierDisplay(posNum);
                }
            } else if (v == bt2) {
                if (posText.length() <= 6) {
                    posText += "2";
                    setPosNum(posText);
                    showCashierDisplay(posNum);
                }
            } else if (v == bt3) {
                if (posText.length() <= 6) {
                    posText += "3";
                    setPosNum(posText);
                    showCashierDisplay(posNum);
                }
            } else if (v == bt4) {
                if (posText.length() <= 6) {
                    posText += "4";
                    setPosNum(posText);
                    showCashierDisplay(posNum);
                }
            } else if (v == bt5) {
                if (posText.length() <= 6) {
                    posText += "5";
                    setPosNum(posText);
                    showCashierDisplay(posNum);
                }
            } else if (v == bt6) {
                if (posText.length() <= 6) {
                    posText += "6";
                    setPosNum(posText);
                    showCashierDisplay(posNum);
                }
            } else if (v == bt7) {
                if (posText.length() <= 6) {
                    posText += "7";
                    setPosNum(posText);
                    showCashierDisplay(posNum);
                }
            } else if (v == bt8) {
                if (posText.length() <= 6) {
                    posText += "8";
                    setPosNum(posText);
                    showCashierDisplay(posNum);
                }
            } else if (v == bt9) {
                if (posText.length() <= 6) {
                    posText += "9";
                    setPosNum(posText);
                    showCashierDisplay(posNum);
                }
            } else if (v == btBack) {
                int posTextLength = posText.length();
                if (posTextLength > 1) {
                    posText = posText.substring(0, posTextLength - 1);
                    setPosNum(posText);
                    showCashierDisplay(posNum);
                } else {
                    posText = "0";
                    setPosNum(posText);
                    showCashierDisplay(posNum);
//                    MyUtil.showText(posNum+"");
                }
//                MyUtil.showText(posTextLength+"");
            } else if (btPay == v) {
                float totalLocal = Float.parseFloat(tvTotal.getText().toString());
                double change = posNum - totalLocal;
                String changeText = String.format(Locale.ENGLISH, "%.2f", change);
                YesNoDialog yesNoDialog;
                if (change < 0) {
                    yesNoDialog = YesNoDialog.newInstance("Change", "Check total.","Check","Cancel");
                    yesNoDialog.setTargetFragment(CashierFragment.this, ERROR_REQUEST_CODE);
                } else {
                    yesNoDialog = YesNoDialog.newInstance("Change", "Change " + changeText + " bath." ,"OK and exit","Cancel");
                    yesNoDialog.setTargetFragment(CashierFragment.this, OK_REQUEST_CODE);
                }
                yesNoDialog.show(getFragmentManager(), "exchange");
            } else if (v == btVoucher) {
                DialogCashierVoucher dialogCashierVoucher = DialogCashierVoucher.newInstance();
                dialogCashierVoucher.setTargetFragment(CashierFragment.this, VOUCHER_REQUEST_CODE);
                dialogCashierVoucher.show(getFragmentManager(), "voucher");
            }
        }
    };


    @Override
    public void onYesButtonClickInYesNODialog(Bundle bundle, int requestCode) {
        if (requestCode == OK_REQUEST_CODE) {
            String tableId = String.format(Locale.ENGLISH, "TB%03d", table);
            String path = "table/" + tableId + "/";
            Map<String, Object> updateTable = new HashMap<>();
            updateTable.put(path + "availableTable", true);
            updateTable.put(path + "availableToCallWaiter", true);
            updateTable.put(path + "availableCheckBill", true);
            updateTable.put(path + "orderId", "none");
            DatabaseReference tableDatabase = UtilDatabase.getDatabase();
            tableDatabase.updateChildren(updateTable).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        startCashierActivity();
                        getActivity().finish();
                    }
                }
            });
        } else if (requestCode == VOUCHER_OK_REQUEST_CODE) {
            orderMenuAdapter.setCheckVoucher(true);
            String voucherValue = String.format(Locale.ENGLISH,"%.2f",(float)(sale));
            orderMenuAdapter.setVoucherValue(Float.parseFloat("-"+voucherValue));
            orderMenuAdapter.setOrderMenuKitchenManager(orderMenuManager);
            orderMenuAdapter.notifyDataSetChanged();
            setTotalDiscount();
        }
    }

    private void setTotalDiscount() {
        float totalLocal = Float.parseFloat(tvTotal.getText().toString());
        float saleLocal = Float.parseFloat(sale + "");
        totalLocal = totalLocal - saleLocal;
        if (totalLocal < 0) {
            tvTotal.setText("0.00");
        } else {
            String totalText = String.format(Locale.ENGLISH, "%.2f", totalLocal);
            tvTotal.setText(totalText);
        }
    }

    @Override
    public void onNoButtonClickInYesNODialog(Bundle bundle, int requestCode) {
        if (requestCode == VOUCHER_OK_REQUEST_CODE) {
            orderMenuAdapter.setCheckVoucher(true);
            String voucherValue = String.format(Locale.ENGLISH,"%.2f",(float)(sale));
            orderMenuAdapter.setVoucherValue(Float.parseFloat("-"+voucherValue));
            orderMenuAdapter.setOrderMenuKitchenManager(orderMenuManager);
            orderMenuAdapter.notifyDataSetChanged();
            setTotalDiscount();
        }
    }

    @Override
    public void onSubmitButtonClickInVoucherCashierDialog(int sale, int requestCode) {
        if (sale == DialogCashierVoucher.VOUCHER_USED) {
            YesNoDialog yesNoDialog = YesNoDialog.newInstance("Voucher", "Voucher is already used");
            yesNoDialog.setTargetFragment(CashierFragment.this, VOUCHER_ERROR_REQUEST_CODE);
            yesNoDialog.show(getFragmentManager(), "voucherError");
        } else if (sale == DialogCashierVoucher.VOUCHER_ERROR) {
            YesNoDialog yesNoDialog = YesNoDialog.newInstance("Voucher", "Error voucher code.");
            yesNoDialog.setTargetFragment(CashierFragment.this, VOUCHER_ERROR_REQUEST_CODE);
            yesNoDialog.show(getFragmentManager(), "voucherError");
        } else {
            this.sale = sale;
            YesNoDialog yesNoDialog = YesNoDialog.newInstance("Voucher", "discount " + sale + " baht");
            yesNoDialog.setTargetFragment(CashierFragment.this, VOUCHER_OK_REQUEST_CODE);
            yesNoDialog.show(getFragmentManager(), "voucherError");
        }
    }
}
