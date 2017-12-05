package com.jiazhou.homeauto.homeauto.dataSet;

/**
 * Created by lijiazhou on 18/10/16.
 */
public class DataDetectedItem extends DataItemBase{
    String name;

    public DataDetectedItem(String name) {
        this.name = name;
        switch (name){
            case "Handwriting Doc": index = 0; break;
            case "Voice Records": index = 1; break;
            case "Photos": index = 2; break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if(Observer != null)
            Observer.executeUpdates();
    }
}
