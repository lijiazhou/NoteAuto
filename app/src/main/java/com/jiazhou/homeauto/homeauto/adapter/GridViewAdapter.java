package com.jiazhou.homeauto.homeauto.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by lijiazhou on 20/11/16.
 */
public class GridViewAdapter extends BaseAdapter {


    ArrayList<View> contentItems;

    public GridViewAdapter(ArrayList<View> contentItems){
        if(null == contentItems)
            throw new IllegalArgumentException("Cannot accept null contentItems !");
        this.contentItems = contentItems;
    }

    @Override
    public int getCount() {
        return contentItems.size();
    }

    @Override
    public View getItem(int position) {
        return contentItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return contentItems.get(position);
    }
}
