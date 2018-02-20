package com.smartaurant_kmutt.smartaurant.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.smartaurant_kmutt.smartaurant.R;
import com.smartaurant_kmutt.smartaurant.view.TableView;

/**
 * Created by LB on 19/2/2561.
 */

public class TableAdapter extends BaseAdapter {
        int position;
    @Override
    public int getCount() {
        return 17;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        TableView item;
        if(convertView==null) {
            item = new TableView(parent.getContext());
        }else{
            item = (TableView) convertView;
        }
        int tableNumber=position+1;
       item.setText("Table "+tableNumber);
//        final float scale = parent.getContext().getResources().getDisplayMetrics().density;
//        int width = (int) (200 * scale + 0.5f);
//        int height = (int) (100 * scale + 0.5f);
        return item;
    }

}
