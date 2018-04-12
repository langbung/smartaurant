package com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.kitchen;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.dao.StaffItemDao;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.Arrays;


@SuppressWarnings("unused")
public class KitchenOrderDialog extends DialogFragment {
    TextView tvMenuName;
    TextView tvNote;
    TextView tvTable;
    Button btClose;
    Button btCook;
    OrderMenuKitchenItemDao orderMenuKitchenItemDao;
    KitchenOrderDialogListener kitchenOrderDialogListener;
    int table;

    public KitchenOrderDialog() {
        super();
    }

    @SuppressWarnings("unused")
    public static KitchenOrderDialog newInstance(Bundle bundle) {
        KitchenOrderDialog fragment = new KitchenOrderDialog();
        Bundle args = new Bundle();
        args.putParcelable("bundle", bundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderMenuKitchenItemDao = getArguments().getBundle("bundle").getParcelable("orderMenuKitchenItemDao");
        init(savedInstanceState);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_kitchen_order, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here

    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById
        initTextView(rootView);
        initButton(rootView);

    }

    private void initButton(View rootView) {
        btClose = rootView.findViewById(R.id.btClose);
        btCook = rootView.findViewById(R.id.btCook);
        btCook.setOnClickListener(onClickListener);
        btClose.setOnClickListener(onClickListener);
    }

    private void initTextView(View rootView) {
        tvMenuName = rootView.findViewById(R.id.tvMenuName);
        tvNote = rootView.findViewById(R.id.tvNote);
        tvTable = rootView.findViewById(R.id.tvTable);
        setMenuName(orderMenuKitchenItemDao.getMenuName());
        setNote(orderMenuKitchenItemDao.getNote());

        DatabaseReference orderKitchenDatabase = UtilDatabase.getDatabase().child("order/" + orderMenuKitchenItemDao.getOrderId() + "/table");
        orderKitchenDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                table = dataSnapshot.getValue(Integer.class);
                setTable(table);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        setScreen();

    }

    private void setScreen() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        getDialog().getWindow().setLayout(width*2/4, height*2/3);
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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface OnOptionsStaffDialogListener {
        void onSelectEditStaffOptionDialog(Bundle bundle);
    }

    public void setMenuName(String menuName) {
        tvMenuName.setText(menuName);
    }

    public void setNote(String note) {
        tvNote.setText(note);
    }

    public void setTable(int table) {
        String tableText = table + "";
        tvTable.setText(tableText);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btClose) {
                kitchenOrderDialogListener = (KitchenOrderDialogListener) getTargetFragment();
                kitchenOrderDialogListener.onCloseButtonClickInKitchenOrderDialog();
                dismiss();
            } else if (v == btCook) {
                kitchenOrderDialogListener = (KitchenOrderDialogListener) getTargetFragment();
                kitchenOrderDialogListener.onCookButtonClickInKitchenOrderDialog(orderMenuKitchenItemDao);
                dismiss();
            }
        }
    };

    public interface KitchenOrderDialogListener {
        void onCookButtonClickInKitchenOrderDialog(OrderMenuKitchenItemDao orderMenuKitchenItemDao);

        void onCloseButtonClickInKitchenOrderDialog();
    }

}
