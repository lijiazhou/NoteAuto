package com.jiazhou.homeauto.homeauto.dialog;

import android.app.Dialog;
import android.content.Context;
import android.hardware.Camera;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.homeauto.homeauto.control.features.CameraPreview;
import com.jiazhou.homeauto.homeauto.utility.ControlPraser;

/**
 * Created by lijiazhou on 24/11/16.
 */
public class CameraDialog extends Dialog {

    private Camera camera;
    private CameraPreview preview;
    Button cap;
    Button cancel;
    FrameLayout frameLayout;

    private Camera.PictureCallback picture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };

    public CameraDialog(Context context) {
        super(context, R.style.Dialog);
        this.setContentView(R.layout.dialog_cam_preview);
        setCancelable(false);
        //getActionBar().hide();
        cap = ControlPraser.PraserControl(this, R.id.capture);
        cancel = ControlPraser.PraserControl(this, R.id.capCancel);
        frameLayout = ControlPraser.PraserControl(this, R.id.cameraPreview);
        camera = ControlPraser.getCameraInstance();
        preview = new CameraPreview(context, camera);
        frameLayout.addView(preview);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, picture);
            }
        });
    }
}
