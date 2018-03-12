package com.smartaurant_kmutt.smartaurant.fragment.owner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.MenuAdapter;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.MenuListDao;
import com.smartaurant_kmutt.smartaurant.manager.MenuManager;
import com.smartaurant_kmutt.smartaurant.util.AdditionMenuPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

@SuppressWarnings("unused")
public class OwnerMenuSettingFragment extends Fragment implements AdditionMenuPopup.OnAddMenuDialogListening {
    Button btAddMenu;
    EditText etMenuName;
    EditText etMenuPrice;
    ListView lvAllMenu;
    Button btRemoveAllMenu;
    Button btReadDatabase;
    DatabaseReference database;
    MenuAdapter menuAdapter;
    MenuManager menuManager;
    TextView tv01,tv02,tv03;
    int countDatabaseChange;

    public OwnerMenuSettingFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OwnerMenuSettingFragment newInstance() {
        OwnerMenuSettingFragment fragment = new OwnerMenuSettingFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_owner_menu_setting, container, false);
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

        btAddMenu = (Button)rootView.findViewById(R.id.btAddMenu);
        btAddMenu.setOnClickListener(onClickListener);
        etMenuName = (EditText)rootView.findViewById(R.id.etMenuName);
        etMenuPrice = (EditText)rootView.findViewById(R.id.etMenuPrice);
        btRemoveAllMenu = (Button)rootView.findViewById(R.id.btRemoveAllMenu);
        btRemoveAllMenu.setOnClickListener(onClickListener);
        btReadDatabase = (Button)rootView.findViewById(R.id.btReadDatabase);
        btReadDatabase.setOnClickListener(onClickListener);
        btAddMenu.setOnClickListener(onClickListener);
        lvAllMenu = (ListView)rootView.findViewById(R.id.lvAllMenu);
        menuAdapter = new MenuAdapter();
        tv01 = (TextView) rootView.findViewById(R.id.tv1);
        tv02 = (TextView) rootView.findViewById(R.id.tv2);
        tv03 = (TextView) rootView.findViewById(R.id.tv3);
        menuManager = new MenuManager();
        setRealTime();

        lvAllMenu.setAdapter(menuAdapter);


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
    void showText(String text){
        Toast.makeText(getActivity(),text,Toast.LENGTH_LONG).show();
    }

    boolean checkText(String text){
        if(text.equals("")){
            return false;
        }
        return true;
    }

    boolean checkInt(int integer){
        if(String.valueOf(integer).equals("")){
            return false;
        }
        return true;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v==btAddMenu){
                DatabaseReference menuDatabase= database.child("menu");
                String menuID = String.format(Locale.ENGLISH,"MN%03d",menuManager.getMenuList().size()+1);
                String menuName = etMenuName.getText().toString();
                int menuPrice = Integer.parseInt(etMenuPrice.getText().toString());
                if(checkText(menuName)&& checkInt(menuPrice)){
                    MenuItemDao menuItemDao = new MenuItemDao(menuID,menuName,menuPrice);
                    menuDatabase.child(menuID).setValue(menuItemDao).addOnCompleteListener(onAddMenuCompleteListener);
                }
            }else if (v==btRemoveAllMenu){
                showText("remove all menu click");
            }
//            else if (v==btReadDatabase){
//
//            }
        }
    };
    void setRealTime(){
        DatabaseReference menuDatabase = database.child("menu");
        menuDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                MenuItemDao menuItemDao = dataSnapshot.getValue(MenuItemDao.class);
//                tv01.setText(menuItemDao.getName());
//                countDatabaseChange+=1;
//                String text = "Database count"+countDatabaseChange;
//                tv02.setText(String.valueOf(menuItemDao.getPrice()));

                ArrayList<MenuItemDao> menuList = new ArrayList<>();
                for(DataSnapshot menuChild:dataSnapshot.getChildren()){
                    MenuItemDao menuItemDao = menuChild.getValue(MenuItemDao.class);
                    menuList.add(menuItemDao);
                }
                showText(String.valueOf(menuList.size()));
                menuManager.setMenuList(menuList);
                menuAdapter.setMenuList(menuManager.getMenuList());
                menuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                tv01.setText("Can't get data");
                String text = "Database count"+countDatabaseChange;
                tv02.setText(text);
            }
        });
    }

    OnCompleteListener<Void> onAddMenuCompleteListener = new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task task) {
            if(task.isSuccessful()){
                showText("Add menu click");
            }else{
                showText("Error: Can't add menu");
            }
        }
    };


    @Override
    public void onAddMenuBtClick(String name) {
        showText(name);
    }
}
