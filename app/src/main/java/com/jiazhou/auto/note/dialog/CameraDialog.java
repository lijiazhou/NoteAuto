package com.jiazhou.auto.note.dialog;

import android.app.Dialog;
import android.content.Context;
import android.hardware.Camera;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jiazhou.auto.note.dataSet.DataStruct;
import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.auto.note.control.features.CameraPreview;
import com.jiazhou.auto.note.utility.ControlPraser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by lijiazhou on 24/11/16.
 */
public class CameraDialog extends Dialog {

    private Camera camera;
    private CameraPreview preview;
    Button cap;
    Button cancel;
    FrameLayout frameLayout;
    ImageView picSave;
    ImageView picGiveUp;
    byte[] picData = null;
    String parentName;

    private Camera.PictureCallback picture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            picData = data;
            picSave.setVisibility(View.VISIBLE);
            picGiveUp.setVisibility(View.VISIBLE);
        }
    };

    public CameraDialog(Context context, String parentName) {
        super(context, R.style.Dialog);
        this.setContentView(R.layout.dialog_cam_preview);
        setCancelable(false);
        //getActionBar().hide();
        this.parentName = parentName;
        picSave = ControlPraser.PraserControl(this, R.id.savePic);
        picGiveUp = ControlPraser.PraserControl(this, R.id.giveUpPic);
        picSave.setVisibility(View.INVISIBLE);
        picGiveUp.setVisibility(View.INVISIBLE);
        cap = ControlPraser.PraserControl(this, R.id.capture);
        cancel = ControlPraser.PraserControl(this, R.id.capCancel);
        frameLayout = ControlPraser.PraserControl(this, R.id.cameraPreview);
        camera = ControlPraser.getCameraInstance();
        preview = new CameraPreview(context, camera);
        frameLayout.addView(preview);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.release();
                dismiss();
            }
        });
        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, picture);

            }
        });
        picSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(picData != null){
                    String path = getContext().getFilesDir().getPath() + "/"+ DataStruct.OFFICEAUTODATAFILES + "/" + CameraDialog.this.parentName + "/" + DataStruct.PHTOTS + "/";
                    path += new java.text.SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date()) + ".png";
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(new File(path));
                        fos.write(picData);
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                picSave.setVisibility(View.INVISIBLE);
                picGiveUp.setVisibility(View.INVISIBLE);
                camera.startPreview();
            }
        });
        picGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picSave.setVisibility(View.INVISIBLE);
                picGiveUp.setVisibility(View.INVISIBLE);
                camera.startPreview();
            }
        });

    }
}
