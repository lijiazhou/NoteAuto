package com.jiazhou.homeauto.homeauto.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.homeauto.homeauto.control.features.WaveformView;
import com.jiazhou.homeauto.homeauto.dataSet.SoundFile;
import com.jiazhou.homeauto.homeauto.utility.ControlPraser;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lijiazhou on 10/12/16.
 */
public class MusicPlayerDialog extends Dialog {



    WaveformView waveformView;
    SoundFile soundFile;
    ProgressDialog progressDialog;
    long loadingLastUpdateTime;
    boolean loadingKeepGoing;
    boolean finishActivity;
    public MusicPlayerDialog(Context context, final String musicFile) {
        super(context, R.style.Dialog);
        progressDialog = new ProgressDialog(context);
        waveformView = ControlPraser.PraserControl(this, R.id.waveform);

        loadingLastUpdateTime = getCurrentTime();
        //waveformView.setSoundFile();
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        loadingKeepGoing = false;
                        finishActivity = true;
                    }
                });
        progressDialog.show();
    }

    private long getCurrentTime() {
        return System.nanoTime() / 1000000;
    }

    private void initializeSoundFile(String musicFile){
        try {
            soundFile = SoundFile.create(musicFile, new SoundFile.ProgressListener() {
                @Override
                public boolean reportProgress(double fractionComplete) {
                    long now = getCurrentTime();
                    if (now - loadingLastUpdateTime > 100) {
                        progressDialog.setProgress(
                                (int) (progressDialog.getMax() * fractionComplete));
                        loadingLastUpdateTime = now;
                    }
                    return loadingKeepGoing;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SoundFile.InvalidInputException e) {
            e.printStackTrace();
        }
    }
}
