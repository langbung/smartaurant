package com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.customer;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.dao.TableItemDao;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;

import java.util.ArrayList;
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
    int table;
    int maxOrKit;
    MenuItemDao menu;
    OrderItemDao orderItemDao;
    OrderKitchenItemDao orderKitchenItemDao;
    OrderMenuKitchenItemDao orderMenuKitchenItemDao;
    TableItemDao tableItem;
    ArrayList<OrderMenuKitchenItemDao> menuListKitchenDao;

    String textMaxOrderId;

    OnOrderDialogListener onOrderDialogListener;

    int maxOrderId;

    public OrderDialogFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OrderDialogFragment newInstance(Bundle bundle) {
        OrderDialogFragment fragment = new OrderDialogFragment();
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
        menu = bundle.getParcelable("menu");
        orderItemDao = bundle.getParcelable("orderItemDao");
        orderKitchenItemDao = bundle.getParcelable("orderKitchenItemDao");
        table = bundle.getInt("table", -1);
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

    private void setImage(String uri) {
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
        } catch (ClassCastException e) {

        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btPlus) {
                quantity++;
                String textQuantity = String.valueOf(quantity);
                etQuantity.setText(textQuantity);
            } else if (v == btMinus) {
                quantity--;
                String textQuantity = String.valueOf(quantity);
                etQuantity.setText(textQuantity);
            } else if (v == btOrder) {
                if (orderItemDao == null) {
                    DatabaseReference maxOrderDatabase = UtilDatabase.getUtilDatabase().child("maxOrderId");
                    maxOrderDatabase.addListenerForSingleValueEvent(maxOrderListener);

                } else if (orderItemDao.getOrderList() != null) {
                    Log.e("orderDialog", "into second if");

//                    Log.e("123",orderList.toString());


                    DatabaseReference maxOrderKitchen = UtilDatabase.getDatabase().child("util/maxOrderKitchen");
                    maxOrderKitchen.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            maxOrKit = dataSnapshot.getValue(Integer.class);
                            maxOrKit++;

                            OrderMenuItemDao orderMenuItemDao = new OrderMenuItemDao();
                            orderMenuItemDao.setQuantity(quantity);
                            orderMenuItemDao.setName(menu.getName());

                            Map<String, OrderMenuItemDao> orderList = orderItemDao.getOrderList();
                            orderList.put(maxOrKit + "", orderMenuItemDao);
                            orderItemDao.setOrderList(orderList);

                            orderMenuKitchenItemDao = new OrderMenuKitchenItemDao();
                            orderMenuKitchenItemDao.setStatus("in queue");
                            orderMenuKitchenItemDao.setQuantity(quantity);
                            orderMenuKitchenItemDao.setMenuName(menu.getName());
                            orderMenuKitchenItemDao.setOrderId(orderItemDao.getOrderId());


                            Map<String, Object> updateChild = new HashMap<>();
                            updateChild.put("order/" + orderItemDao.getOrderId(), orderItemDao);
                            updateChild.put("order_kitchen/" + maxOrKit, orderMenuKitchenItemDao);
//                                        updateChild.put("table/"+String.format(Locale.ENGLISH,"TB%03d",table),tableItem);

                            DatabaseReference databaseReference = UtilDatabase.getDatabase();
                            databaseReference.updateChildren(updateChild).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        DatabaseReference maxOrderKitchen = UtilDatabase.getUtilDatabase().child("maxOrderKitchen");
                                        maxOrderKitchen.setValue(maxOrKit);
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelable("orderItemDao", orderItemDao);
                                        onOrderDialogListener = (OnOrderDialogListener) getActivity()
                                                .getSupportFragmentManager()
                                                .findFragmentById(R.id.contentContainer);
                                        onOrderDialogListener.onOrderClick(bundle);
                                        dismiss();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        }
    };

    ValueEventListener maxOrderListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.e("orderDialog", "into first if");
            maxOrderId = dataSnapshot.getValue(Integer.class);
            maxOrderId++;
            textMaxOrderId = String.format(Locale.ENGLISH, "OD%04d", maxOrderId);

            DatabaseReference maxOrderKitchen = UtilDatabase.getDatabase().child("util/maxOrderKitchen");
            maxOrderKitchen.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    maxOrKit = dataSnapshot.getValue(Integer.class);
                    maxOrKit++;

                    OrderMenuItemDao orderMenuItemDao = new OrderMenuItemDao();
                    orderMenuItemDao.setName(menu.getName());
                    orderMenuItemDao.setQuantity(quantity);

                    Map<String, OrderMenuItemDao> orderList = new HashMap<>();
                    orderList.put(maxOrKit + "", orderMenuItemDao);

                    orderItemDao = new OrderItemDao();
                    orderItemDao.setOrderId(textMaxOrderId);
                    orderItemDao.setBeginOrder(true);
                    orderItemDao.setOrderList(orderList);
                    orderItemDao.setTable(table);
                    orderItemDao.setTotal(0);

                    orderMenuKitchenItemDao = new OrderMenuKitchenItemDao();
                    orderMenuKitchenItemDao.setMenuName(menu.getName());
                    orderMenuKitchenItemDao.setQuantity(quantity);
                    orderMenuKitchenItemDao.setStatus("in queue");
                    orderMenuKitchenItemDao.setOrderId(textMaxOrderId);

                    menuListKitchenDao = new ArrayList<>();
                    menuListKitchenDao.add(orderMenuKitchenItemDao);

                    tableItem = new TableItemDao();
                    tableItem.setAvailableTable(false);
                    tableItem.setAvailableToCallWaiter(true);
                    tableItem.setOrderId(textMaxOrderId);
                    tableItem.setTable(table);

                    Map<String, Object> updateChild = new HashMap<>();
                    updateChild.put("order/" + orderItemDao.getOrderId(), orderItemDao);
                    updateChild.put("order_kitchen/" + maxOrKit, orderMenuKitchenItemDao);
                    updateChild.put("table/" + String.format(Locale.ENGLISH, "TB%03d", table), tableItem);
                    updateChild.put("util/maxOrderKitchen", maxOrKit);
                    updateChild.put("util/maxOrderId", maxOrderId);

                    DatabaseReference databaseReference = UtilDatabase.getDatabase();
                    databaseReference.updateChildren(updateChild).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("orderItemDao", orderItemDao);
                                onOrderDialogListener = (OnOrderDialogListener) getActivity().getSupportFragmentManager()
                                        .findFragmentById(R.id.contentContainer);
                                onOrderDialogListener.onOrderClick(bundle);
                                dismiss();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


//            DatabaseReference orderDatabase = UtilDatabase.getDatabase().child("order/"+orderItemDao.getOrderId());
////            orderDatabase.setValue(orderItemDao).addOnCompleteListener(new OnCompleteListener<Void>() {
////                @Override
////                public void onComplete(@NonNull Task<Void> task) {
////                    if(task.isSuccessful()){
////                        DatabaseReference OrderKitchenDatabase = UtilDatabase.getDatabase().child("order_kitchen/"+orderKitchenItemDao.getOrderId());
////                        OrderKitchenDatabase.setValue(orderKitchenItemDao).addOnCompleteListener(new OnCompleteListener<Void>() {
////                            @Override
////                            public void onComplete(@NonNull Task<Void> task) {
////                                if(task.isSuccessful()){
////                                    Bundle bundle = new Bundle();
////                                    bundle.putParcelable("orderItemDao",orderItemDao);
////                                    bundle.putParcelable("orderKitchenItemDao",orderKitchenItemDao);
////
////                                    onOrderDialogListener = (OnOrderDialogListener) getActivity()
////                                            .getSupportFragmentManager()
////                                            .findFragmentById(R.id.contentContainer);
////                                    onOrderDialogListener.onOrderClick(bundle);
////
////                                    OnOrderDialogListener onOrderDialogListenerToActivity = (OnOrderDialogListener) getActivity();
////                                    onOrderDialogListenerToActivity.onOrderClick(bundle);
////
////                                    DatabaseReference tableDatabase = UtilDatabase.getDatabase().child("table")
////                                            .child(String.format(Locale.ENGLISH,"TB%03d",table));
////                                    TableItemDao tableItem = new TableItemDao();
////                                    tableItem.setAvailableTable(false);
////                                    tableItem.setAvailableToCallWaiter(true);
////                                    tableItem.setOrderId(textMaxOrderId);
////
////                                    tableDatabase.setValue(tableItem).addOnCompleteListener(new OnCompleteListener<Void>() {
////                                        @Override
////                                        public void onComplete(@NonNull Task<Void> task) {
////                                            if(task.isSuccessful()){
////                                                dismiss();
////                                            }
////                                        }
////                                    });
////                                }
////                            }
////                        });
////
////
////                    }
////                }
////            });
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };

    public interface OnOrderDialogListener {
        void onOrderClick(Bundle bundle);
    }


}
