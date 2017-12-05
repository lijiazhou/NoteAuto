package com.jiazhou.auto.note.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import com.jiazhou.homeauto.homeauto.R;

/**
 * Created by lijiazhou on 8/1/17.
 */

public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context, int second) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_loading);
        setCancelable(false);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                LoadingDialog.this.dismiss();
                //Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            }
        }, second * 1000);
    }
}
