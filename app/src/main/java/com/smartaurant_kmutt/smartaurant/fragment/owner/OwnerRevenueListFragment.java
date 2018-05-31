package com.smartaurant_kmutt.smartaurant.fragment.owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.activity.owner.OwnerDetailOrderListActivity;
import com.smartaurant_kmutt.smartaurant.adapter.OrderListAdapter;
import com.smartaurant_kmutt.smartaurant.dao.OrderItemDao;
import com.smartaurant_kmutt.smartaurant.manager.OrderListManager;
import com.smartaurant_kmutt.smartaurant.util.Loading;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@SuppressWarnings("unused")
public class OwnerRevenueListFragment extends Fragment {
    Button btFilter;
    Button btNotFilter;
    ArrayList<String> dateList;
    ArrayList<String> monthList;
    ArrayList<String> yearList;
    ArrayList<OrderItemDao> orderList;

    String date;
    String month;
    String year;

    ListView lvRevenue;
    OrderListManager orderListManager;
    OrderListAdapter orderListAdapter;
    TextView tvTotal;
    BetterSpinner dateSpinner;
    BetterSpinner monthSpinner;
    BetterSpinner yearSpinner;
    float total;
    Loading loading = Loading.newInstance();
    OrderItemDao orderItemDao;
    View view;

    public OwnerRevenueListFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static OwnerRevenueListFragment newInstance() {
        OwnerRevenueListFragment fragment = new OwnerRevenueListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_owner_revenue, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        view = rootView;
        getActivity().setTitle("Revenue");
        initListViewRevenue(rootView);
        tvTotal = rootView.findViewById(R.id.tvTotal);
        initSpinner(rootView);
        initButton(rootView);
    }

    private void initButton(View rootView) {
        btFilter = rootView.findViewById(R.id.btFilter);
        btFilter.setOnClickListener(onClickListener);
        btNotFilter = rootView.findViewById(R.id.btNotFilter);
        btNotFilter.setOnClickListener(onClickListener);
    }

