package com.jiazhou.auto.note.control.features;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jiazhou.auto.note.interfaces.listener.OnColorPickListener;
import com.jiazhou.auto.note.utility.ControlPraser;
import com.jiazhou.homeauto.homeauto.R;

/**
 * Created by lijiazhou on 2/11/16.
 */
public class ColorPicker extends LinearLayout {

    ImageView colorPicker;
    Button pickerBlack;
    Button pickerWhite;
    Button pickerBlue;
    Button pickerGreen;
    Button pickerSalmon;
    Button pickerDarkGreen;
    Button pickerDarkBlue;
    Button pickerRed;

    OnColorPickListener onColorPickListener;

    OnClickListener pickerListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button)v;
            Drawable buttonBackground = button.getBackground();
            ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
            int colorId = buttonColor.getColor();
            colorPicker.setBackground(buttonBackground);
            if(null != onColorPickListener)
                onColorPickListener.OnColorPick(colorId, v);
        }
    };


    public ColorPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.control_color_picker, this, true);
        colorPicker = ControlPraser.PraserControl(this, R.id.colorPicker);
        pickerBlack = ControlPraser.PraserControl(this, R.id.pickerBlack);
        pickerWhite = ControlPraser.PraserControl(this, R.id.pickerWhite);
        pickerBlue = ControlPraser.PraserControl(this, R.id.pickerBlue);
        pickerGreen = ControlPraser.PraserControl(this, R.id.pickerGreen);
        pickerSalmon = ControlPraser.PraserControl(this, R.id.pickerSalmon);
        pickerDarkGreen = ControlPraser.PraserControl(this, R.id.pickerDarkGreen);
        pickerDarkBlue = ControlPraser.PraserControl(this, R.id.pickerDarkBlue);
        pickerRed = ControlPraser.PraserControl(this, R.id.pickerRed);

        pickerBlue.setOnClickListener(pickerListener);
        pickerRed.setOnClickListener(pickerListener);
        pickerBlack.setOnClickListener(pickerListener);
        pickerWhite.setOnClickListener(pickerListener);
        pickerSalmon.setOnClickListener(pickerListener);
        pickerDarkGreen.setOnClickListener(pickerListener);
        pickerDarkBlue.setOnClickListener(pickerListener);
        pickerGreen.setOnClickListener(pickerListener);
    }

    public void setOnColorPickListener(OnColorPickListener onColorPickListener){
        this.onColorPickListener = onColorPickListener;
    }
}
