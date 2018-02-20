package com.smartaurant_kmutt.smartaurant.fragment.cashier;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.smartaurant_kmutt.smartaurant.R;

import org.w3c.dom.Text;


@SuppressWarnings("unused")
public class CashierFragment extends Fragment {
    String table;
    TextView tvText;
    android.support.v7.widget.Toolbar toolbar;
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public CashierFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CashierFragment newInstance(String table) {
        CashierFragment fragment = new CashierFragment();
        Bundle args = new Bundle();
        args.putString("table",table);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        setTable(getArguments().getString("table"));
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cashier, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        toolbar = (android.support.v7.widget.Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Cashier");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        tvText = (TextView)rootView.findViewById(R.id.tvText);
        tvText.setText(getTable());

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

}
