package com.smartaurant_kmutt.smartaurant.fragment.customer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.MenuAdapterCustomer;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.MenuListDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.customer.OrderDialogFragment;
import com.smartaurant_kmutt.smartaurant.fragment.owner.OwnerListMenuFragment;
import com.smartaurant_kmutt.smartaurant.manager.MenuManager;
import com.smartaurant_kmutt.smartaurant.util.InternalStorage;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CustomerMenuFragment extends Fragment implements OrderDialogFragment.OnOrderDialogListener {
    GridView gvMenu;
    MenuManager menuManager;
    MenuAdapterCustomer menuAdapterCustomer;
    OrderItemDao orderItemDao;
    int table;

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
        table = getArguments().getBundle("bundle").getInt("table",-1);
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
        menuAdapterCustomer = new MenuAdapterCustomer();

    }

    private void setListener() {
        menuRealTime();
        gvMenu.setAdapter(menuAdapterCustomer);
        gvMenu.setOnItemClickListener(onItemMenuClick);
    }

    private void menuRealTime() {
        final DatabaseReference menuDatabase = UtilDatabase.getMenu();
        menuDatabase.addValueEventListener(new ValueEventListener() {
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
                menuAdapterCustomer.setMenuManager(menuManager);
                menuAdapterCustomer.notifyDataSetChanged();
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
        try {
            InternalStorage.writeObject(getActivity(),"orderItemDao",orderItemDao);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    AdapterView.OnItemClickListener onItemMenuClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            MenuItemDao menu = menuManager.getMenuDao().getMenuList().get(position);
            bundle.putParcelable("menu",menu);
            bundle.putParcelable("orderItemDao",orderItemDao);
            bundle.putInt("table",table);
            OrderDialogFragment orderDialogFragment = OrderDialogFragment.newInstance(bundle);
            orderDialogFragment.setTargetFragment(CustomerMenuFragment.this,1);
            orderDialogFragment.show(getFragmentManager(),"orderDialogFragment");
        }
    };

}
