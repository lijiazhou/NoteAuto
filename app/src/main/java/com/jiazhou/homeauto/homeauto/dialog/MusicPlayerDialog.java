package com.jiazhou.homeauto.homeauto.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.homeauto.homeauto.utility.ControlPraser;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lijiazhou on 10/12/16.
 */
public class MusicPlayerDialog extends Dialog {

    Button musicPlay;
    Button musicStop;
    String musicFile;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    Timer mTimer;
    TimerTask mTimerTask;
    boolean isChanging=false;
    View exit;

    public MusicPlayerDialog(Context context, final String musicFile) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_record_player);
        setCancelable(false);
        this.musicFile = musicFile;
        mediaPlayer = new MediaPlayer();
        musicStop = ControlPraser.PraserControl(this, R.id.buttonPauseStop);
        musicPlay = ControlPraser.PraserControl(this, R.id.buttonPlayRecord);
        seekBar = ControlPraser.PraserControl(this, R.id.seekBar);
        exit = ControlPraser.PraserControl(this, R.id.playerExit);
        musicStop.setEnabled(false);

        try {
            mediaPlayer.setDataSource(musicFile);
            mediaPlayer.prepare();
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "An error occured during the initialization of the media player!", Toast.LENGTH_LONG);
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        musicPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicStop.setEnabled(true);
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    musicPlay.setBackgroundResource(R.drawable.play);
                    return;
                }
                mediaPlayer.start();
                musicPlay.setBackgroundResource(R.drawable.pause);
                mTimer = new Timer();
                mTimerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if(isChanging==true) {
                            return;
                        }
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                };
                mTimer.schedule(mTimerTask, 0, 10);
            }
        });

        musicStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                //mediaPlayer.release();
                musicStop.setEnabled(false);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                mTimer.cancel();
                mTimerTask.cancel();
                mTimer = null;
                mTimerTask = null;
            }
        });

        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mediaPlayer.release();
                mTimer.cancel();
                mTimerTask.cancel();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isChanging=true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                isChanging=false;
            }
        });

    }

}
