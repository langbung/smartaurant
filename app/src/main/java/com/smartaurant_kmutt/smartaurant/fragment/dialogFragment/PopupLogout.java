package com.smartaurant_kmutt.smartaurant.fragment.dialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smartaurant_kmutt.smartaurant.R;

/**
 * Created by LB on 20/2/2561.
 */

public class PopupLogout extends android.support.v4.app.DialogFragment{
    Button btSubmit;
    EditText etEmail;
    EditText etPassword;



    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_logout,null);
        builder.setView(view);
        initInstance(view);
        return builder.create();

    }
    private void initInstance(View view){
        btSubmit = (Button)view.findViewById(R.id.btSubmit);
        etEmail=(EditText)view.findViewById(R.id.etEmail);
        etPassword=(EditText)view.findViewById(R.id.etPassword);
        btSubmit.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OnPopupLogoutClicked dialogListener =(OnPopupLogoutClicked) getActivity();
            dialogListener.onPopupLogoutClick(etEmail.getText().toString(),etPassword.getText().toString());
        }
    };

    public interface OnPopupLogoutClicked {
        void onPopupLogoutClick(String email, String password);
    }
}