    private void initListViewRevenue(View rootView) {
        lvRevenue = rootView.findViewById(R.id.lvRevenue);
        orderListAdapter = new OrderListAdapter();
        orderListManager = new OrderListManager();
        lvRevenue.setAdapter(orderListAdapter);
        setOrderRealTime();
        lvRevenue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orderItemDao = orderListManager.getOrderList().get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("orderItemDao", orderItemDao);
                Intent intent = new Intent(getActivity(), OwnerDetailOrderListActivity.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }

    private void initSpinner(View rootView) {
        yearSpinner = rootView.findViewById(R.id.yearSpinner);
        dateSpinner = rootView.findViewById(R.id.dateSpinner);
        monthSpinner = rootView.findViewById(R.id.monthSpinner);

        yearList = getYear();
        monthList = getMonth();
        dateList = getDate();

        ArrayAdapter monthAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, monthList);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                month = monthList.get(position);
            }
        });
        ArrayAdapter yearAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, yearList);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                year = yearList.get(position);
            }
        });
        ArrayAdapter dateAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, dateList);
        dateSpinner.setAdapter(dateAdapter);
        dateSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                date = dateList.get(position);
            }
        });

    }

    private ArrayList<String> getDate() {
        int count;
        ArrayList<String> date = new ArrayList<>();
        for (count = 1; count <= 31; count++) {
            date.add(String.format(Locale.ENGLISH, "%02d", count));
        }
        return date;
    }

    private ArrayList<String> getMonth() {
        int count;
        ArrayList<String> month = new ArrayList<>();
        for (count = 1; count <= 12; count++) {
            month.add(String.format(Locale.ENGLISH, "%02d", count));
        }
        return month;
    }

    private ArrayList<String> getYear() {
        int count;
        int yearNum = 2017;
        ArrayList<String> year = new ArrayList<>();
        for (count = 1; count <= 2; count++) {
            yearNum += 1;
            year.add(yearNum + "");
        }
        return year;
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

    private void setOrderRealTime() {
        loading.show(getFragmentManager(), "l");
        DatabaseReference orderDatabase = UtilDatabase.getDatabase().child("order");
        orderDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderList = new ArrayList<>();
                for (DataSnapshot orderItem : dataSnapshot.getChildren()) {
//                    OrderItemDao orderItemDao = orderItemDao.getValue(OrderItemDao.class);
                    Map<String, String> date = new HashMap<>();
                    DataSnapshot dateData = orderItem.child("dateTime");
                    date.put("day", dateData.child("day").getValue(String.class));
                    date.put("month", dateData.child("month").getValue(String.class));
                    date.put("time", dateData.child("time").getValue(String.class));
                    date.put("year", dateData.child("year").getValue(String.class));
//
                    orderItemDao = new OrderItemDao();
                    orderItemDao.setOrderId(orderItem.child("orderId").getValue(String.class));
                    orderItemDao.setTable(orderItem.child("table").getValue(Integer.class));
                    orderItemDao.setTotal(orderItem.child("total").getValue(Float.class));
                    orderItemDao.setDateTime(date);
                    total += orderItemDao.getTotal();
                    orderList.add(orderItemDao);
                }
                DecimalFormat df = new DecimalFormat("###,##0.00");
                tvTotal.setText(df.format(total));
                orderList = sortOrderList(orderList);
                orderListManager.setOrderList(orderList);
                orderListAdapter.setOrderListManager(orderListManager);
                orderListAdapter.notifyDataSetChanged();

                loading.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private ArrayList<OrderItemDao> sortOrderList(ArrayList<OrderItemDao> orderItemDaoList) {
        int count = orderItemDaoList.size();
        OrderItemDao tempOrder;
        String orderId1;
        String orderId2;
        OrderItemDao orderItemDao1;
        OrderItemDao orderItemDao2;
        for (int i = 0; i < count; i++) {
            if (i == count - 1)
                break;
            orderId1 = orderItemDaoList.get(i).getOrderId();
            orderItemDao1 = orderItemDaoList.get(i);
            for (int j = i + 1; j < count; j++) {
                orderId2 = orderItemDaoList.get(j).getOrderId();
                orderItemDao2 = orderItemDaoList.get(j);
                if (orderId1.compareTo(orderId2) < 0) {
                    tempOrder = orderItemDao1;
                    orderItemDaoList.set(i, orderItemDao2);
                    orderItemDaoList.set(j, tempOrder);
                    orderItemDao1 = orderItemDaoList.get(i);
                    orderId1 = orderItemDao1.getOrderId();
                }
            }
        }
        return orderItemDaoList;
    }

    private ArrayList<OrderItemDao> filter(String date, String month, String year, ArrayList<OrderItemDao> orderItemDaoList) {
        int mode = 0;
        System.out.println("mode = 0");
        if (!date.equals("") && !month.equals("") && !year.equals("")) {
            mode = 1;
            System.out.println("mode = 1");
        } else if (!date.equals("") && !month.equals("")) {
            mode = 2;
            System.out.println("mode = 2");
        } else if (!date.equals("") && !year.equals("")) {
            mode = 3;
            System.out.println("mode = 3");
        } else if (!month.equals("") && !year.equals("")) {
            mode = 4;
            System.out.println("mode = 4");
        } else if (!date.equals("")) {
            mode = 5;
            System.out.println("mode = 5");
        } else if (!month.equals("")) {
            mode = 6;
            System.out.println("mode = 6");
        } else if (!year.equals("")) {
            mode = 7;
            System.out.println("mode = 7");
        }
        float filterTotal;
        ArrayList<OrderItemDao> filterOrderItemList = new ArrayList<>();
        int count = orderItemDaoList.size();
        String orderId1;
        OrderItemDao orderItemDao1;
        DecimalFormat df = new DecimalFormat("###,##0.00");
        switch (mode) {
            case 1: {
                filterTotal = 0;
                for (int i = 0; i < count; i++) {
                    String mDay = orderItemDaoList.get(i).getDateTime().get("day");
                    String mMonth = orderItemDaoList.get(i).getDateTime().get("month");
                    String mYear = orderItemDaoList.get(i).getDateTime().get("year");
                    if (mDay.equals(date) && mMonth.equals(month) && mYear.equals(year)) {
                        filterOrderItemList.add(orderItemDaoList.get(i));
                        filterTotal += orderItemDaoList.get(i).getTotal();
                    }
                }
                tvTotal.setText(df.format(filterTotal));
                break;
            }
            case 2: {
                filterTotal = 0;
                for (int i = 0; i < count; i++) {
                    String mDay = orderItemDaoList.get(i).getDateTime().get("day");
                    String mMonth = orderItemDaoList.get(i).getDateTime().get("month");
                    if (mDay.equals(date) && mMonth.equals(month)) {
                        filterOrderItemList.add(orderItemDaoList.get(i));
                        filterTotal += orderItemDaoList.get(i).getTotal();
                    }
                }
                tvTotal.setText(df.format(filterTotal));
                break;
            }

            case 3: {
                filterTotal = 0;
                for (int i = 0; i < count; i++) {
                    String mDay = orderItemDaoList.get(i).getDateTime().get("day");
                    String mYear = orderItemDaoList.get(i).getDateTime().get("year");
                    if (mDay.equals(date) && mYear.equals(year)) {
                        filterOrderItemList.add(orderItemDaoList.get(i));
                        filterTotal += orderItemDaoList.get(i).getTotal();
                    }
                }
                tvTotal.setText(df.format(filterTotal));
                break;
            }
            case 4: {
                filterTotal = 0;
                for (int i = 0; i < count; i++) {
                    String mMonth = orderItemDaoList.get(i).getDateTime().get("month");
                    String mYear = orderItemDaoList.get(i).getDateTime().get("year");
                    if (mMonth.equals(month) && mYear.equals(year)) {
                        filterOrderItemList.add(orderItemDaoList.get(i));
                        filterTotal += orderItemDaoList.get(i).getTotal();
                    }
                }
                tvTotal.setText(df.format(filterTotal));
                break;
            }
            case 5: {
                filterTotal = 0;
                for (int i = 0; i < count; i++) {
                    String mDay = orderItemDaoList.get(i).getDateTime().get("day");
                    if (mDay.equals(date)) {
                        filterOrderItemList.add(orderItemDaoList.get(i));
                        filterTotal += orderItemDaoList.get(i).getTotal();
                    }
                }
                tvTotal.setText(df.format(filterTotal));
                break;
            }
            case 6: {
                filterTotal = 0;
                String mMonth = "";
                for (int i = 0; i < count; i++) {
                    mMonth = orderItemDaoList.get(i).getDateTime().get("month");
                    if (mMonth.equals(month)) {
                        filterOrderItemList.add(orderItemDaoList.get(i));
                        filterTotal += orderItemDaoList.get(i).getTotal();
                    }
                }
                tvTotal.setText(df.format(filterTotal));
                break;
            }
            case 7: {
                filterTotal = 0;
                for (int i = 0; i < count; i++) {
                    String mYear = orderItemDaoList.get(i).getDateTime().get("year");
                    if (mYear.equals(year)) {
                        filterOrderItemList.add(orderItemDaoList.get(i));
                        filterTotal += orderItemDaoList.get(i).getTotal();
                    }
                }
                tvTotal.setText(df.format(filterTotal));
                break;
            }
            case 0: {
                tvTotal.setText(df.format(total));
                return orderItemDaoList;
            }
        }

        return filterOrderItemList;
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
            if (v == btNotFilter) {
                dateSpinner.setText("");
                monthSpinner.setText("");
                yearSpinner.setText("");

                dateSpinner.clearFocus();
                monthSpinner.clearFocus();
                yearSpinner.clearFocus();
                ArrayList<OrderItemDao> filterList = filter("", "", "", orderList);
                orderListManager.setOrderList(filterList);
                orderListAdapter.setOrderListManager(orderListManager);
                orderListAdapter.notifyDataSetChanged();

            }
            if (v == btFilter) {
                loading.show(getFragmentManager(), "l");
                String date = dateSpinner.getText().toString();
                String month = monthSpinner.getText().toString();
                String year = yearSpinner.getText().toString();
                ArrayList<OrderItemDao> filterList = filter(date, month, year, orderList);
                orderListManager.setOrderList(filterList);
                orderListAdapter.setOrderListManager(orderListManager);
                orderListAdapter.notifyDataSetChanged();
                loading.dismiss();
            }
        }

    };

}
