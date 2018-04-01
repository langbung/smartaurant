package com.smartaurant_kmutt.smartaurant.fragment.staff;

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
import com.smartaurant_kmutt.smartaurant.adapter.CustomerMenuAdapter;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.MenuListDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.manager.MenuManager;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;


@SuppressWarnings("unused")
public class StaffMenuSettingFragment extends Fragment implements YesNoDialog.OnYesNoDialogListener {
    CustomerMenuAdapter menuAdapter;
    MenuManager menuManager;
    GridView gridViewMenu;
    public StaffMenuSettingFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static StaffMenuSettingFragment newInstance() {
        StaffMenuSettingFragment fragment = new StaffMenuSettingFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_staff_menu_management, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        initMenuGridView(rootView);

    }

    private void initMenuGridView(View rootView) {
        gridViewMenu = rootView.findViewById(R.id.gridViewMenu);
        menuManager = new MenuManager();
        menuAdapter = new CustomerMenuAdapter();
        menuRealTime();
        gridViewMenu.setAdapter(menuAdapter);
        gridViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItemDao menuItemDao = menuManager.getMenuDao().getMenuList().get(position);
                String title = menuItemDao.getName();
                String detail = "Do you set menu to DISABLE ?";
                YesNoDialog yesNoDialog = YesNoDialog.newInstance(title,detail);
                yesNoDialog.setTargetFragment(StaffMenuSettingFragment.this,01);
                yesNoDialog.show(getFragmentManager(),"setEnableMenuDialog");
            }
        });
    }

    private void menuRealTime() {
        DatabaseReference menuDatabase = UtilDatabase.getMenu();
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
    public void onYesButtonClickInYesNODialog(Bundle bundle) {

    }

    @Override
    public void onNoButtonClickInYesNODialog(Bundle bundle) {

    }
}
