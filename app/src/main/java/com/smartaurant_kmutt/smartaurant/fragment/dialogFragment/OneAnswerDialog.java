package com.smartaurant_kmutt.smartaurant.fragment.dialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smartaurant_kmutt.smartaurant.R;

/**
 * Created by LB on 20/2/2561.
 */

public class OneAnswerDialog extends android.support.v4.app.DialogFragment {
    TextView tvTitle;
    Button btYes;
    TextView tvDetail;
    OneAnswerDialogListener onYesNoDialogListener;
    int mode;
    static final int MODE_SET_TITLE_DETAIL = 1;
    static final int MODE_SET_TITLE_DETAIL_OK_BUTTON_CANCEL_BUTTON = 2;

    public static OneAnswerDialog newInstance(Bundle bundle) {
        OneAnswerDialog yesNoDialog = new OneAnswerDialog();
        Bundle args = new Bundle();
        args.putBundle("bundle", bundle);
        yesNoDialog.setArguments(args);
        args.putInt("mode", 0);
        return yesNoDialog;
    }

    public static OneAnswerDialog newInstance(String title, String detail) {
        OneAnswerDialog yesNoDialog = new OneAnswerDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("detail", detail);
        args.putInt("mode", MODE_SET_TITLE_DETAIL);
        yesNoDialog.setArguments(args);
        return yesNoDialog;
    }

    public static OneAnswerDialog newInstance(String title, String detail, String buttonOKText) {
        OneAnswerDialog yesNoDialog = new OneAnswerDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("detail", detail);
        args.putString("buttonOKText", buttonOKText);
        args.putInt("mode", MODE_SET_TITLE_DETAIL_OK_BUTTON_CANCEL_BUTTON);
        yesNoDialog.setArguments(args);
        return yesNoDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_one_answer, null);
        builder.setView(view);
        initInstance(view);
        setListener();
        String title;
        String detail;
        String btOKText;
        Bundle bundle = getArguments().getBundle("bundle");
        mode = getArguments().getInt("mode");
        if (mode == MODE_SET_TITLE_DETAIL_OK_BUTTON_CANCEL_BUTTON) {
            title = getArguments().getString("title");
            detail = getArguments().getString("detail");
            btOKText = getArguments().getString("buttonOKText");
            tvTitle.setText(title);
            tvDetail.setText(detail);
            btYes.setText(btOKText);

        } else if (mode == MODE_SET_TITLE_DETAIL) {
            title = getArguments().getString("title");
            detail = getArguments().getString("detail");
            tvTitle.setText(title);
            tvDetail.setText(detail);
        } else {
            title = bundle.getString("title", "none");
            detail = bundle.getString("detail", "none");
            tvTitle.setText(title);
            tvDetail.setText(detail);
        }
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onYesNoDialogListener = (OneAnswerDialogListener) getTargetFragment();

    }

    private void setListener() {
        btYes.setOnClickListener(onClickListener);
    }

    private void initInstance(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        btYes = view.findViewById(R.id.btYes);
        tvDetail = view.findViewById(R.id.tvDetail);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btYes) {
                Bundle bundle = new Bundle();
                onYesNoDialogListener.onYesButtonClickInOneAnswerDialog(bundle, getTargetRequestCode());
                dismiss();
            }
        }
    };


    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public interface OneAnswerDialogListener {
        void onYesButtonClickInOneAnswerDialog(Bundle bundle, int requestCode);
    }
}
