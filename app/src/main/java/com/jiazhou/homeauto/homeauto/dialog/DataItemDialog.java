package com.jiazhou.homeauto.homeauto.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.homeauto.homeauto.dataSet.DataDetectedItem;
import com.jiazhou.homeauto.homeauto.dataSet.DataItemMain;
import com.jiazhou.homeauto.homeauto.dataSet.DataStruct;
import com.jiazhou.homeauto.homeauto.interfaces.listener.OnDialogDismissListener;
import com.jiazhou.homeauto.homeauto.utility.ControlPraser;

import java.util.ArrayList;

/**
 * Created by lijiazhou on 6/11/16.
 */
public class DataItemDialog extends Dialog {

    EditText txtName;
    Button txtClear;
    Button nameOk;
    Button nameCancel;
    DataItemMain dataItemMain;
    OnDialogDismissListener onDialogDismissListener;


    public  DataItemDialog(Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_create_document);
        setCancelable(false);
        txtName = ControlPraser.PraserControl(this, R.id.txtName);
        txtClear = ControlPraser.PraserControl(this, R.id.txtNameDelete);

        nameOk = ControlPraser.PraserControl(this, R.id.nameOk);
        nameCancel = ControlPraser.PraserControl(this, R.id.nameCancel);

        txtClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtName.setText("");
            }
        });

        nameOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtName.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the name!", Toast.LENGTH_LONG);
                    return;
                }

                if(DataStruct.searchByItemMianName(txtName.getText().toString().trim()) != -1){
                    Toast.makeText(getContext(), "Item name already exists!", Toast.LENGTH_LONG);
                    return;
                }

                ArrayList<DataDetectedItem> detectedItems = new ArrayList<DataDetectedItem>();
                DataDetectedItem doc = new DataDetectedItem(DataStruct.HANDWRITING_DOC);
                DataDetectedItem record = new DataDetectedItem(DataStruct.VOICE_RECORDS);
                DataDetectedItem pic = new DataDetectedItem(DataStruct.PHTOTS);
                detectedItems.add(doc);
                detectedItems.add(record);
                detectedItems.add(pic);
                dataItemMain = new DataItemMain(txtName.getText().toString(), R.drawable.file_pack, detectedItems);
                dismiss();
                if(onDialogDismissListener != null)
                    onDialogDismissListener.OnDialogDismiss(DataItemDialog.this, dataItemMain);
            }

        });

        nameCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public DataItemMain getDataItemMain() {
        return dataItemMain;
    }

    public void setOnDialogDismissListener(OnDialogDismissListener onDialogDismissListener){
        this.onDialogDismissListener = onDialogDismissListener;
    }
}
