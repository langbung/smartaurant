package com.smartaurant_kmutt.smartaurant.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.dao.TableItemDao;
import com.smartaurant_kmutt.smartaurant.manager.TableManager;
import com.smartaurant_kmutt.smartaurant.view.TableView;

/**
 * Created by LB on 19/2/2561.
 */

public class TableAdapter extends BaseAdapter {
    private TableManager tableManager;
    public static final int MODE_STAFF = 1;
    public static final int MODE_CASHIER = 4;
    public static final int MODE_CUSTOMER = 2;
    public static final int MODE_STAFF_CALL_WAITER = 3;
    int mode;

    public TableAdapter(int mode) {
        this.mode = mode;
    }

    @Override
    public int getCount() {
        if (tableManager == null)
            return 0;
        if (tableManager.getTableDao().getTableList().size() <= 0)
            return 0;
        return tableManager.getTableDao().getTableList().size();
    }

    @Override
    public Object getItem(int position) {
        return tableManager.getTableDao().getTableList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TableView item;
        if (convertView == null) {
            item = new TableView(parent.getContext());
        } else {
            item = (TableView) convertView;
        }

        int tableNumber = position + 1;
        item.setTable(tableNumber);
        TableItemDao tableItemDao = (TableItemDao) getItem(position);
        if (mode == MODE_CUSTOMER) {
            if (tableItemDao.isAvailableTable())
                item.setBackground(R.drawable.selector_button);
            else
                item.setBackground("#55555555");

        } else if (mode == MODE_STAFF) {
            if (tableItemDao.isAvailableTable())
                item.setBackground("#55555555");
            else
                item.setBackground(R.drawable.selector_button);

        } else if (mode == MODE_CASHIER) {
            if (!tableItemDao.isAvailableCheckBill()) {
                item.setBackground(android.R.color.holo_red_light);
            } else if (tableItemDao.isAvailableTable())
                item.setBackground("#55555555");
            else {
                item.setBackground(R.drawable.selector_button);

            }

        } else if (mode == MODE_STAFF_CALL_WAITER) {
            if (tableItemDao.isAvailableToCallWaiter())
                item.setBackground("#55555555");
            else
                item.setBackground(android.R.color.holo_red_light);
        }


//        final float scale = parent.getContext().getResources().getDisplayMetrics().density;
//        int width = (int) (200 * scale + 0.5f);
//        int height = (int) (100 * scale + 0.5f);
        return item;
    }

    public void setTableManager(TableManager tableManager) {
        this.tableManager = tableManager;
    }
}
