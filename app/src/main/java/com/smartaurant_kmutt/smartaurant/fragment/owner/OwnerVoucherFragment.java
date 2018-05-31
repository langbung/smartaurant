package com.smartaurant_kmutt.smartaurant.fragment.owner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.owner.OwnerActivity;
import com.smartaurant_kmutt.smartaurant.activity.owner.OwnerDetailOrderListActivity;
import com.smartaurant_kmutt.smartaurant.adapter.CustomerOrderListAdapter;
import com.smartaurant_kmutt.smartaurant.dao.MenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.dao.VoucherItem;
import com.smartaurant_kmutt.smartaurant.fragment.dialogFragment.YesNoDialog;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.util.Loading;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;
import com.smartaurant_kmutt.smartaurant.util.Voucher;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;


@SuppressWarnings("unused")
public class OwnerVoucherFragment extends Fragment {

    OwnerActivity activity;
    Button btCopy;
    Button bt50;
    Button bt150;
    Button bt100;
    Button bt200;
    Button bt1000;
    Button bt500;
    VoucherItem voucherItem;
    TextView tvVoucherCode;

    public OwnerVoucherFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OwnerVoucherFragment newInstance() {
        OwnerVoucherFragment fragment = new OwnerVoucherFragment();
        Bundle args = new Bundle();
//        args.putBundle("bundle", bundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
//        Bundle bundle = getArguments().getBundle("bundle");
//        orderItemDao = bundle.getParcelable("orderItemDao");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_owner_voucher, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        initToolbar(rootView);
        initButton(rootView);
        initTextView(rootView);

    }

    private void initButton(View rootView) {
        bt50 = rootView.findViewById(R.id.bt50);
        bt100= rootView.findViewById(R.id.bt100);
        bt150= rootView.findViewById(R.id.bt150);
        bt200= rootView.findViewById(R.id.bt200);
        bt500= rootView.findViewById(R.id.bt500);
        bt1000= rootView.findViewById(R.id.bt1000);
        btCopy= rootView.findViewById(R.id.btCopy);

        bt50.setOnClickListener(onClickListener);
        bt100.setOnClickListener(onClickListener);
        bt150.setOnClickListener(onClickListener);
        bt200.setOnClickListener(onClickListener);
        bt500.setOnClickListener(onClickListener);
        bt1000.setOnClickListener(onClickListener);
        btCopy.setOnClickListener(onClickListener);
    }


    private void initTextView(View rootView) {
        tvVoucherCode = rootView.findViewById(R.id.tvVoucherCode);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (OwnerActivity) getActivity();
    }

    private void initToolbar(View rootView) {
        activity.setTitle("Voucher generator");
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
    private void genVoucherCode(int discount){
        Voucher voucher = new Voucher();
        voucherItem = voucher.getVoucher(discount,30);
        tvVoucherCode.setText(voucherItem.getId());
        if(tvVoucherCode.getVisibility()!=View.VISIBLE){
            tvVoucherCode.setVisibility(View.VISIBLE);
            btCopy.setVisibility(View.VISIBLE);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(bt50==v){
                genVoucherCode(50);
            }else if(bt100==v){
                genVoucherCode(100);
            }
            else if(bt150==v){
                genVoucherCode(150);
            }
            else if(bt200==v){
                genVoucherCode(200);
            }
            else if(bt500==v){
                genVoucherCode(500);
            }else if(bt1000==v){
                genVoucherCode(1000);
            }
            else if(v==btCopy){
                Voucher voucher = new Voucher();
                voucher.updateVoucher(voucherItem);
                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("voucherCode", tvVoucherCode.getText().toString());
                clipboard.setPrimaryClip(clip);
                MyUtil.showText("Voucher code was copied");
            }
        }
    };

}
