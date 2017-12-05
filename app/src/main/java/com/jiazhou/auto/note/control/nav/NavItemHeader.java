package com.jiazhou.auto.note.control.nav;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiazhou.auto.note.utility.ControlPraser;
import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.auto.note.dataSet.DataItemMain;
import com.jiazhou.auto.note.interfaces.DataObserver;

import java.util.ArrayList;

/**
 * Created by lijiazhou on 18/10/16.
 */
public class NavItemHeader extends LinearLayout {

    DataItemMain dataItemMain;
    ImageView navIcon;
    TextView navName;

    ArrayList<NavChild> navChildren;

    public NavItemHeader(Context context, DataItemMain dataItem) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.control_nav_header, this, true);
        dataItemMain = dataItem;
        navIcon = ControlPraser.PraserControl(this, R.id.navIcon);
        navName = ControlPraser.PraserControl(this, R.id.navName);
        navIcon.setImageResource(dataItemMain.getDrawable());
        navName.setText(dataItemMain.getName());
        createChildren();
        dataItemMain.Observer = new DataObserver() {
            @Override
            public void executeUpdates() {
                setControls();
            }
        };
        
    }

    public int getChildrenCount(){
        return navChildren.size();
    }

    public NavChild getChild(int index){
        return navChildren.get(index);
    }

    private void setControls() {
        navIcon.setImageResource(dataItemMain.getDrawable());
        navName.setText(dataItemMain.getDrawable());
    }

    private void createChildren(){
        navChildren = new ArrayList<>();
        for(int i = 0; i < dataItemMain.getDataDetectedItems().size(); i++){
            navChildren.add(new NavChild(getContext(), dataItemMain.getDataDetectedItems().get(i), this, i));
        }
    }
}
