package com.smartaurant_kmutt.smartaurant.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartaurant_kmutt.smartaurant.manager.StaffManager;
import com.smartaurant_kmutt.smartaurant.view.StaffView;

/**
 * Created by LB on 16/3/2561.
 */

public class OwnerStaffListAdapter extends BaseAdapter {
    StaffManager staffManager;
    @Override
    public int getCount() {
        if(staffManager==null)
            return 0;
        if(staffManager.getStaffDao()==null)
            return 0;
        return staffManager.getStaffDao().getStaffList().size();
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
        StaffView item;
        if(convertView !=null)
            item = (StaffView) convertView;
        else
            item = new StaffView(parent.getContext());
        item.setName(staffManager.getStaffDao().getStaffList().get(position).getName());
        item.setEmail(staffManager.getStaffDao().getStaffList().get(position).getEmail());
        item.setPassword(staffManager.getStaffDao().getStaffList().get(position).getPassword());
        item.setRole(staffManager.getStaffDao().getStaffList().get(position).getRole());
        return item;
    }

    public void setStaffManager(StaffManager staffManager) {
        this.staffManager = staffManager;
    }
}
