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
import com.smartaurant_kmutt.smartaurant.util.Loading;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class StaffLoginFragment extends Fragment {

    String posText = "";
    EditText etPassword;
    TextView tvWrongPassword;
    Button bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
    Button btBack;
    Button btOk;
    Loading loading = Loading.newInstance();
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
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here\
        initButton(rootView);
        initEditText(rootView);
        initTextView(rootView);
    }

    private void initTextView(View rootView) {
        tvWrongPassword = rootView.findViewById(R.id.tvWrongPassword);
    }

    private void initEditText(View rootView) {
        etPassword = rootView.findViewById(R.id.etPassword);
    }

    private void initButton(View rootView) {
        bt0 = rootView.findViewById(R.id.bt0);
        bt1 = rootView.findViewById(R.id.bt1);
        bt2 = rootView.findViewById(R.id.bt2);
        bt3 = rootView.findViewById(R.id.bt3);
        bt4 = rootView.findViewById(R.id.bt4);
        bt5 = rootView.findViewById(R.id.bt5);
        bt6 = rootView.findViewById(R.id.bt6);
        bt7 = rootView.findViewById(R.id.bt7);
        bt8 = rootView.findViewById(R.id.bt8);
        bt9 = rootView.findViewById(R.id.bt9);
        btBack = rootView.findViewById(R.id.btBack);
        btOk = rootView.findViewById(R.id.btOk);

        bt0.setOnClickListener(onCashierButtonClick);
        bt1.setOnClickListener(onCashierButtonClick);
        bt2.setOnClickListener(onCashierButtonClick);
        bt3.setOnClickListener(onCashierButtonClick);
        bt4.setOnClickListener(onCashierButtonClick);
        bt5.setOnClickListener(onCashierButtonClick);
        bt6.setOnClickListener(onCashierButtonClick);
        bt7.setOnClickListener(onCashierButtonClick);
        bt8.setOnClickListener(onCashierButtonClick);
        bt9.setOnClickListener(onCashierButtonClick);
        btBack.setOnClickListener(onCashierButtonClick);
        btBack.setOnLongClickListener(onLongClickListener);
        btOk.setOnClickListener(onCashierButtonClick);

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
                tvWrongPassword.setVisibility(View.VISIBLE);
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

    private void signIn(String password) {
        loading.show(getFragmentManager(),"l");
        tvWrongPassword.setVisibility(View.INVISIBLE);
        Account account = new Account();
        account.signIn(password, new Account.AccountListener() {
            @Override
            public void getStaffAfterSignIn(String staffRole){
                loading.dismiss();
                updateUI(staffRole);
            }
        });
    }


    void showCashierDisplay(String posText) {
        etPassword.setText(String.valueOf(posText));
    }

    View.OnClickListener onCashierButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == bt0) {
                posText += "0";
                showCashierDisplay(posText);
            } else if (v == bt1) {
                posText += "1";
                showCashierDisplay(posText);
            } else if (v == bt2) {
                posText += "2";
                showCashierDisplay(posText);
            } else if (v == bt3) {
                posText += "3";
                showCashierDisplay(posText);
            } else if (v == bt4) {
                posText += "4";
                showCashierDisplay(posText);
            } else if (v == bt5) {
                posText += "5";
                showCashierDisplay(posText);
            } else if (v == bt6) {
                posText += "6";
                showCashierDisplay(posText);
            } else if (v == bt7) {
                posText += "7";
                showCashierDisplay(posText);
            } else if (v == bt8) {
                posText += "8";
                showCashierDisplay(posText);

            } else if (v == bt9) {
                posText += "9";
                showCashierDisplay(posText);
            } else if (v == btBack) {
                int posTextLength = posText.length();
                if (posTextLength > 1) {
                    posText = posText.substring(0, posTextLength - 1);
                    showCashierDisplay(posText);
                } else {
                    posText = "";
                    showCashierDisplay(posText);
                }
            } else if (v == btOk) {
                signIn(posText);
            }
        }
    };

    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            posText = "";
            showCashierDisplay(posText);
            return true;
        }
    };

    public interface StaffLoginFragmentListener {
        void onSubmitClicked(Intent intent);
    }

}
