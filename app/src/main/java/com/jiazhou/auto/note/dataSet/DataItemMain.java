package com.jiazhou.auto.note.dataSet;

import java.util.ArrayList;

/**
 * Created by lijiazhou on 18/10/16.
 */
public class DataItemMain extends DataItemBase{

    private String name;
    private int drawable;

    ArrayList<DataDetectedItem> dataDetectedItems;

    public DataItemMain(String name, int drawable, ArrayList<DataDetectedItem> dataDetectedItems) {
        this.name = name;
        this.drawable = drawable;
        if(null  == dataDetectedItems)
            this.dataDetectedItems = new ArrayList<>();
        else
            this.dataDetectedItems = dataDetectedItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if(Observer != null)
            Observer.executeUpdates();
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
        if(Observer != null)
            Observer.executeUpdates();
    }

    public ArrayList<DataDetectedItem> getDataDetectedItems() {
        return dataDetectedItems;
    }

}
