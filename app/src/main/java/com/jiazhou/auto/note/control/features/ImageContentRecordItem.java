package com.jiazhou.homeauto.homeauto.control.features;

import android.content.Context;

import com.jiazhou.homeauto.homeauto.dialog.ImageViewerDialog;

import java.io.File;

/**
 * Created by lijiazhou on 14/1/17.
 */

public class ImageContentRecordItem extends ContentRecordItem {


    public ImageContentRecordItem(Context context, File file){
        super(context, file);
    }
    @Override
    public void onContentClick() {
        new ImageViewerDialog(context, file.getPath().replace(file.getName(), ""), file.getName()).show();
    }
}
