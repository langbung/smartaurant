package com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.cashier;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;
import com.smartaurant_kmutt.smartaurant.util.Voucher;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LB on 20/2/2561.
 */

public class DialogCashierVoucher extends android.support.v4.app.DialogFragment {
    Button btSubmit;
    EditText etVoucher;
    String voucherCode;
    public static final int VOUCHER_USED = -10;
    public static final int VOUCHER_ERROR = -3;
    int sale = -3;
    int check = -2;

    public static DialogCashierVoucher newInstance() {
        DialogCashierVoucher dialogCashierVoucher = new DialogCashierVoucher();
        Bundle args = new Bundle();
        dialogCashierVoucher.setArguments(args);
        return dialogCashierVoucher;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_voucher, null);
        builder.setView(view);
        initInstance(view);
        return builder.create();

    }

    private void initInstance(View view) {
        btSubmit = view.findViewById(R.id.btSubmit);
        etVoucher = view.findViewById(R.id.etVoucher);
        btSubmit.setOnClickListener(onClickListener);
        Voucher voucher = new Voucher();
        String voucherCode = voucher.getVoucher(200, 3);
        MyUtil.print(voucherCode);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            voucherCode = etVoucher.getText().toString();
            if (!voucherCode.equals("")) {
                Voucher voucher = new Voucher();
                check = voucher.decryptVoucher(voucherCode);
                if (check > 0) {
                    DatabaseReference voucherDatabase = UtilDatabase.getDatabase().child("voucher/" + voucherCode);
                    voucherDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                Map<String, Object> updateVoucher = new HashMap<>();
                                updateVoucher.put(voucherCode, true);
                                DatabaseReference voucherDatabase = UtilDatabase.getDatabase().child("voucher");
                                voucherDatabase.updateChildren(updateVoucher);
                                sale = check;
                                VoucherCashierDialogListener voucherCashierDialogListener = (VoucherCashierDialogListener) getTargetFragment();
                                voucherCashierDialogListener.onSubmitButtonClickInVoucherCashierDialog(sale, getTargetRequestCode());
                                dismiss();
                            } else {
                                sale=VOUCHER_USED;
                                VoucherCashierDialogListener voucherCashierDialogListener = (VoucherCashierDialogListener) getTargetFragment();
                                voucherCashierDialogListener.onSubmitButtonClickInVoucherCashierDialog(sale, getTargetRequestCode());
                                dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    sale=VOUCHER_ERROR;
                    VoucherCashierDialogListener voucherCashierDialogListener = (VoucherCashierDialogListener) getTargetFragment();
                    voucherCashierDialogListener.onSubmitButtonClickInVoucherCashierDialog(sale, getTargetRequestCode());
                    dismiss();
                }
            }
        }
    };

    public interface VoucherCashierDialogListener {
        void onSubmitButtonClickInVoucherCashierDialog(int sale, int requestCode);
    }
}
