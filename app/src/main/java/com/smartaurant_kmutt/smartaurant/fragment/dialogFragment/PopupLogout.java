package com.smartaurant_kmutt.smartaurant.fragment.dialogFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.util.Account;
import com.smartaurant_kmutt.smartaurant.util.Loading;

/**
 * Created by LB on 20/2/2561.
 */

public class PopupLogout extends android.support.v4.app.DialogFragment {
    String posText = "";
    EditText etPassword;
    TextView tvWrongPassword;
    TextView tvTitle;
    Button bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
    Button btBack;
    Button btOk;
    Loading loading = Loading.newInstance();


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.fragment_login, null);
        builder.setView(rootView);
        initInstances(rootView, savedInstanceState);
        return builder.create();

    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here\
        initButton(rootView);
        initEditText(rootView);
        initTextView(rootView);
    }

    private void initTextView(View rootView) {
        tvWrongPassword = rootView.findViewById(R.id.tvWrongPassword);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        tvTitle.setText("Logout");
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

    void showCashierDisplay(String posText) {
        etPassword.setText(String.valueOf(posText));
    }

    private void signIn(String password) {
        loading.show(getFragmentManager(), "l");
        tvWrongPassword.setVisibility(View.INVISIBLE);
        Account account = new Account();
        account.signIn(password, new Account.AccountListener() {
            @Override
            public void getStaffAfterSignIn(String staffRole) {
                loading.dismiss();
                logout(staffRole);
            }
        });
    }

    private void logout(String staffRole) {
        if (!staffRole.equals("none")) {
            OnPopupLogoutClicked onPopupLogoutClicked = (OnPopupLogoutClicked) getActivity();
            onPopupLogoutClicked.onPopupLogoutClick(staffRole);
        }
        else
            tvWrongPassword.setVisibility(View.VISIBLE);
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

    public interface OnPopupLogoutClicked {
        void onPopupLogoutClick(String staffRole);
    }
}
