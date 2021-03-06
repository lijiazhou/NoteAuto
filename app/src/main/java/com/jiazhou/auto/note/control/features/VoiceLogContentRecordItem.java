package com.jiazhou.auto.note.control.features;

import android.content.Context;

import com.jiazhou.auto.note.dialog.MusicPlayerDialog;

import java.io.File;

/**
 * Created by lijiazhou on 15/1/17.
 */

public class VoiceLogContentRecordItem extends ContentRecordItem {

    public VoiceLogContentRecordItem(Context context, File file) {
        super(context, file);
    }

    @Override
    public void onContentClick() {
        new MusicPlayerDialog(context, file.getPath()).show();
    }
}
