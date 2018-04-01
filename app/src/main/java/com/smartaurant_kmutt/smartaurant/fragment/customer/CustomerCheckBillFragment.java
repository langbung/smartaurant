package com.smartaurant_kmutt.smartaurant.fragment.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.MenuActivity;
import com.smartaurant_kmutt.smartaurant.activity.customer.CustomerActivity;
import com.smartaurant_kmutt.smartaurant.dao.TableItemDao;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.Locale;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class CustomerCheckBillFragment extends Fragment implements YesNoDialog.OnYesNoDialogListener {
    Button btCheckBill;
    int table;
    DatabaseReference tableDatabase;
    public CustomerCheckBillFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static CustomerCheckBillFragment newInstance(Bundle bundle) {
        CustomerCheckBillFragment fragment = new CustomerCheckBillFragment();
        Bundle args = new Bundle();
        args.putBundle("bundle", bundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        table = getArguments().getBundle("bundle").getInt("table");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_check_bill, container, false);
        initInstances(rootView, savedInstanceState);
        setListener();
        return rootView;
    }


    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        btCheckBill = rootView.findViewById(R.id.btCheckBill);
    }

    private void setListener() {
        btCheckBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "Check bill");
                bundle.putString("detail", "Do you want check bill?");
                YesNoDialog yesNoDialog = YesNoDialog.newInstance(bundle);
                yesNoDialog.setTargetFragment(CustomerCheckBillFragment.this, 12);
                yesNoDialog.show(getFragmentManager(), "yesNoDialog");
            }
        });
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

    @Override
    public void onYesButtonClickInYesNODialog(Bundle bundle) {
        MyUtil.showText("Check bill success");
        changeMaxOrderAndStartActivityAgain();

    }

    private void startThisActivity() {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        startActivity(intent);
    }

    private void changeMaxOrderAndStartActivityAgain() {
        DatabaseReference maxOrderDatabase = UtilDatabase.getUtilDatabase().child("maxOrderId");
        maxOrderDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference maxOrderDatabase = UtilDatabase.getUtilDatabase().child("maxOrderId");
                int maxOrder = dataSnapshot.getValue(Integer.class);
                maxOrder++;
                maxOrderDatabase.setValue(maxOrder).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            setTableToNoOrder();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void setTableToNoOrder(){
        String tableIDText = String.format(Locale.ENGLISH,"TB%03d",table);
        tableDatabase = UtilDatabase.getDatabase().child("table/"+tableIDText);

        tableDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TableItemDao tableItemDao = dataSnapshot.getValue(TableItemDao.class);
                tableItemDao.setAvailableTable(true);
                tableItemDao.setOrderId("none");
                tableDatabase.setValue(tableItemDao).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startThisActivity();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNoButtonClickInYesNODialog(Bundle bundle) {
        MyUtil.showText("Cancel check bill");
    }
}
