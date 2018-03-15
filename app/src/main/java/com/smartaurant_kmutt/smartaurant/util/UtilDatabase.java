package com.smartaurant_kmutt.smartaurant.util;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by LB on 13/3/2561.
 */

public class UtilDatabase {
    private static final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference utilDatabase = database.child("util");
    private static final DatabaseReference menu = database.child("menu");
    int maxMenuId;

    public UtilDatabase() {
    }

    public void setMaxMenuId(int maxMenuId) {
        this.maxMenuId = maxMenuId;
        DatabaseReference maxMenuIdDatabase = utilDatabase.child("maxMenuId");
        maxMenuIdDatabase.setValue(maxMenuId);
    }

    public static DatabaseReference getDatabase() {
        return database;
    }

    public static DatabaseReference getUtilDatabase() {
        return utilDatabase;
    }

    public static DatabaseReference getMenu() {
        return menu;
    }
}
