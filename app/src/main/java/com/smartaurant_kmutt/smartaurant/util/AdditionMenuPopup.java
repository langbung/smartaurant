package com.smartaurant_kmutt.smartaurant.util;

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

public class AdditionMenuPopup extends android.support.v4.app.DialogFragment {
    Button btAddMenu,btClose;
    EditText etName;
    EditText etPrice;


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_addition_menu, null);
        builder.setView(view);
        initInstance(view);
        return builder.create();

    }

    private void initInstance(View view) {
        btAddMenu = (Button) view.findViewById(R.id.btAddMenu);
        etName = (EditText) view.findViewById(R.id.etName);
        etPrice = (EditText) view.findViewById(R.id.etPrice);
        btAddMenu.setOnClickListener(onClickListener);
        btClose = view.findViewById(R.id.btClose);
        btClose.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btAddMenu) {
                String name = etName.getText().toString().trim();
                if (!name.equals("")) {
                    OnAddMenuDialogListening dialogListener = (OnAddMenuDialogListening)getActivity();
                    dialogListener.onAddMenuBtClick(name);
                }
            }
            else if(v==btClose){
               getDialog().dismiss();
            }

        }
    };

    public interface OnAddMenuDialogListening {
        void onAddMenuBtClick(String name);
    }
}
