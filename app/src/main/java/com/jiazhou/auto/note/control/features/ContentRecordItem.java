package com.jiazhou.auto.note.control.features;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jiazhou.auto.note.utility.ControlPraser;
import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.auto.note.interfaces.listener.OnContentClick;

import java.io.File;

/**
 * Created by lijiazhou on 20/11/16.
 */
public abstract class ContentRecordItem extends LinearLayout implements OnContentClick{

    protected Button delete;
    protected File file;
    protected ImageView recordImage;
    protected Context context;

    protected ContentRecordItem(Context context, File file) {
        super(context);
        this.context = context;
        this.file = file;
        LayoutInflater.from(context).inflate(R.layout.item_content_record, this, true);
        delete = ControlPraser.PraserControl(this, R.id.recordDelete);
        recordImage = ControlPraser.PraserControl(this, R.id.recordImage);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onContentClick();
            }
        });
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
