package com.jiazhou.homeauto.homeauto.control.features;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.homeauto.homeauto.utility.ControlPraser;

import java.io.File;

/**
 * Created by lijiazhou on 20/11/16.
 */
public class ContentRecordItem extends LinearLayout {

    Button delete;
    File file;
    ImageView recordImage;

    public ContentRecordItem(Context context, File file) {
        super(context);
        this.file = file;
        LayoutInflater.from(context).inflate(R.layout.item_content_record, this, true);
        delete = ControlPraser.PraserControl(this, R.id.recordDelete);
        recordImage = ControlPraser.PraserControl(this, R.id.recordImage);
    }

    public void setDeleteClick(final OnClickListener onClickListener){
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onClickListener)
                    onClickListener.onClick(ContentRecordItem.this);
            }
        });
    }

    public File getFile() {
        return file;
    }

    public void setRecordImage(){
        recordImage.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
    }
}
