package com.smartaurant_kmutt.smartaurant.fragment.customer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.MenuAdapter;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.MenuListDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.customer.OrderDialogFragment;
import com.smartaurant_kmutt.smartaurant.manager.MenuManager;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.internal.Util;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CustomerMenuFragment extends Fragment implements OrderDialogFragment.OnOrderDialogListener {
    GridView gvMenu;
    MenuManager menuManager;
    MenuAdapter menuAdapter;
    OrderItemDao orderItemDao;
    OrderKitchenItemDao orderKitchenItemDao;
    int table;
    int pos;
    String title;
    String menuType;
    public CustomerMenuFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CustomerMenuFragment newInstance(Bundle bundle) {
        CustomerMenuFragment fragment = new CustomerMenuFragment();
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
        menuType = bundle.getString("menuType");
        title = bundle.getString("title");
        table = bundle.getInt("table");
        orderItemDao=bundle.getParcelable("orderItemDao");
        orderKitchenItemDao = bundle.getParcelable("orderKitchenItemDao");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_menu, container, false);
        initInstances(rootView, savedInstanceState);
        setListener();
        return rootView;
    }



    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        gvMenu = rootView.findViewById(R.id.gvMenuCustomer);
        menuManager = new MenuManager();
        menuAdapter = new MenuAdapter(MenuAdapter.CUSTOMER_MODE);
        getActivity().setTitle(title);
    }

    private void setListener() {
        menuRealTime();
        gvMenu.setAdapter(menuAdapter);
        gvMenu.setOnItemClickListener(onItemMenuClick);
    }

    private void menuRealTime() {
        DatabaseReference menuDatabase = UtilDatabase.getMenu();
        Log.e("customer",menuType);
        Query menuQuery = menuDatabase.orderByChild(menuType).equalTo(true);
        menuQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<MenuItemDao> menuList = new ArrayList<>();
                for(DataSnapshot menuItem:dataSnapshot.getChildren()){
                    MenuItemDao menuItemDao = menuItem.getValue(MenuItemDao.class);
                    menuList.add(menuItemDao);
                }
//                MyUtil.showText(String.valueOf(dataSnapshot.getChildrenCount()));
                MenuListDao menuListDao = new MenuListDao();
                menuListDao.setMenuList(menuList);
                menuManager.setMenuDao(menuListDao);
                menuAdapter.setMenuManager(menuManager);
                menuAdapter.notifyDataSetChanged();
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

    @Override
    public void onOrderClick(Bundle bundle) {
        orderItemDao = bundle.getParcelable("orderItemDao");

//        orderKitchenItemDao = bundle.getParcelable("orderKitchenItemDao");
//        Log.e("OrderDialogClick",orderItemDao.getOrderList().size()+"");
//        try {
//            InternalStorage.writeObject(getActivity(),"orderItemDao",orderItemDao);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    AdapterView.OnItemClickListener onItemMenuClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            pos = position;
            if(orderItemDao!=null){
                DatabaseReference orderItemDatabase = UtilDatabase.getDatabase().child("order/"+orderItemDao.getOrderId()+"/orderList");
                orderItemDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String,OrderMenuKitchenItemDao> orderList = new HashMap<>();
                        for(DataSnapshot orderItem:dataSnapshot.getChildren()){
                            orderList.put(orderItem.getKey(),orderItem.getValue(OrderMenuKitchenItemDao.class));
                        }
                        orderItemDao.setOrderList(orderList);
                        Bundle bundle = new Bundle();
                        MenuItemDao menu = menuManager.getMenuDao().getMenuList().get(pos);
                        bundle.putParcelable("menu",menu);
                        bundle.putParcelable("orderItemDao",orderItemDao);
                        bundle.putInt("table",table);

                        OrderDialogFragment orderDialogFragment = OrderDialogFragment.newInstance(bundle);
                        orderDialogFragment.setTargetFragment(CustomerMenuFragment.this,1);
                        orderDialogFragment.show(getFragmentManager(),"orderDialogFragment");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }else{
                Bundle bundle = new Bundle();
                MenuItemDao menu = menuManager.getMenuDao().getMenuList().get(pos);
                bundle.putParcelable("menu",menu);
                bundle.putInt("table",table);
                OrderDialogFragment orderDialogFragment = OrderDialogFragment.newInstance(bundle);
                orderDialogFragment.setTargetFragment(CustomerMenuFragment.this,1);
                orderDialogFragment.show(getFragmentManager(),"orderDialogFragment");
            }

        }
    };
}
