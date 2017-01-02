package com.jiazhou.homeauto.homeauto.activity;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.jiazhou.homeauto.homeauto.dialog.DataItemDialog;
import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.homeauto.homeauto.adapter.NavAdapter;
import com.jiazhou.homeauto.homeauto.control.panel.ImageContainer;
import com.jiazhou.homeauto.homeauto.control.panel.PicPainter;
import com.jiazhou.homeauto.homeauto.control.panel.RecordContainer;
import com.jiazhou.homeauto.homeauto.dataSet.DataItemMain;
import com.jiazhou.homeauto.homeauto.dataSet.DataStruct;
import com.jiazhou.homeauto.homeauto.implementation.menuImp.MenuImpBase;
import com.jiazhou.homeauto.homeauto.interfaces.PanelBase;
import com.jiazhou.homeauto.homeauto.interfaces.listener.OnDialogDismissListener;
import com.jiazhou.homeauto.homeauto.utility.ControlPraser;

import java.io.File;

public class NavActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    //FrameLayout frameLayout;
    FloatingActionButton fab;
    MenuImpBase menuImp;
    Menu menu;
    LinearLayout functionPanel;

    int currentGroup = -1;
    int currentChild = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        functionPanel = ControlPraser.PraserControl(this, R.id.functionPanel);
        expandableListView = ControlPraser.PraserControl(this, R.id.navBar);
        expandableListView.setAdapter(new NavAdapter(DataStruct.GenerateNavHeaders(NavActivity.this)));
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 0) {
                    DataItemDialog dataItemDialog = new DataItemDialog(NavActivity.this);
                    dataItemDialog.show();

                    dataItemDialog.setOnDialogDismissListener(new OnDialogDismissListener() {
                        @Override
                        public void OnDialogDismiss(Dialog dialog, DataItemMain dataItemMain) {
                            if (dataItemMain == null)
                                return;
                            boolean[] expanded = new boolean[DataStruct.mainItems.size()];
                            for (int i = 0; i < DataStruct.mainItems.size(); i++)
                                expanded[i] = expandableListView.isGroupExpanded(i);
                            dataItemMain.setIndex(DataStruct.getLastIndex());
                            DataStruct.mainItems.add(dataItemMain);

                            expandableListView.setAdapter(new NavAdapter(DataStruct.GenerateNavHeaders(NavActivity.this)));
                            for (int i = 0; i < expanded.length; i++)
                                if (expanded[i])
                                    expandableListView.expandGroup(i);

                            File file = new File(getApplicationContext().getFilesDir() + "/" + DataStruct.OFFICEAUTODATAFILES, dataItemMain.getName());
                            file.mkdir();
                            for (int i = 0; i < dataItemMain.getDataDetectedItems().size(); i++) {
                                File loopFile = new File(getApplicationContext().getFilesDir() + "/" + DataStruct.OFFICEAUTODATAFILES + "/" + dataItemMain.getName(), dataItemMain.getDataDetectedItems().get(i).getName());
                                loopFile.mkdir();
                            }
                        }
                    });
                } else {
                    if (expandableListView.isGroupExpanded(groupPosition))
                        expandableListView.collapseGroup(groupPosition);
                    else
                        expandableListView.expandGroup(groupPosition);
                }

                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(groupPosition == currentGroup && childPosition == currentChild)
                    return true;

                switch (DataStruct.mainItems.get(groupPosition).getDataDetectedItems().get(childPosition).getName()){
                    case DataStruct.HANDWRITING_DOC:
                        PanelBase picDrawer = new PicPainter(NavActivity.this, DataStruct.mainItems.get(groupPosition).getName());
                        addPanelView(picDrawer);
                        break;
                    case DataStruct.VOICE_RECORDS:
                        RecordContainer recordContainer = new RecordContainer(NavActivity.this, DataStruct.mainItems.get(groupPosition));
                        recordContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        });

                        addPanelView(recordContainer);
                        break;
                    case DataStruct.PHTOTS:
                        ImageContainer imageContainer = new ImageContainer(NavActivity.this, DataStruct.mainItems.get(groupPosition));
                        imageContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        });

                        addPanelView(imageContainer);
                        break;
                }

                currentChild = childPosition;
                currentGroup = groupPosition;
                return true;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.pic_drawer_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(null == menuImp)
            return true;
        menuImp.setMenuRuning(item.getItemId());
        return true ;
    }

    private void addPanelView(PanelBase panelBase){
        if(functionPanel.getChildCount() > 0)
            functionPanel.removeAllViews();
        functionPanel.addView((View)panelBase);
        panelBase.setMenu(menu, getMenuInflater());
        menuImp = panelBase.getMenu();
    }

}
