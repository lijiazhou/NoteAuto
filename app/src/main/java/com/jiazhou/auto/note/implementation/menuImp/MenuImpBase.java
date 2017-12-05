package com.jiazhou.auto.note.implementation.menuImp;

import android.content.Context;
import android.view.Menu;


/**
 * Created by lijiazhou on 5/11/16.
 */
public abstract class MenuImpBase {
    Menu menu;
    Context context;

    public MenuImpBase(Context context) {
        this.context = context;
    }

    public boolean setMenuRuning(int menuId){
        if(implement(menuId)) {
            return true;
        }
        else
            throw new IllegalArgumentException("menuId not defined");
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    protected abstract boolean implement(int menuId);
}
