package com.smartaurant_kmutt.smartaurant.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.smartaurant_kmutt.smartaurant.R;

/**
 * Created by LB on 20/2/2561.
 */

public class Loading extends android.support.v4.app.DialogFragment{
    FrameLayout loading;
    public static Loading newInstance(){
        Loading loading = new Loading();
        return loading;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.loading,null);
        builder.setView(rootView);
        iniInstance(rootView);
        return builder.create();
    }

    private void iniInstance(View rootView) {
        loading = rootView.findViewById(R.id.loading);
//        loading.setLayoutParams(new FrameLayout.LayoutParams(200,200));
//        loading.requestLayout();
    }

    @Override
    public void onStart() {
        super.onStart();
        setScreen();
    }

    private void setScreen() {
        int width = 100;
        int height = 100;
        getDialog().getWindow().setLayout(width, height);
        loading.setBackgroundColor(Color.parseColor("#ff333333"));
    }

}
