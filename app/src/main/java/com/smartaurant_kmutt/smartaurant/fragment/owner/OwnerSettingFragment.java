package com.smartaurant_kmutt.smartaurant.fragment.owner;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.owner.OwnerActivity;
import com.smartaurant_kmutt.smartaurant.dao.TableItemDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.util.Loading;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@SuppressWarnings("unused")
public class OwnerSettingFragment extends Fragment implements YesNoDialog.OnYesNoDialogListener {
    EditText etTable;
    EditText etVat;
    Button btSave;
    int table;
    float vat;
    Loading loading;

    public OwnerSettingFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OwnerSettingFragment newInstance() {
        OwnerSettingFragment fragment = new OwnerSettingFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_owner_setting, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        initToolbar();
        initEditText(rootView);
        initButton(rootView);
        getUtilDatabase();
        initLoadingDialog();
    }

    private void initLoadingDialog() {
        loading = Loading.newInstance();
    }

    private void initToolbar() {
        getActivity().setTitle("Setting");
    }

    private void initButton(View rootView) {
        btSave = rootView.findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float vt;
                int tb;
                if (etTable.getText().toString().equals("")) {
                    showDialog("table can't be blank");
                } else if (etVat.getText().toString().equals("")) {
                    showDialog("Vat can't be blank");
                } else {
                    vt = Float.parseFloat(etVat.getText().toString());
                    tb = Integer.parseInt(etTable.getText().toString());
                    if (tb > 30 || tb < 1) {
                        showDialog("table should be 1 - 30 table");
                    }
                    else if (vt > 50 || vt < 0) {
                        showDialog("vat should be 0% - 50%");
                    }
                    else if (vt != vat || table != tb) {
                        loading.show(getFragmentManager(), "loading");
                        setTableDatabase(tb);
                        Map<String, TableItemDao> tableMap = createListTable(tb);
                        Map<String, Object> updateUtil = new HashMap<>();
                        updateUtil.put("util/maxTable", tb);
                        updateUtil.put("util/vat", vt);
                        DatabaseReference utilDatabase = UtilDatabase.getDatabase();
                        utilDatabase.updateChildren(updateUtil).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    loading.dismiss();
                            }
                        });

                    }
                }

            }
        });
    }

    private void showDialog(String detail) {
        YesNoDialog yesNoDialog = YesNoDialog.newInstance("alert", detail);
        yesNoDialog.setTargetFragment(OwnerSettingFragment.this, 123);
        yesNoDialog.show(getFragmentManager(), "alertDialog");
    }

    private void initEditText(View rootView) {
        etTable = rootView.findViewById(R.id.etTable);
        etVat = rootView.findViewById(R.id.etVat);
    }

    void setTableDatabase(int table) {

        Map<String, TableItemDao> listTable = createListTable(table);
        DatabaseReference tableDatabase = UtilDatabase.getDatabase().child("table");
        tableDatabase.setValue(listTable).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful())
                    MyUtil.showText("can't set table");
            }

        });
    }


    private Map<String, TableItemDao> createListTable(int numberOfTable) {
        Map<String, TableItemDao> listTable = new HashMap<>();
        for (int i = 1; i <= numberOfTable; i++) {
            String tableId = String.format(Locale.ENGLISH, "TB%03d", i);
            TableItemDao tableItem = new TableItemDao(i, true, true, "none",true);
            listTable.put( tableId, tableItem);
        }
        return listTable;
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

    private void getUtilDatabase() {
        DatabaseReference databaseReference = UtilDatabase.getUtilDatabase();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vat = dataSnapshot.child("vat").getValue(Float.class);
                table = dataSnapshot.child("maxTable").getValue(Integer.class);
                String vatText = vat + "";
                String tableText = table + "";
                etVat.setText(vatText);
                etTable.setText(tableText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MyUtil.showText("OwnerFragmentSetting: on cancel");
            }
        });
    }

    @Override
    public void onYesButtonClickInYesNODialog(Bundle bundle,int requestCode) {

    }

    @Override
    public void onNoButtonClickInYesNODialog(Bundle bundle,int requestCode) {

    }
}
