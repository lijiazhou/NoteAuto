package com.jiazhou.homeauto.homeauto.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.jiazhou.homeauto.homeauto.control.nav.NavItemHeader;

import java.util.List;

/**
 * Created by lijiazhou on 19/10/16.
 */
public class NavAdapter extends BaseExpandableListAdapter {


    private List<NavItemHeader> navItemHeaders;

    public NavAdapter(List<NavItemHeader> navItemHeaders) {
        this.navItemHeaders = navItemHeaders;
    }

    @Override
    public int getGroupCount() {
        return navItemHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return navItemHeaders.get(groupPosition).getChildrenCount();
    }

    @Override
    public View getGroup(int groupPosition) {
        return navItemHeaders.get(groupPosition);
    }

    @Override
    public View getChild(int groupPosition, int childPosition) {
        return navItemHeaders.get(groupPosition).getChild(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        //1/2(k1 + k2)(k1 + k2 + 1) + k2
        return childPosition;//(groupPosition + childPosition)*(groupPosition + childPosition + 1)/2 + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return navItemHeaders.get(groupPosition);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return navItemHeaders.get(groupPosition).getChild(childPosition);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
