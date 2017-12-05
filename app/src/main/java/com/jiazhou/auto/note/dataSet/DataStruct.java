package com.jiazhou.homeauto.homeauto.dataSet;

import android.content.Context;

import com.jiazhou.homeauto.homeauto.control.nav.NavItemHeader;

import java.util.ArrayList;

/**
 * Created by lijiazhou on 18/10/16.
 */
public class DataStruct extends DataItemBase{

    public static ArrayList<DataItemMain> mainItems = new ArrayList<>();

    public static ArrayList<NavItemHeader> GenerateNavHeaders(Context context){
        ArrayList<NavItemHeader> headers = new ArrayList<>();
        for(int i = 0; i < mainItems.size(); i++){
            headers.add(new NavItemHeader(context, mainItems.get(i)));
        }
        return headers;
    }

    public static int getLastIndex(){
        return DataStruct.mainItems.get(DataStruct.mainItems.size() - 1).getIndex() + 1;
    }


    public static int searchByItemMianName(String name){
        for(int i = 0; i < mainItems.size(); i++){
            if(mainItems.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }


    //data constant name

    public static final String NEW_DOCUMENT = "New Document";
    public static final String VOICE_RECORDS = "Voice Records";
    public static final String HANDWRITING_DOC = "Handwriting Doc";
    public static final String PHTOTS = "Photos";

    public static final String OFFICEAUTODATAFILES = "OfficeAutoDataFiles";
}
