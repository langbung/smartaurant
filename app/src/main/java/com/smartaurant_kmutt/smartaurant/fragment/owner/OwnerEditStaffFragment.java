package com.smartaurant_kmutt.smartaurant.fragment.owner;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.dao.StaffDao;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.Locale;


@SuppressWarnings("unused")
public class OwnerEditStaffFragment extends Fragment {
    String title;
    Button btSave;
    EditText etName;
    EditText etEmail;
    EditText etPassword;
    String staffName;
    String staffEmail;
    String staffPassword;
    FrameLayout frameLayout;
    Activity activity;
    StaffDao staffDao;

    public OwnerEditStaffFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OwnerEditStaffFragment newInstance(Bundle bundle) {
        OwnerEditStaffFragment fragment = new OwnerEditStaffFragment();
        Bundle args = new Bundle();
        args.putBundle("bundle", bundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        Bundle bundle = getArguments().getBundle("bundle");
        title = bundle.getString("title");
        staffDao = bundle.getParcelable("staffDao");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_edit_staff, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        getActivity().setTitle(title);
        btSave = rootView.findViewById(R.id.btSave);
        etName = rootView.findViewById(R.id.etName);
        etEmail = rootView.findViewById(R.id.etEmail);
        etPassword = rootView.findViewById(R.id.etPassword);
        frameLayout = rootView.findViewById(R.id.frameLayout);
        if (staffDao != null) {
            etName.setText(staffDao.getName());
            etEmail.setText(staffDao.getEmail());
            etPassword.setText(staffDao.getPassword());
        }
        activity = getActivity();
        btSave.setOnClickListener(onClickListener);
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            staffName = etName.getText().toString().trim();
            staffEmail = etEmail.getText().toString().trim();
            staffPassword = etPassword.getText().toString().trim();
            if (MyUtil.checkText(staffName) && MyUtil.checkText(staffEmail) && MyUtil.checkText(staffPassword)) {
                if (staffDao == null) {
                    frameLayout.setVisibility(View.VISIBLE);
                    DatabaseReference utilDatabase = UtilDatabase.getUtilDatabase();
                    DatabaseReference maxStaffIdDatabase = utilDatabase.child("maxStaffId");
                    maxStaffIdDatabase.addListenerForSingleValueEvent(getMaxStaffIdAndUpdateDatabase);
                }else{
                    frameLayout.setVisibility(View.VISIBLE);
                    DatabaseReference staffDatabase = UtilDatabase.getDatabase().child("staff/"+staffDao.getId());
                    staffDao.setName(staffName);
                    staffDao.setEmail(staffEmail);
                    staffDao.setPassword(staffPassword);
                    staffDatabase.setValue(staffDao).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            activity.finish();
                        }
                    });
                }
            }
        }
    };

    ValueEventListener getMaxStaffIdAndUpdateDatabase = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            int maxStaffId = dataSnapshot.getValue(Integer.class);
            String staffId = String.format(Locale.ENGLISH, "ST%03d", maxStaffId + 1);
            StaffDao staff = new StaffDao(staffId, staffName, staffEmail, staffPassword);
            DatabaseReference staffDatabase = UtilDatabase.getDatabase().child("staff");
            staffDatabase.child(staffId).setValue(staff);
            dataSnapshot.getRef().setValue(maxStaffId + 1);
            activity.finish();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
