package com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.customer;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@SuppressWarnings("unused")
public class OrderDialogFragment extends DialogFragment {
    TextView tvTitle;
    Button btPlus;
    Button btMinus;
    Button btOrder;
    ImageView imageView;
    EditText etQuantity;
    int quantity;
    MenuItemDao menu;
    OrderItemDao orderItemDao;
    int table;

    OnOrderDialogListener onOrderDialogListener;
    int maxOrderId;

    public OrderDialogFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OrderDialogFragment newInstance(Bundle bundle) {
        OrderDialogFragment fragment = new OrderDialogFragment();
        Bundle args = new Bundle();
        args.putBundle("bundle",bundle);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        Bundle bundle = getArguments().getBundle("bundle");
        menu = bundle.getParcelable("menu");
        orderItemDao = bundle.getParcelable("orderItemDao");
        table = bundle.getInt("table");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_customer_order, container, false);
        initInstances(rootView, savedInstanceState);
        setListener();
        return rootView;
    }



    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here

    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById
        btMinus = rootView.findViewById(R.id.btMinus);
        btPlus = rootView.findViewById(R.id.btPlus);
        btOrder = rootView.findViewById(R.id.btOrder);
        imageView = rootView.findViewById(R.id.imageView);
        etQuantity = rootView.findViewById(R.id.etQuantity);
        tvTitle = rootView.findViewById(R.id.tvTitle);
    }

    private void setListener() {
        quantity = Integer.parseInt(etQuantity.getText().toString());
        btPlus.setOnClickListener(onClickListener);
        btMinus.setOnClickListener(onClickListener);
        btOrder.setOnClickListener(onClickListener);
        tvTitle.setText(menu.getName());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setImage(menu.getImageUri());

    }

    private void setImage(String uri){
        RequestOptions requestOptions = RequestOptions
                .placeholderOf(R.drawable.loading)
                .error(android.R.drawable.ic_menu_gallery);
        Glide.with(getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(uri)
                .into(imageView);

    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow()
                .setLayout(750, WindowManager.LayoutParams.WRAP_CONTENT);
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
        try {
        }catch (ClassCastException e){

        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == btPlus){
                quantity++;
                String textQuantity = String.valueOf(quantity);
                etQuantity.setText(textQuantity);
            }else if (v==btMinus){
                quantity--;
                String textQuantity = String.valueOf(quantity);
                etQuantity.setText(textQuantity);
            }
            else if(v==btOrder){
                if(orderItemDao == null){
                    DatabaseReference maxOrderDatabase = UtilDatabase.getUtilDatabase().child("maxOrderId");
                    maxOrderDatabase.addListenerForSingleValueEvent(maxOrderListener);
                }else if(orderItemDao.getOrderList() != null){
                    Map<String,Integer> orderList = orderItemDao.getOrderList();
                    if(orderList.containsKey(menu.getName())){
                        orderList.put(menu.getName(),quantity+orderList.get(menu.getName()));
                    }else{
                        orderList.put(menu.getName(),quantity);
                    }

                    DatabaseReference orderDatabase = UtilDatabase.getDatabase().child("order/"+orderItemDao.getOrderId());
                    orderDatabase.setValue(orderItemDao).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                dismiss();
                            }
                        }
                    });
                }

            }
        }
    };

    ValueEventListener maxOrderListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            maxOrderId = dataSnapshot.getValue(Integer.class);
            maxOrderId++;
            String textMaxOrderId = String.format(Locale.ENGLISH,"OD%04d",maxOrderId);


            Map<String,Integer> orderList = new HashMap<>();
            orderList.put(menu.getName(),quantity);

            orderItemDao = new OrderItemDao();
            orderItemDao.setOrderId(textMaxOrderId);
            orderItemDao.setBeginOrder(true);
            orderItemDao.setOrderList(orderList);
            orderItemDao.setTable(table);

            DatabaseReference orderDatabase = UtilDatabase.getDatabase().child("order/"+orderItemDao.getOrderId());
            orderDatabase.setValue(orderItemDao).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("orderItemDao",orderItemDao);
                        onOrderDialogListener = (OnOrderDialogListener)getTargetFragment();
                        OnOrderDialogListener onOrderDialogListener2 = (OnOrderDialogListener)getActivity()
                                .getSupportFragmentManager().findFragmentById(R.id.contentContainer);
                        onOrderDialogListener.onOrderClick(bundle);
                        onOrderDialogListener2.onOrderClick(bundle);
                        dismiss();
                    }
                }
            });
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public interface OnOrderDialogListener{
        void onOrderClick(Bundle bundle);
    }




}
