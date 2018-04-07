package com.smartaurant_kmutt.smartaurant.fragment.staff;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.staff.StaffCheckOrderActivity;
import com.smartaurant_kmutt.smartaurant.adapter.TableAdapter;
import com.smartaurant_kmutt.smartaurant.dao.TableItemDao;
import com.smartaurant_kmutt.smartaurant.dao.TableListDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.manager.TableManager;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.Locale;


@SuppressWarnings("unused")
public class StaffCallWaiter extends Fragment implements YesNoDialog.OnYesNoDialogListener{
    GridView gridViewTable;
    TableAdapter tableAdapter;
    TableManager tableManager;
    DatabaseReference tableDatabase;
    int pos;

    public StaffCallWaiter() {
        super();
    }

    @SuppressWarnings("unused")
    public static StaffCallWaiter newInstance() {
        StaffCallWaiter fragment = new StaffCallWaiter();
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
        View rootView = inflater.inflate(R.layout.fragment_staff_table_detail, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
//        getActivity().setTitle("Check order");
        initTableGridView(rootView);
        setTableDatabaseRealTime();
    }

    private void initTableGridView(View rootView) {
        gridViewTable = rootView.findViewById(R.id.listViewTable);
        tableAdapter = new TableAdapter(TableAdapter.MODE_STAFF_CALL_WAITER);
        tableManager = new TableManager();
        gridViewTable.setAdapter(tableAdapter);
        gridViewTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                TableItemDao tableItemDao = tableManager.getTableDao().getTableList().get(position);
                if(!tableItemDao.isAvailableToCallWaiter()){
                    checkDialog(tableItemDao.getTable());
                }
            }
        });
    }

    private void setTableDatabaseRealTime(){
        tableDatabase = UtilDatabase.getDatabase().child("table");
        tableDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot listTable) {
                ArrayList<TableItemDao> tableListDao = new ArrayList<>();
                for(DataSnapshot tableItemRetrieve:listTable.getChildren()){
                    TableItemDao tableItemDao = tableItemRetrieve.getValue(TableItemDao.class);
                    tableListDao.add(tableItemDao);
                }
                TableListDao tableList = new TableListDao();
                tableList.setTableList(tableListDao);
                tableManager.setTableDao(tableList);
                tableAdapter.setTableManager(tableManager);
                tableAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MyUtil.showText("Error retrieve TABLE DATA in staff check Order");
            }
        });
    }
    void checkDialog(int table){
        TableItemDao tableItemDao = tableManager.getTableDao().getTableList().get(pos);
        String title = "Table "+tableItemDao.getTable();
        String detail = tableItemDao.getTable()+" call waiter";
        YesNoDialog yesNoDialog = YesNoDialog.newInstance(title,detail);
        yesNoDialog.setTargetFragment(StaffCallWaiter.this,123);
        yesNoDialog.show(getFragmentManager(),"callWaiterFragment");
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
        TableItemDao tableItemDao = tableManager.getTableDao().getTableList().get(pos);
        String tableId= String.format(Locale.ENGLISH,"TB%03d",tableItemDao.getTable());
        DatabaseReference table = UtilDatabase.getDatabase().child("table/"+tableId+"/availableToCallWaiter");
        table.setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    MyUtil.showText("can't set to normal");
                }
            }
        });
    }

    @Override
    public void onNoButtonClickInYesNODialog(Bundle bundle) {

    }
}
