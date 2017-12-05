package com.jiazhou.auto.note.control.nav;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiazhou.auto.note.utility.ControlPraser;
import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.auto.note.dataSet.DataDetectedItem;
import com.jiazhou.auto.note.interfaces.DataObserver;

/**
 * Created by lijiazhou on 19/10/16.
 */
public class NavChild extends LinearLayout {

    TextView navChild;
    DataDetectedItem dataDetectedItem;
    NavItemHeader parent;

    int index;

    public NavChild(Context context, DataDetectedItem dataDetectedItem, NavItemHeader parent, int index) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_nav_child, this, true);
        navChild = ControlPraser.PraserControl(this, R.id.navChildName);
        this.dataDetectedItem = dataDetectedItem;
        this.parent = parent;
        this.index = index;
        setupChild();
        dataDetectedItem.Observer = new DataObserver() {
            @Override
            public void executeUpdates() {
                setupChild();
            }
        };
    }

    private void setupChild(){
        navChild.setText(dataDetectedItem.getName());
    }
}
