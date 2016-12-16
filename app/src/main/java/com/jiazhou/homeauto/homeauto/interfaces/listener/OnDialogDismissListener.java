package com.jiazhou.homeauto.homeauto.interfaces.listener;

import android.app.DialogFragment;

import com.jiazhou.homeauto.homeauto.dataSet.DataItemMain;

/**
 * Created by lijiazhou on 9/11/16.
 */
public interface OnDialogDismissListener {
    void OnDialogDismiss(DialogFragment dialog, DataItemMain dataItemMain);
}
