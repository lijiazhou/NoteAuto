package com.jiazhou.homeauto.homeauto.dataSet;

import com.jiazhou.homeauto.homeauto.interfaces.DataObserver;

import java.io.File;

/**
 * Created by lijiazhou on 18/10/16.
 */
public abstract class DataItemBase {

    public DataObserver Observer;

    protected int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
