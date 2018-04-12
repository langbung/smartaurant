package com.smartaurant_kmutt.smartaurant.fragment.cashier;

import android.content.Intent;
import android.os.Bundle;
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
import com.smartaurant_kmutt.smartaurant.activity.cashier.CashierTableActivity;
import com.smartaurant_kmutt.smartaurant.adapter.CustomerOrderListAdapter;
import com.smartaurant_kmutt.smartaurant.adapter.OrderMenuOnlyAdapter;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuListDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuOnlyManager;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.Locale;


@SuppressWarnings("unused")
public class CashierFragment extends Fragment implements YesNoDialog.OnYesNoDialogListener {
    int table;
    TextView tvTotal;
    ListView lvOrderMenu;
    String posText="";
    String orderId;
    OrderItemDao orderItem;
    OrderMenuKitchenItemDao orderMenuItem;
    ArrayList<OrderMenuKitchenItemDao> orderKitchenList;
    int countDatabase;
    float total;
    long posNum;
    Button bt0,bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9;
    Button btPay;
    Button btBack;
    TextView tvCashierDisplay;
    android.support.v7.widget.Toolbar toolbar;
    OrderMenuKitchenManager orderMenuManager;
    CustomerOrderListAdapter orderMenuAdapter;

    public CashierFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CashierFragment newInstance(Bundle bundle) {
        CashierFragment fragment = new CashierFragment();
        Bundle args = new Bundle();
        args.putBundle("bundle",bundle);
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

        bt0.setOnClickListener(onCashierButtonClick);
        bt1.setOnClickListener(onCashierButtonClick);
        bt2.setOnClickListener(onCashierButtonClick);
        bt3.setOnClickListener(onCashierButtonClick);
        bt4.setOnClickListener(onCashierButtonClick);
        bt5.setOnClickListener(onCashierButtonClick);
        bt6.setOnClickListener(onCashierButtonClick);
        bt7.setOnClickListener(onCashierButtonClick);
        bt8.setOnClickListener(onCashierButtonClick);
        bt9 .setOnClickListener(onCashierButtonClick);
        btBack.setOnClickListener(onCashierButtonClick);
        btPay.setOnClickListener(onCashierButtonClick);
    }

    private void initToolbar(View rootView) {
        toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Cashier > table"+table);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
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

    void showCashierDisplay(long num){
        tvCashierDisplay.setText(String.valueOf(num));
    }

    void setPosNum(String posText){
        posNum= Integer.parseInt(posText);
    }

    void readOrder(){
        orderItem = new OrderItemDao();
        String tableID = String.format(Locale.ENGLISH,"TB%03d",table);
        DatabaseReference tableDatabase = UtilDatabase.getDatabase().child("table/"+tableID+"/orderId");
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
                            Log.e("OrderListFragment","data exist");
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
                                        MenuItemDao menuItem = menuItemDatabase.getValue(MenuItemDao.class);
                                        float price = menuItem.getPrice()*(1-menuItem.getPromotion()/100);
                                        orderKitchenList.get(countDatabase).setPrice(orderKitchenList.get(countDatabase).getQuantity() * price);

//                                Log.e("123", orderKitchenList.get(countDatabase).getMenuName() + " จำนวน " + orderKitchenList.get(countDatabase).getQuantity() + " ราคา " + orderKitchenList.get(countDatabase).getPrice());
                                        total += orderKitchenList.get(countDatabase).getPrice();
                                        String textTotal = String.format(Locale.ENGLISH, "%.2f", total);
                                        tvTotal.setText(textTotal);
                                        countDatabase++;
                                        orderMenuManager.setOrderMenuKitchenDao(orderKitchenList);
                                        orderMenuAdapter.setOrderMenuKitchenManager(orderMenuManager);
                                        orderMenuAdapter.notifyDataSetChanged();
                                        Log.e("OrderListFragment", "orderKitchen list = " + orderKitchenList.size());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }
                        }
                        else{
                            Log.e("OrderListFragment","data not exist");
                            orderKitchenList = new ArrayList<>();
                            orderMenuManager.setOrderMenuKitchenDao(orderKitchenList);
                            orderMenuAdapter.setOrderMenuKitchenManager(orderMenuManager);
                            orderMenuAdapter.notifyDataSetChanged();
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

    void startCashierActivity(){
        Intent intent = new Intent(getActivity(), CashierTableActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    View.OnClickListener onCashierButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v==bt0){
                posText+="0";
                setPosNum(posText);
                showCashierDisplay(posNum);
            }
            else if(v==bt1){
                posText+="1";
                setPosNum(posText);
                showCashierDisplay(posNum);
            }
            else if(v==bt2){
                posText+="2";
                setPosNum(posText);
                showCashierDisplay(posNum);
            }
            else if(v==bt3){
                posText+="3";
                setPosNum(posText);
                showCashierDisplay(posNum);
            }
            else if(v==bt4){
                posText+="4";
                setPosNum(posText);
                showCashierDisplay(posNum);
            }
            else if(v==bt5){
                posText+="5";
                setPosNum(posText);
                showCashierDisplay(posNum);
            }
            else if(v==bt6){
                posText+="6";
                setPosNum(posText);
                showCashierDisplay(posNum);
            }
            else if(v==bt7){
                posText+="7";
                setPosNum(posText);
                showCashierDisplay(posNum);
            }
            else if(v==bt8){
                posText+="8";
                setPosNum(posText);
                showCashierDisplay(posNum);
            }
            else if(v==bt9){
                posText+="9";
                setPosNum(posText);
                showCashierDisplay(posNum);
            }
            else if(v==btBack){
                int posTextLength = posText.length();
                if(posTextLength>1){
                    posText = posText.substring(0,posTextLength-1);
                    setPosNum(posText);
                    showCashierDisplay(posNum);
                }else {
                    posText="0";
                    setPosNum(posText);
                    showCashierDisplay(posNum);
//                    MyUtil.showText(posNum+"");
                }
//                MyUtil.showText(posTextLength+"");
            }
            else if(btPay==v){
                String tableId = String.format(Locale.ENGLISH,"TB%03d",table);
                DatabaseReference tableDatabase = UtilDatabase.getDatabase().child("table/"+tableId+"/availableTable");
                tableDatabase.setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            float change = posNum - total;
                            YesNoDialog yesNoDialog = YesNoDialog.newInstance("Exchange","Change "+change+" bath");
                            yesNoDialog.setTargetFragment(CashierFragment.this,1234);
                            yesNoDialog.show(getFragmentManager(),"exchange");
                        }
                    }
                });
            }
        }
    };

    @Override
    public void onYesButtonClickInYesNODialog(Bundle bundle) {
        startCashierActivity();
        getActivity().finish();
    }

    @Override
    public void onNoButtonClickInYesNODialog(Bundle bundle) {

    }
}
