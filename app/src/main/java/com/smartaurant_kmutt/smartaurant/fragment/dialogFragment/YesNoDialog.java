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

public class YesNoDialog extends android.support.v4.app.DialogFragment{
    TextView tvTitle;
    Button btYes;
    Button btNo;
    TextView tvDetail;
    OnYesNoDialogListener onYesNoDialogListener;

    public static YesNoDialog newInstance(Bundle bundle){
        YesNoDialog yesNoDialog = new YesNoDialog();
        Bundle args = new Bundle();
        args.putBundle("bundle",bundle);
        yesNoDialog.setArguments(args);
        return yesNoDialog;
    }

    public static YesNoDialog newInstance(String title,String detail){
        YesNoDialog yesNoDialog = new YesNoDialog();
        Bundle args = new Bundle();
        args.putString("title",title);
        args.putString("detail",detail);
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
        View view = inflater.inflate(R.layout.dialog_yes_no,null);
        builder.setView(view);
        initInstance(view);
        setListener();
        Bundle bundle = getArguments().getBundle("bundle");
        String title = bundle.getString("title","none");
        String detail = bundle.getString("detail","none");
        if (title.equals("none")||detail.equals("none")){
            title = getArguments().getString("title");
            detail = getArguments().getString("detail");
        }
        tvTitle.setText(title);
        tvDetail.setText(detail);

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onYesNoDialogListener = (OnYesNoDialogListener)getTargetFragment();
    }

    private void setListener() {
        btYes.setOnClickListener(onClickListener);
        btNo.setOnClickListener(onClickListener);
    }

    private void initInstance(View view){
        tvTitle = view.findViewById(R.id.tvTitle);
        btYes = view.findViewById(R.id.btYes);
        btNo = view.findViewById(R.id.btNo);
        tvDetail=view.findViewById(R.id.tvDetail);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v==btYes){
                Bundle bundle = new Bundle();
                onYesNoDialogListener.onYesButtonClickInYesNODialog(bundle);
                dismiss();
            }else if(v==btNo){
                Bundle bundle = new Bundle();
                onYesNoDialogListener.onNoButtonClickInYesNODialog(bundle);
                dismiss();
            }
        }
    };


    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setTextButtonYes(String text){
        btYes.setText(text);
    }

    public void setTextButtonNo(String text){
        btNo.setText(text);
    }

    public void setTextDetail(String detail){
        tvDetail.setText(detail);
    }

    public interface OnYesNoDialogListener{
        void onYesButtonClickInYesNODialog(Bundle bundle);
        void onNoButtonClickInYesNODialog(Bundle bundle);
    }
}
