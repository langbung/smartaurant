package com.smartaurant_kmutt.smartaurant.fragment.owner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.melnykov.fab.FloatingActionButton;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.StaffAdapter;
import com.smartaurant_kmutt.smartaurant.dao.StaffItemDao;
import com.smartaurant_kmutt.smartaurant.dao.StaffListDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.owner.OptionsStaffDialog;
import com.smartaurant_kmutt.smartaurant.manager.StaffManager;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;


@SuppressWarnings("unused")
public class OwnerStaffManagementFragment extends Fragment {
    FloatingActionButton btAddStaff;
    ListView listViewStaff;
    StaffManager staffManager;
    StaffAdapter staffAdapter;

    public OwnerStaffManagementFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OwnerStaffManagementFragment newInstance() {
        OwnerStaffManagementFragment fragment = new OwnerStaffManagementFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_owner_staff_management, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        getActivity().setTitle("Staff management");
        btAddStaff = rootView.findViewById(R.id.btAddStaffFloat);
        listViewStaff = rootView.findViewById(R.id.listViewStaff);
        staffManager = new StaffManager();
        staffAdapter = new StaffAdapter();
        setRealTime();
        btAddStaff.setOnClickListener(onButtonOnClickListener);
        listViewStaff.setAdapter(staffAdapter);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                StaffItemDao staffItemDao = staffManager.getStaffDao().getStaffList().get(position);
                bundle.putParcelable("staffItemDao", staffItemDao);
                OptionsStaffDialog optionsStaffDialog = OptionsStaffDialog.newInstance(bundle);
                optionsStaffDialog.show(getFragmentManager(),"optionsStaffDialog");
            }
        };
        listViewStaff.setOnItemClickListener(onItemClickListener);
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

    void setRealTime(){
        DatabaseReference staffDatabase = UtilDatabase.getDatabase().child("staff");
        staffDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<StaffItemDao> staffList = new ArrayList<>();
                for(DataSnapshot staff:dataSnapshot.getChildren()){
                    StaffItemDao staffItemDao = staff.getValue(StaffItemDao.class);
                    staffList.add(staffItemDao);
                }
                StaffListDao staffListDao = new StaffListDao(staffList);
                staffManager.setStaffDao(staffListDao);
                staffAdapter.setStaffManager(staffManager);
                staffAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    View.OnClickListener onButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v==btAddStaff){
                OnOwnerStaffManagementListener onOwnerStaffManagementListener = (OnOwnerStaffManagementListener) getActivity();
                Bundle bundle = new Bundle();
                bundle.putString("title","Add staff");
                onOwnerStaffManagementListener.onAddStaffButtonClick(bundle);
            }
        }
    };

    public interface OnOwnerStaffManagementListener{
        void onAddStaffButtonClick(Bundle bundle);
    }
}
