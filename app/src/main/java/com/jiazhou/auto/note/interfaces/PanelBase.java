package com.jiazhou.auto.note.interfaces;

import android.view.Menu;
import android.view.MenuInflater;

import com.jiazhou.auto.note.implementation.menuImp.MenuImpBase;

/**
 * Created by lijiazhou on 5/11/16.
 */
public interface PanelBase  {
    MenuImpBase getMenu();
    void setMenu(Menu menu, MenuInflater inflater);
    void finalizePanel();
}
