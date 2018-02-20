package com.smartaurant_kmutt.smartaurant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.StaffLoginActivity;
import com.smartaurant_kmutt.smartaurant.activity.cashier.CashierActivity;
import com.smartaurant_kmutt.smartaurant.activity.cashier.CashierTableActivity;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class StaffLoginFragment extends Fragment {
    EditText etEmail;
    EditText etPassword;
    TextView tvWrongPassord;
    Button btSubmit;
    String cashierEmail="cashier";
    String password="0000";

    public StaffLoginFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static StaffLoginFragment newInstance() {
        StaffLoginFragment fragment = new StaffLoginFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_staff_login, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        etEmail = (EditText)rootView.findViewById(R.id.etEmail);
        etPassword = (EditText)rootView.findViewById(R.id.etPassword);
        btSubmit = (Button)rootView.findViewById(R.id.btSubmit);
        tvWrongPassord = (TextView)rootView.findViewById(R.id.tvWrongPassword);
        btSubmit.setOnClickListener(onClickListener);

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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentListener fragmentListener = (FragmentListener)getActivity();
            Intent intent;
            if(etEmail.getText().toString().equals(cashierEmail)){
                tvWrongPassord.setVisibility(View.INVISIBLE);
                intent = new Intent(getActivity(), CashierTableActivity.class);
                fragmentListener.onSubmitClicked(intent);
            }else{
                tvWrongPassord.setVisibility(View.VISIBLE);
            }
        }
    };
    public interface FragmentListener{
        void onSubmitClicked(Intent intent);
    }

}
