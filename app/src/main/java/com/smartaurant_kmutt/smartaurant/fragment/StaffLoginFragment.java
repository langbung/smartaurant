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

import com.google.firebase.auth.FirebaseAuth;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.cashier.CashierTableActivity;
import com.smartaurant_kmutt.smartaurant.activity.kitchen.KitchenActivity;
import com.smartaurant_kmutt.smartaurant.activity.owner.OwnerActivity;
import com.smartaurant_kmutt.smartaurant.activity.staff.StaffActivity;
import com.smartaurant_kmutt.smartaurant.util.Account;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class StaffLoginFragment extends Fragment {
    EditText etEmail;
    EditText etPassword;
    TextView tvWrongPassord;
    Button btSubmit;
    String cashierEmail = "cashier";
    String password = "0000";
    private FirebaseAuth mAuth;

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
        View rootView = inflater.inflate(R.layout.fragmentf_login, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        etEmail = rootView.findViewById(R.id.etEmail);
        etPassword = rootView.findViewById(R.id.etPassword);
        btSubmit = rootView.findViewById(R.id.btSubmit);
        tvWrongPassord = rootView.findViewById(R.id.tvWrongPassword);
        btSubmit.setOnClickListener(onClickListener);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI();
    }

    private void updateUI(String role) {
        switch (role) {
            case "Owner": {
                Intent intent = new Intent(getActivity(), OwnerActivity.class);
                StaffLoginFragmentListener staffLoginFragmentListener = (StaffLoginFragmentListener) getActivity();
                staffLoginFragmentListener.onSubmitClicked(intent);
                break;
            }
            case "Staff": {
                Intent intent = new Intent(getActivity(), StaffActivity.class);
                StaffLoginFragmentListener staffLoginFragmentListener = (StaffLoginFragmentListener) getActivity();
                staffLoginFragmentListener.onSubmitClicked(intent);
                break;
            }
            case "Cashier": {
                Intent intent = new Intent(getActivity(), CashierTableActivity.class);
                StaffLoginFragmentListener staffLoginFragmentListener = (StaffLoginFragmentListener) getActivity();
                staffLoginFragmentListener.onSubmitClicked(intent);
                break;
            }
            case "Kitchen": {
                Intent intent = new Intent(getActivity(), KitchenActivity.class);
                StaffLoginFragmentListener staffLoginFragmentListener = (StaffLoginFragmentListener) getActivity();
                staffLoginFragmentListener.onSubmitClicked(intent);
                break;
            }
            case "Manager": {
                Intent intent = new Intent(getActivity(), StaffActivity.class);
                StaffLoginFragmentListener staffLoginFragmentListener = (StaffLoginFragmentListener) getActivity();
                staffLoginFragmentListener.onSubmitClicked(intent);
                break;
            }
            default: {
                tvWrongPassord.setVisibility(View.VISIBLE);
                break;
            }
        }

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

    private void signIn(String email, String password) {
        switch (email){
            case "owner":{
                updateUI("Owner");
                break;
            }
            case "kitchen":{
                updateUI("Kitchen");
                break;
            }
            case "staff":{
                updateUI("Staff");
                break;
            }
            case "cashier":{
                updateUI("cashier");
                break;
            }
            default:{
                tvWrongPassord.setVisibility(View.INVISIBLE);
                Account account = new Account();
                account.signIn(email, password, new Account.AccountListener() {
                    @Override
                    public void getStaffAfterSignIn(String staffRole) {
                        if (!staffRole.equals("none"))
                            updateUI(staffRole);
                        else
                            tvWrongPassord.setVisibility(View.VISIBLE);
                    }
                });
                break;
            }
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            StaffLoginFragmentListener staffLoginFragmentListener = (StaffLoginFragmentListener) getActivity();
            Intent intent;
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (MyUtil.checkText(email) && MyUtil.checkText(password)) {
                signIn(email,password);
            } else {
                MyUtil.showText("fill all field please");
            }
        }
    };


    public interface StaffLoginFragmentListener {
        void onSubmitClicked(Intent intent);
    }

}
