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

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.adapter.TableAdapter;


@SuppressWarnings("unused")
public class CustomerTableFragment extends Fragment {
    GridView gridViewTable;
    TableAdapter tableAdapter;
    android.support.v7.widget.Toolbar toolbar;
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
        toolbar=(android.support.v7.widget.Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Select table");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        gridViewTable = (GridView) rootView.findViewById(R.id.listViewTable);
        gridViewTable.setOnItemClickListener(gridViewOnItemClickListener);
        tableAdapter = new TableAdapter();
        gridViewTable.setAdapter(tableAdapter);





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
            position+=1;
            String table = "Table: "+position;
            FragmentListener fragmentListener=(FragmentListener)getActivity();
            fragmentListener.onTableItemClicked(table);

        }
    };

    public interface FragmentListener{
        void onTableItemClicked(String table);
    }

}
