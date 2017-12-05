package com.jiazhou.homeauto.homeauto.interfaces;

import android.view.Menu;
import android.view.MenuInflater;

import com.jiazhou.homeauto.homeauto.implementation.menuImp.MenuImpBase;

import java.util.zip.Inflater;

/**
 * Created by lijiazhou on 5/11/16.
 */
public interface PanelBase  {
    MenuImpBase getMenu();
    void setMenu(Menu menu, MenuInflater inflater);
    void finalizePanel();
}
