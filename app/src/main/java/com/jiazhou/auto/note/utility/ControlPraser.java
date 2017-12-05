package com.jiazhou.auto.note.utility;

/**
 * Created by lijiazhou on 18/10/16.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by lijiazhou on 31/01/2016.
 */
public class ControlPraser {

    public static <T> T PraserControl(View context, int id)
    {
        return (T)(context.findViewById(id));
    }

    public static <T> T PraserControl(Activity context, int id)
    {
        return (T)(context.findViewById(id));
    }

    public static <T> T PraserControl(Dialog context, int id)
    {
        return (T)(context.findViewById(id));
    }

    public static AttributeSet GetAttr(Context context, int id)
    {
        return Xml.asAttributeSet(context.getResources().getLayout(id));
    }

    public static <T> T GetAttributeByName(Object parent, String name) throws NoSuchFieldException, IllegalAccessException
    {
        Field field = parent.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (T)(field.get(parent));
    }

    public static Activity GetActivity(Context context)
    {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}

