package com.smartaurant_kmutt.smartaurant.fragment.cashier;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.smartaurant_kmutt.smartaurant.adapter.TableAdapter;
import com.smartaurant_kmutt.smartaurant.dao.TableItemDao;
import com.smartaurant_kmutt.smartaurant.dao.TableListDao;
import com.smartaurant_kmutt.smartaurant.manager.TableManager;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CashierTableFragment extends Fragment {
    Toolbar toolbar;
    GridView gvTable;
    TableAdapter tableAdapter;
    TableManager tableManager;
    DatabaseReference tableDatabase;

    public CashierTableFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CashierTableFragment newInstance() {
        CashierTableFragment fragment = new CashierTableFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_cashier_table, container, false);
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
        initGridViewTable(rootView);
        setTableDatabaseRealTime();
    }

    private void initGridViewTable(View rootView) {
        tableAdapter = new TableAdapter(TableAdapter.MODE_CASHIER);
        tableManager = new TableManager();
        gvTable = rootView.findViewById(R.id.gvTable);
        gvTable.setAdapter(tableAdapter);
        gvTable.setOnItemClickListener(onItemClickListener);
    }

    private void initToolbar(View rootView) {
        toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Cashier");
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

    private void setTableDatabaseRealTime() {
        tableDatabase = UtilDatabase.getDatabase().child("table");
        tableDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot listTable) {
                ArrayList<TableItemDao> tableListDao = new ArrayList<>();
                for (DataSnapshot tableItemRetrieve : listTable.getChildren()) {
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


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TableItemDao tableItemDao = tableManager.getTableDao().getTableList().get(position);
            if (!tableItemDao.isAvailableTable()) {
                CashierTableFragmentListener cashierTableFragmentListener = (CashierTableFragmentListener) getActivity();
                int tableNum = position + 1;
                Bundle bundle = new Bundle();
                bundle.putInt("table", position + 1);
                cashierTableFragmentListener.onTableItemClickInCashierTableFragment(bundle);
            }
        }
    };

    public interface CashierTableFragmentListener {
        void onTableItemClickInCashierTableFragment(Bundle bundle);
    }

}
