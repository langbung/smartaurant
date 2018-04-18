package com.smartaurant_kmutt.smartaurant.fragment.owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.melnykov.fab.FloatingActionButton;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;
import com.smartaurant_kmutt.smartaurant.adapter.MenuAdapter;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.MenuListDao;
import com.smartaurant_kmutt.smartaurant.dao.TableItemDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.owner.OptionsOwnerMenuDialog;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.PopupLogout;
import com.smartaurant_kmutt.smartaurant.manager.MenuManager;
import com.smartaurant_kmutt.smartaurant.util.Loading;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("unused")
public class OwnerListMenuFragment extends Fragment{
    FloatingActionButton btAddMenuFloat;
    GridView lvAllMenu;
    DatabaseReference database;
    MenuAdapter menuAdapter;
    MenuManager menuManager;
    int countDatabaseChange;
    String menuName;
    String menuPriceCheck;
    int maxMenuId;
    Loading loading = Loading.newInstance();

    public OwnerListMenuFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OwnerListMenuFragment newInstance() {
        OwnerListMenuFragment fragment = new OwnerListMenuFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_owner_menu_list, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here

        database = FirebaseDatabase.getInstance().getReference();


    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        getActivity().setTitle("Menu");
        btAddMenuFloat = rootView.findViewById(R.id.btAddMenuFloat);

        lvAllMenu = rootView.findViewById(R.id.lvAllMenu);

        menuAdapter = new MenuAdapter(MenuAdapter.OWNER_MODE);
        menuManager = new MenuManager();

        lvAllMenu.setAdapter(menuAdapter);
        lvAllMenu.setOnItemClickListener(onItemClickListener);

        setRealTime();

        btAddMenuFloat.attachToListView(lvAllMenu);
        btAddMenuFloat.setOnClickListener(onClickListener);
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

    void showText(String text) {
        Toast.makeText(Contextor.getInstance().getContext(), text, Toast.LENGTH_LONG).show();
    }

    void setRealTime() {
        loading.show(getFragmentManager(), "l");
        DatabaseReference menuDatabase = database.child("menu");
        menuDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<MenuItemDao> menuList = new ArrayList<>();
                MenuListDao menuListDao = new MenuListDao();
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot menuChild : dataSnapshot.getChildren()) {
                        MenuItemDao menuItemDao = menuChild.getValue(MenuItemDao.class);
                        menuList.add(menuItemDao);
                    }
//                    showText(String.valueOf(menuList.size()));
                    menuListDao.setMenuList(menuList);
                    menuManager.setMenuDao(menuListDao);
                    menuAdapter.setMenuManager(menuManager);
                    menuAdapter.notifyDataSetChanged();
                } else {
                    menuListDao.setMenuList(menuList);
                    menuManager.setMenuDao(menuListDao);
                    menuAdapter.setMenuManager(menuManager);
                    menuAdapter.notifyDataSetChanged();
                }
                loading.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            OptionsOwnerMenuDialog optionsOwnerMenuDialog = OptionsOwnerMenuDialog.newInstance(menuManager.getMenuDao().getMenuList().get(position));
            optionsOwnerMenuDialog.show(getFragmentManager(), "optionsOwnerMenuDialog");
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btAddMenuFloat) {
                OnOwnerListMenuFragmentListener onOwnerListMenuFragmentListener = (OnOwnerListMenuFragmentListener) getActivity();
                Bundle bundle = new Bundle();
                bundle.putString("title", "Add menu");
                onOwnerListMenuFragmentListener.onBtAddMenuFloatClick(bundle);
//                menuName = etMenuName.getText().toString().trim();
//                menuPriceText = etMenuPrice.getText().toString().trim();
//                if(checkText(menuName)&& checkText(menuPriceText)){
//                    DatabaseReference util = database.child("util").child("maxMenuId");
//                    util.addListenerForSingleValueEvent(onSaveMenu);
//                }
            }
        }
    };


    public interface OnOwnerListMenuFragmentListener {
        void onBtAddMenuFloatClick(Bundle bundle);
    }

}
