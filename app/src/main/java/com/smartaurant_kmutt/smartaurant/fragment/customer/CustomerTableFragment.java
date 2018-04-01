package com.smartaurant_kmutt.smartaurant.fragment.customer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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


@SuppressWarnings("unused")
public class CustomerTableFragment extends Fragment {
    GridView gridViewTable;
    TableAdapter tableAdapter;
    android.support.v7.widget.Toolbar toolbar;
    TableManager tableManager;

    public CustomerTableFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CustomerTableFragment newInstance() {
        CustomerTableFragment fragment = new CustomerTableFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_customer_table, container, false);
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
        initGridView(rootView);

    }

    private void initGridView(View rootView) {
        gridViewTable = rootView.findViewById(R.id.listViewTable);
        gridViewTable.setOnItemClickListener(gridViewOnItemClickListener);
        tableAdapter = new TableAdapter(TableAdapter.MODE_CUSTOMER);
        gridViewTable.setAdapter(tableAdapter);
        setTableDatabaseRealTime();
    }

    private void initToolbar(View rootView) {
        toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Select table");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void setTableDatabaseRealTime() {
        final DatabaseReference tableDatabase = UtilDatabase.getDatabase().child("table");
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
                if (tableManager == null)
                    tableManager = new TableManager();
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

    AdapterView.OnItemClickListener gridViewOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TableItemDao tableItemDao = tableManager.getTableDao().getTableList().get(position);
            if (tableItemDao.isAvailableTable()) {
                int numTable = position + 1;
                Bundle bundle = new Bundle();
                bundle.putInt("table", numTable);
                FragmentListener fragmentListener = (FragmentListener) getActivity();
                fragmentListener.onTableItemClicked(bundle);
            }

        }
    };

    public interface FragmentListener {
        void onTableItemClicked(Bundle bundle);
    }

}
