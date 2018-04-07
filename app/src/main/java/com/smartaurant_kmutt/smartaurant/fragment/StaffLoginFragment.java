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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.cashier.CashierActivity;
import com.smartaurant_kmutt.smartaurant.activity.cashier.CashierTableActivity;
import com.smartaurant_kmutt.smartaurant.activity.kitchen.KitchenActivity;
import com.smartaurant_kmutt.smartaurant.activity.owner.OwnerActivity;
import com.smartaurant_kmutt.smartaurant.activity.staff.StaffActivity;
import com.smartaurant_kmutt.smartaurant.dao.StaffItemDao;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class StaffLoginFragment extends Fragment {
    EditText etEmail;
    EditText etPassword;
    TextView tvWrongPassord;
    Button btSubmit;
    String cashierEmail = "cashier";
    String password = "0000";
    private FirebaseAuth mAuth;

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
        View rootView = inflater.inflate(R.layout.fragmentf_login, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        etEmail = rootView.findViewById(R.id.etEmail);
        etPassword = rootView.findViewById(R.id.etPassword);
        btSubmit = rootView.findViewById(R.id.btSubmit);
        tvWrongPassord = rootView.findViewById(R.id.tvWrongPassword);
        btSubmit.setOnClickListener(onClickListener);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI();
    }

    private void updateUI(String role) {
        switch (role){
            case "Owner":{
                Intent intent = new Intent(getActivity(), OwnerActivity.class);
                StaffLoginFragmentListener staffLoginFragmentListener = (StaffLoginFragmentListener) getActivity();
                staffLoginFragmentListener.onSubmitClicked(intent);
                break;
            }
            case "Staff":{
                Intent intent = new Intent(getActivity(), StaffActivity.class);
                StaffLoginFragmentListener staffLoginFragmentListener = (StaffLoginFragmentListener) getActivity();
                staffLoginFragmentListener.onSubmitClicked(intent);
                break;
            }
            case "Cashier":{
                Intent intent = new Intent(getActivity(), CashierTableActivity.class);
                StaffLoginFragmentListener staffLoginFragmentListener = (StaffLoginFragmentListener) getActivity();
                staffLoginFragmentListener.onSubmitClicked(intent);
                break;
            }
            case "Kitchen":{
                Intent intent = new Intent(getActivity(), KitchenActivity.class);
                StaffLoginFragmentListener staffLoginFragmentListener = (StaffLoginFragmentListener) getActivity();
                staffLoginFragmentListener.onSubmitClicked(intent);
                break;
            }
            case "Manager":{
                Intent intent = new Intent(getActivity(), StaffActivity.class);
                StaffLoginFragmentListener staffLoginFragmentListener = (StaffLoginFragmentListener) getActivity();
                staffLoginFragmentListener.onSubmitClicked(intent);
                break;
            }
            default:{
                tvWrongPassord.setVisibility(View.VISIBLE);
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

    private void signIn(final String email, final String password) {
        DatabaseReference staffDatabase = UtilDatabase.getDatabase().child("staff");
        staffDatabase.orderByChild("email").equalTo(email).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StaffItemDao staff = new StaffItemDao();
                for (DataSnapshot staffChild : dataSnapshot.getChildren()) {
                  staff = staffChild.getValue(StaffItemDao.class);
                }
                if (staff.getRole()!=null&& password.equals(staff.getPassword())) {
                    tvWrongPassord.setVisibility(View.INVISIBLE);
                    MyUtil.showText(staff.getEmail());
                    updateUI(staff.getRole());
                }
                else {
                    tvWrongPassord.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MyUtil.showText("can't select this");
            }
        });
        if(email.equals("owner")){
            updateUI("Owner");
        }
        else if(email.equals("staff")){
            updateUI("Staff");
        }
        else if(email.equals("cashier")){
            updateUI("Cashier");
        }
        else if(email.equals("kitchen")){
            updateUI("Kitchen");
        }
    }



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            StaffLoginFragmentListener staffLoginFragmentListener = (StaffLoginFragmentListener) getActivity();
            Intent intent;
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (MyUtil.checkText(email) && MyUtil.checkText(password)) {
                signIn(email, password);
            }else{
                MyUtil.showText("fill all field please");
            }

//            if(etEmail.getText().toString().trim().equals("cashier")){
//                tvWrongPassord.setVisibility(View.INVISIBLE);
//                intent = new Intent(getActivity(), CashierTableActivity.class);
//                staffLoginFragmentListener.onSubmitClicked(intent);
//            }else if(etEmail.getText().toString().trim().equals("owner")){
//                tvWrongPassord.setVisibility(View.INVISIBLE);
//                intent = new Intent(getActivity(), OwnerActivity.class);
//                staffLoginFragmentListener.onSubmitClicked(intent);
//            }else{
//                tvWrongPassord.setVisibility(View.VISIBLE);
//            }

        }
    };


    public interface StaffLoginFragmentListener {
        void onSubmitClicked(Intent intent);
    }

}
