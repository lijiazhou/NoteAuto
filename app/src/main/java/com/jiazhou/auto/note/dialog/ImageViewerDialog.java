package com.jiazhou.auto.note.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiazhou.auto.note.control.features.TouchImageView;
import com.jiazhou.auto.note.utility.ControlPraser;
import com.jiazhou.homeauto.homeauto.R;

import java.io.File;

/**
 * Created by lijiazhou on 14/1/17.
 */

public class ImageViewerDialog extends Dialog {

    String path;
    String fileName;
    TouchImageView photoZoomView;
    HorizontalScrollView photoBrowser;
    LinearLayout photoWrap;
    public ImageViewerDialog(Context context, String path, String fileName) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_image_viewer);
        this.path = path;
        this.fileName = fileName;
        photoBrowser = ControlPraser.PraserControl(this, R.id.photoBrowser);
        photoZoomView = ControlPraser.PraserControl(this, R.id.photoZoomView);
        photoWrap = ControlPraser.PraserControl(this, R.id.photoWrap);
        File[] files = new File(path).listFiles();
        if(null == files || 0 == files.length) {
            Toast.makeText(context, "Not photo found!", Toast.LENGTH_LONG);
            return;
        }

        for(File file : files){
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType. FIT_XY);
            imageView.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(98,98);
            layout.setMargins(1, 1, 1, 1);  //设置间距
            imageView.setLayoutParams(layout);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photoZoomView.setImageBitmap(((BitmapDrawable)((ImageView)v).getDrawable()).getBitmap());
                }
            });
            photoWrap.addView(imageView);
            if(file.getName().equals(fileName))
                photoZoomView.setImageBitmap(((BitmapDrawable)imageView.getDrawable()).getBitmap());
        }
    }
}
