package com.jiazhou.auto.note.control.features;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;

/**
 * Created by lijiazhou on 14/1/17.
 */

public class ZoomView extends View{
    private Drawable image;
    ImageButton img,img1;
    private int zoomControler=20;

    public ZoomView(Context context){
        super(context);

        //image=context.getResources().getDrawable(R.drawable.j);
        //image=context.getResources().getDrawable(R.drawable.icon);
        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //here u can control the width and height of the images........ this line is very important
        image.setBounds((getWidth()/2)-zoomControler, (getHeight()/2)-zoomControler, (getWidth()/2)+zoomControler, (getHeight()/2)+zoomControler);
        image.draw(canvas);
    }


    public void setImage(File file){

        image = Drawable.createFromPath(file.getPath());
        invalidate();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_DPAD_UP){
            // zoom in
            zoomControler+=10;
        }
        if(keyCode==KeyEvent.KEYCODE_DPAD_DOWN){
            // zoom out
            zoomControler-=10;
        }
        if(zoomControler<10){
            zoomControler=10;
        }

        invalidate();
        return true;
    }
}
