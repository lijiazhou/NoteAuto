package com.jiazhou.auto.note.control.panel;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiazhou.auto.note.control.features.ContentRecordItem;
import com.jiazhou.auto.note.control.features.ImageContentRecordItem;
import com.jiazhou.auto.note.dataSet.DataItemMain;
import com.jiazhou.auto.note.dataSet.DataStruct;
import com.jiazhou.auto.note.implementation.menuImp.MenuImpBase;
import com.jiazhou.auto.note.interfaces.PanelBase;
import com.jiazhou.auto.note.utility.ControlPraser;
import com.jiazhou.auto.note.adapter.GridViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lijiazhou on 20/11/16.
 */
public class ImageContainer extends GridView implements PanelBase {

    DataItemMain dataItemMain;
    ArrayList<File> files;
    ArrayList<View> contentRecordItems;

    public ImageContainer(final Context context, DataItemMain dataItemMain) {
        super(context);
        //LayoutInflater.from(context).inflate(R.layout.control_record_container, this, true);
        this.dataItemMain = dataItemMain;
        contentRecordItems = new ArrayList<>();
        setNumColumns(GridView.AUTO_FIT);

        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        setFocusable(true);
        loadFiles();
        createView();
//        setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                new ImageViewerDialog(context,
//                        getContext().getFilesDir().getPath() + "/" + DataStruct.OFFICEAUTODATAFILES + "/" + ImageContainer.this.dataItemMain.getName() + "/" + DataStruct.PHTOTS,
//                        files.get(position).getName())
//                .show();
//            }
//        });

        ViewTreeObserver vto =  getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int column = ControlPraser.px2dip(getContext(), getWidth()) / 132;
                setNumColumns(column);
            }
        });
    }

    @Override
    public void finalizePanel(){
        try {
            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public MenuImpBase getMenu() {
        return null;
    }

    @Override
    public void setMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    private void loadFiles(){
        String path = getContext().getFilesDir().getPath() + "/" + DataStruct.OFFICEAUTODATAFILES + "/" + dataItemMain.getName() + "/" + DataStruct.PHTOTS;
        File file = new File(path);
        File[] files = file.listFiles();
        if(files.length == 0){
            Toast.makeText(getContext(), "No records found!", Toast.LENGTH_LONG);
            return;
        }

        this.files = new ArrayList(Arrays.asList(files));
    }

    private void createView(){
        if(null == files)
            return;

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentRecordItem contentRecordItem = (ContentRecordItem)v;
                contentRecordItems.remove(contentRecordItem);
                files.remove(contentRecordItem.getFile());
                contentRecordItem.getFile().delete();
                ImageContainer.this.setAdapter();
            }
        };

        for(int i = 0; i < files.size(); i++){
            ContentRecordItem contentRecordItem = new ImageContentRecordItem(getContext(), files.get(i));
            contentRecordItem.setRecordImage();
            contentRecordItem.setDeleteClick(onClickListener);
            contentRecordItems.add(contentRecordItem);
        }
        setAdapter();
    }

    private void setAdapter(){
        if(contentRecordItems == null)
            return;

        GridViewAdapter gridViewAdapter = new GridViewAdapter(contentRecordItems);
        this.setAdapter(null);
        this.setAdapter(gridViewAdapter);
    }
}
