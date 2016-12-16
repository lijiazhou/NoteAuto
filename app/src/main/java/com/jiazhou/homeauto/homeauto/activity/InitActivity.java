package com.jiazhou.homeauto.homeauto.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.homeauto.homeauto.dataSet.DataDetectedItem;
import com.jiazhou.homeauto.homeauto.dataSet.DataItemMain;
import com.jiazhou.homeauto.homeauto.dataSet.DataStruct;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;

public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        createNewItemData();
        //startActivity(new Intent(this, NavActivity.class));
        //createMetaData();
        if(!dataFolderCheck())
            createDataFolder();
        try {
            getDataFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, NavActivity.class));
        finish();
    }

    public void createNewItemData(){
        DataItemMain newItem = new DataItemMain("New Document", R.drawable.new_item, null);
        newItem.setIndex(0);
        DataStruct.mainItems.add(newItem);
    }

    private boolean dataFolderCheck(){
        File file = new File(getApplicationContext().getFilesDir().toString());
        File[] files = file.listFiles();
        for(int i = 0; i < files.length; i++) {
            if(files[i].getName().equals(DataStruct.OFFICEAUTODATAFILES))
                return true;
        }
        return false;
    }

    private void createDataFolder(){
        File file = new File(getApplicationContext().getFilesDir(), DataStruct.OFFICEAUTODATAFILES);
        file.mkdir();
    }

    private void getDataFiles() throws Exception {
        Thread.sleep(5000);
        File files[]  = new File(getApplicationContext().getFilesDir(), DataStruct.OFFICEAUTODATAFILES).listFiles();
        for(int i = 0; i < files.length; i++){
            String[] filename = files[i].list();
            //Handwriting Doc Voice Records Photos
            ArrayList<String> fileArray = new ArrayList<>(Arrays.asList(filename));
            if(fileArray.contains(DataStruct.HANDWRITING_DOC) || fileArray.contains(DataStruct.VOICE_RECORDS) || fileArray.contains(DataStruct.PHTOTS)){
                DataDetectedItem dataDetectedItem1 = new DataDetectedItem(DataStruct.HANDWRITING_DOC);
                DataDetectedItem dataDetectedItem2 = new DataDetectedItem(DataStruct.VOICE_RECORDS);
                DataDetectedItem dataDetectedItem3 = new DataDetectedItem(DataStruct.PHTOTS);
                ArrayList<DataDetectedItem> arrayList = new ArrayList<>();
                arrayList.add(dataDetectedItem1);
                arrayList.add(dataDetectedItem2);
                arrayList.add(dataDetectedItem3);
                DataItemMain dataItemMain = new DataItemMain(files[i].getName(), R.drawable.file_pack, arrayList);
                dataItemMain.setIndex(DataStruct.getLastIndex());
                DataStruct.mainItems.add(dataItemMain);
            }
            else {
                throw new Exception("Sub-folders are not created");
            }
        }
    }
}
