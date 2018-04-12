package com.smartaurant_kmutt.smartaurant.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuOnlyManager;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.util.UtilDatabase;
import com.smartaurant_kmutt.smartaurant.view.OrderKitchenView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LB on 21/3/2561.
 */

public class KitchenOrderListAdapter extends BaseAdapter {
    private OrderMenuOnlyManager orderMenuOnlyManager;
    private OrderMenuKitchenManager orderMenuKitchenManager;
    int mode;
    public static final int MODE_ORDER_KITCHEN_CUSTOMER = 1;
    public static final int MODE_ORDER_KITCHEN_STAFF = 2;

    public KitchenOrderListAdapter() {

    }

    public KitchenOrderListAdapter(int mode) {
        this.mode = mode;
    }

    @Override
    public int getCount() {
        if (orderMenuKitchenManager == null)
            return 0;
        if (orderMenuKitchenManager.getOrderMenuKitchenDao().size() <= 0)
            return 0;
        return orderMenuKitchenManager.getOrderMenuKitchenDao().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderKitchenView item;
        if (convertView != null)
            item = (OrderKitchenView) convertView;
        else
            item = new OrderKitchenView(parent.getContext());
        OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
        item.setName(orderMenuKitchenItemDao.getMenuName());
        item.setQuantity(orderMenuKitchenItemDao.getQuantity());
        item.setNote(orderMenuKitchenItemDao.getNote());
        item.setStatus(orderMenuKitchenItemDao.getStatus());
        if(position == 0){
            item.setZeroIndex();
            setZero(orderMenuKitchenItemDao);
        }
        else
            item.setBackground();



        return item;
    }

    public void setOrderMenuOnlyManager(OrderMenuOnlyManager orderMenuOnlyManager) {
        this.orderMenuOnlyManager = orderMenuOnlyManager;
    }

    public void setOrderMenuKitchenManager(OrderMenuKitchenManager orderMenuKitchenManager) {
        this.orderMenuKitchenManager = orderMenuKitchenManager;
    }
    void setZero(OrderMenuKitchenItemDao orderMenuKitchenItemDao){
        Map<String,Object> updateZero = new HashMap<>();
        String path = "order_kitchen/"+orderMenuKitchenItemDao.getOrderKitchenId()+"/status";
        updateZero.put(path,"cooking");
        DatabaseReference zeroDatabase = UtilDatabase.getDatabase();
        zeroDatabase.updateChildren(updateZero).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    MyUtil.showText("can't set status");
                }
            }
        });
    }

}
