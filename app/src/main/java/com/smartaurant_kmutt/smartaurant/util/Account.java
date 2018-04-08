package com.smartaurant_kmutt.smartaurant.util;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.dao.StaffItemDao;

public class Account {
    private DatabaseReference staffDatabase = UtilDatabase.getDatabase().child("staff");
    String email;
    String password;
    StaffItemDao staffItemDao;

    public Account() {
    }

    public void signIn(String email, final String password, final AccountListener accountListener){
        Query staffQuery = staffDatabase.orderByChild("email").equalTo(email);
        staffQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot staffDatabase:dataSnapshot.getChildren()){
                    staffItemDao = staffDatabase.getValue(StaffItemDao.class);
                }
                if(staffItemDao!=null && password.equals(staffItemDao.getPassword()))
                    accountListener.getStaffAfterSignIn(staffItemDao.getRole());
                else
                    accountListener.getStaffAfterSignIn("none");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MyUtil.showText("can't get staff data");
            }
        });
    }


    public interface AccountListener {
        void getStaffAfterSignIn(String staffRole);
    }
}
