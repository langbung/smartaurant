package com.smartaurant_kmutt.smartaurant.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartaurant_kmutt.smartaurant.dao.OrderMenuItemDao;
import com.smartaurant_kmutt.smartaurant.dao.OrderMenuKitchenItemDao;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuKitchenManager;
import com.smartaurant_kmutt.smartaurant.manager.OrderMenuOnlyManager;
import com.smartaurant_kmutt.smartaurant.util.MyUtil;
import com.smartaurant_kmutt.smartaurant.view.OrderAndCheckBillMenuViewCustomer;
import com.smartaurant_kmutt.smartaurant.view.OrderMenuViewCustomer;

import java.util.ArrayList;

/**
 * Created by LB on 21/3/2561.
 */

public class CustomerOrderListAdapter extends BaseAdapter {
    private boolean checkDiscount;
    private boolean checkVoucher;
    private float vat;
    private float vatValue;
    private float voucherValue;
    private float discountValue;
    private int discountCondition;
    private OrderMenuOnlyManager orderMenuOnlyManager;
    private OrderMenuKitchenManager orderMenuKitchenManager;
    int mode;
    public static final int MODE_ORDER_KITCHEN_CUSTOMER = 1;
    public static final int MODE_ORDER_KITCHEN_STAFF = 2;

    public CustomerOrderListAdapter() {

    }

    public CustomerOrderListAdapter(int mode) {
        this.mode = mode;
    }

    @Override
    public int getCount() {
        if (mode == MODE_ORDER_KITCHEN_CUSTOMER) {
            if (orderMenuKitchenManager == null)
                return 0;
            if (orderMenuKitchenManager.getOrderMenuKitchenDao().size() <= 0)
                return 0;
            return orderMenuKitchenManager.getOrderMenuKitchenDao().size();
        }
        if (orderMenuKitchenManager == null)
            return 0;
        if (orderMenuKitchenManager.getOrderMenuKitchenDao().size() <= 0)
            return 0;
        if (checkDiscount && checkVoucher)
            return orderMenuKitchenManager.getOrderMenuKitchenDao().size() + 3;
        else if (checkDiscount || checkVoucher)
            return orderMenuKitchenManager.getOrderMenuKitchenDao().size() + 2;
        return orderMenuKitchenManager.getOrderMenuKitchenDao().size() + 1;
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
        OrderAndCheckBillMenuViewCustomer item;
        ArrayList<OrderMenuKitchenItemDao> orderMenuList = orderMenuKitchenManager.getOrderMenuKitchenDao();
        if (convertView != null)
            item = (OrderAndCheckBillMenuViewCustomer) convertView;
        else
            item = new OrderAndCheckBillMenuViewCustomer(parent.getContext());
        if (checkVoucher && checkDiscount) {
            if (position == orderMenuList.size() + 1) {
                item.setName("discount when buy " + discountCondition);
                item.setQuantity(1);
                item.setPrice(discountValue);
                return item;
            } else if (position == orderMenuList.size() + 2) {
                item.setName("voucher");
                item.setQuantity(1);
                item.setPrice(voucherValue);
                return item;
            }else if (position == orderMenuList.size()) {
//                Log.e("vat in adap", vat + "");
                item.setName("vat " + vat + " %");
                item.setQuantity(1);
                item.setPrice(vatValue);
                return item;
            }
            else {
                OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
                item.setName(orderMenuKitchenItemDao.getMenuName() + " " + orderMenuKitchenItemDao.getSize());
                item.setQuantity(orderMenuKitchenItemDao.getQuantity());
                item.setPrice(orderMenuKitchenItemDao.getPrice());
                return item;
            }
        } else if (checkDiscount) {
            if (position == orderMenuList.size() + 1) {
                item.setName("discount when buy " + discountCondition);
                item.setQuantity(1);
                item.setPrice(discountValue);
                return item;
            } else if (position == orderMenuList.size()) {
//                Log.e("vat in adap", vat + "");
                item.setName("vat " + vat + " %");
                item.setQuantity(1);
                item.setPrice(vatValue);
                return item;
            } else {
                OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
                item.setName(orderMenuKitchenItemDao.getMenuName() + " " + orderMenuKitchenItemDao.getSize());
                item.setQuantity(orderMenuKitchenItemDao.getQuantity());
                item.setPrice(orderMenuKitchenItemDao.getPrice());
                return item;
            }
        }
        else if (checkVoucher) {
            if (position == orderMenuList.size() + 1) {
                item.setName("discount when buy " + discountCondition);
                item.setQuantity(1);
                item.setPrice(discountValue);
                return item;
            }else if (position == orderMenuList.size()) {
//                Log.e("vat in adap", vat + "");
                item.setName("Voucher ");
                item.setQuantity(1);
                item.setPrice(voucherValue);
                return item;
            }
            else {
                OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
                item.setName(orderMenuKitchenItemDao.getMenuName() + " " + orderMenuKitchenItemDao.getSize());
                item.setQuantity(orderMenuKitchenItemDao.getQuantity());
                item.setPrice(orderMenuKitchenItemDao.getPrice());
                return item;
            }
        }

        if (mode == MODE_ORDER_KITCHEN_CUSTOMER) {
            OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
//            MyUtil.showText(orderMenuKitchenItemDao.getMenuName());
            item.setName(orderMenuKitchenItemDao.getMenuName() + " " + orderMenuKitchenItemDao.getSize());
            item.setQuantity(orderMenuKitchenItemDao.getQuantity());
            item.setStatus(orderMenuKitchenItemDao.getStatus());
            item.setPrice(orderMenuKitchenItemDao.getPrice());

        } else if (mode == MODE_ORDER_KITCHEN_STAFF) {
            OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
            item.setName(orderMenuKitchenItemDao.getMenuName() + " " + orderMenuKitchenItemDao.getSize());
            item.setQuantity(orderMenuKitchenItemDao.getQuantity());
            item.setStatusAtRight(orderMenuKitchenItemDao.getStatus());
        } else {
            if (position == orderMenuList.size()) {
                Log.e("vat in adap", vat + "");
                item.setName("vat " + vat + " %");
                item.setQuantity(1);
                item.setPrice(vatValue);
                return item;
            }
            OrderMenuKitchenItemDao orderMenuKitchenItemDao = orderMenuKitchenManager.getOrderMenuKitchenDao().get(position);
            item.setName(orderMenuKitchenItemDao.getMenuName() + " " + orderMenuKitchenItemDao.getSize());
            item.setQuantity(orderMenuKitchenItemDao.getQuantity());
            item.setPrice(orderMenuKitchenItemDao.getPrice());
        }
        return item;
    }

    public void setOrderMenuOnlyManager(OrderMenuOnlyManager orderMenuOnlyManager) {
        this.orderMenuOnlyManager = orderMenuOnlyManager;
    }

    public void setOrderMenuKitchenManager(OrderMenuKitchenManager orderMenuKitchenManager) {
        this.orderMenuKitchenManager = orderMenuKitchenManager;
    }

    public void setCheckDiscount(boolean checkDiscount) {
        this.checkDiscount = checkDiscount;
    }

    public void setCheckVoucher(boolean checkVoucher) {
        this.checkVoucher = checkVoucher;
    }

    public void setVoucherValue(float voucherValue) {
        this.voucherValue = voucherValue;
    }

    public void setDiscountValue(float discountValue) {
        this.discountValue = discountValue;
    }

    public void setDiscountCondition(int discountCondition) {
        this.discountCondition = discountCondition;
    }

    public void setVat(float vat) {
        this.vat = vat;
    }

    public void setVatValue(float vatValue) {
        this.vatValue = vatValue;
    }
}
