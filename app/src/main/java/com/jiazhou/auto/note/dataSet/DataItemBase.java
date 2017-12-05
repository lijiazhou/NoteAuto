package com.jiazhou.auto.note.dataSet;

import com.jiazhou.auto.note.interfaces.DataObserver;

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
