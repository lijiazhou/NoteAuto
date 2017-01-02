package com.jiazhou.homeauto.homeauto.control.panel;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaRecorder;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiazhou.homeauto.homeauto.dialog.CalculatorDialog;
import com.jiazhou.homeauto.homeauto.dialog.CameraDialog;
import com.jiazhou.homeauto.homeauto.R;
import com.jiazhou.homeauto.homeauto.control.features.ColorPicker;
import com.jiazhou.homeauto.homeauto.dataSet.DataStruct;
import com.jiazhou.homeauto.homeauto.implementation.menuImp.MenuImpBase;
import com.jiazhou.homeauto.homeauto.implementation.menuImp.PicDrawerMenuImp;
import com.jiazhou.homeauto.homeauto.interfaces.PanelBase;
import com.jiazhou.homeauto.homeauto.interfaces.listener.OnColorPickListener;
import com.jiazhou.homeauto.homeauto.utility.ControlPraser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;



/**
 * Created by lijiazhou on 31/10/16.
 */
public class PicPainter extends LinearLayout implements PanelBase {

    ImageView picDrawer;
    private Bitmap baseBitMap;
    private Canvas canvas;
    private Paint paint;
    private ColorPicker colorPicker;
    private ArrayList<Bitmap> baseBitMaps;
    private PicDrawerMenuImp picDrawerMenuImp;
    int currentIndex;
    String parentName;
    Context context;
    MediaRecorder mediaRecorder;
    FloatingActionButton fab;

    public  PicPainter(Context context, String parentName){
        this(context, ControlPraser.GetAttr(context, R.layout.control_pic_painter), parentName);
    }

    public PicPainter(Context context, AttributeSet attrs, final String parentName) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.control_pic_painter, this, true);
        this.context = context;
        this.parentName = parentName;
        picDrawer = ControlPraser.PraserControl(this, R.id.picDrawer);
        colorPicker = ControlPraser.PraserControl(this, R.id.picColorPicker);
        fab = ControlPraser.PraserControl((Activity)getContext(), R.id.fab);
        baseBitMaps = new ArrayList<>();
        currentIndex = 0;

        LayoutParams LLParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        setLayoutParams(LLParams);

        ViewTreeObserver vto = picDrawer.getViewTreeObserver();
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                picDrawer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                setCanvasBitmap();
            }

            private void setCanvasBitmap() {
                loadImageFiles(parentName, DataStruct.HANDWRITING_DOC);
                canvas = new Canvas();
                canvas.drawColor(Color.WHITE);
                paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(2);
                canvas.setBitmap(baseBitMap);
                canvas.drawBitmap(baseBitMap, new Matrix(), paint);
                picDrawer.setImageBitmap(baseBitMap);
                colorPicker.setOnColorPickListener(new OnColorPickListener() {
                    @Override
                    public void OnColorPick(int color, View view) {
                        paint.setColor(color);
                    }
                });


                picDrawer.setOnTouchListener(new OnTouchListener() {
                    int startX;
                    int startY;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                startX = (int) event.getX();
                                startY = (int) event.getY();
                                int width = fab.getWidth();
                                int height = fab.getHeight();
                                int _startX = startX + ControlPraser.dip2px(getContext(), 250);
                                int _startY = startY + ControlPraser.dip2px(getContext(), colorPicker.getHeight());
                                int x = (int)fab.getX();
                                int y = (int)fab.getY();
                                if(_startX > x && _startX < (x + width) && _startY > y && _startY < (y + height) ) {
                                    fab.callOnClick();
                                }
                                break;
                            case MotionEvent.ACTION_MOVE:
                                int stopX = (int) event.getX();
                                int stopY = (int) event.getY();
                                canvas.drawLine(startX, startY, stopX, stopY, paint);
                                startX = (int) event.getX();
                                startY = (int) event.getY();
                                picDrawer.setImageBitmap(baseBitMap);
                                break;
                        }
                        return true;
                    }
                });
            }
        });
    }

    private void addNewBitmap(){
        baseBitMap = Bitmap.createBitmap(picDrawer.getWidth(), picDrawer.getHeight(), Bitmap.Config.ARGB_8888);
        baseBitMaps.add(baseBitMap);
        currentIndex = baseBitMaps.size() - 1;
        canvas.setBitmap(baseBitMap);
        picDrawer.setImageBitmap(baseBitMap);
    }

    private void goToNextPage(boolean next){
        if (next) {
            if ((currentIndex + 1) == baseBitMaps.size())
                return;
            currentIndex++;
        }
        else {
            if((currentIndex - 1) < 0)
                return;
            currentIndex--;
        }
        baseBitMap = baseBitMaps.get(currentIndex);
        canvas.setBitmap(baseBitMaps.get(currentIndex));
        picDrawer.setImageBitmap(baseBitMap);
    }

    private void deletePage(){
        if (baseBitMaps.size() == 1){
            baseBitMap.eraseColor(Color.TRANSPARENT);
            picDrawer.setImageBitmap(baseBitMap);
            return;
        }

        String path = getContext().getFilesDir().getPath() + "/"+ DataStruct.OFFICEAUTODATAFILES + "/" + parentName + "/" + DataStruct.HANDWRITING_DOC + "/" + currentIndex + ".jpg";

        if ((currentIndex + 1) == baseBitMaps.size()){

            baseBitMaps.remove(currentIndex);
            goToNextPage(false);
        }
        else {
            baseBitMaps.remove(currentIndex);
            goToNextPage(true);
        }

        new File(path).delete();
    }

    public void save() {
        try {
            for(int i = 0; i < baseBitMaps.size(); i++) {
                File file = new File( new File(getContext().getFilesDir().getPath() + "/"+ DataStruct.OFFICEAUTODATAFILES + "/" + parentName + "/" + DataStruct.HANDWRITING_DOC + "/") ,
                        i + ".jpg");
                OutputStream stream = new FileOutputStream(file);
                baseBitMaps.get(i).compress(Bitmap.CompressFormat.PNG, 100, stream);
                stream.flush();
                stream.close();
                // 模拟一个广播，通知系统sdcard被挂载
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
//                intent.setData(Uri.fromFile(Environment
//                        .getExternalStorageDirectory()));
//                getContext().sendBroadcast(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImageFiles(String parentName, String itemName){
        String path = getContext().getFilesDir().getPath() + "/" + DataStruct.OFFICEAUTODATAFILES + "/" + parentName + "/" + itemName;
        File file = new File(path);
        File[] files = file.listFiles();
        if(files.length == 0){
            baseBitMap = Bitmap.createBitmap(picDrawer.getWidth(), picDrawer.getHeight(), Bitmap.Config.ARGB_8888);
            currentIndex = 0;
            baseBitMaps.add(baseBitMap);
            return;
        }
        for(int i = 0; i < files.length; i++){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inMutable = true;
            Bitmap bitmap = BitmapFactory.decodeFile(files[i].getPath(), options);
            baseBitMaps.add(bitmap);
        }
        baseBitMap = baseBitMaps.get(0);
        currentIndex = 0;
    }

    private void showCalculator(){
        CalculatorDialog calculatorDialog = new CalculatorDialog(context);
        calculatorDialog.show();
    }

    @Override
    public MenuImpBase getMenu() {
        return picDrawerMenuImp;
    }

    @Override
    public void setMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.pic_drawer_menu, menu);
        picDrawerMenuImp = new PicDrawerMenuImp(getContext()) {
            @Override
            protected boolean implement(int menuId) {
                switch (menuId) {
                    case R.id.menuCam:
                        turnOnCam();
                        break;
                    case R.id.menuSearch:
                        break;
                    case R.id.menuLast:
                        goToNextPage(false);
                        break;
                    case R.id.menuNext:
                        goToNextPage(true);
                        break;
                    case R.id.menuAdd:
                        addNewBitmap();
                        break;
                    case R.id.menuCalculator:
                        showCalculator();
                        break;
                    case R.id.menuRecord:
                            startRecording();
                        break;
                    case R.id.menuSave:
                        save();
                        break;
                    case R.id.menuDelete:
                        deletePage();
                        break;
                    case R.id.menuDots:
                        break;
                    default:
                        return false;
                }
                return true;
            }
        };
    }

    private void startRecording(){
        if(null != mediaRecorder)
            return;

        String path = getContext().getFilesDir().getPath() + "/"+ DataStruct.OFFICEAUTODATAFILES + "/" + parentName + "/" + DataStruct.VOICE_RECORDS;
        File file = new File(path);
        Integer number = file.list().length;
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(path + "/" + number.toString());
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e("Recording", "prepare() failed");
        }

        mediaRecorder.start();
        fab.setVisibility(View.VISIBLE);
    }

    private void stopRecording() {
        if(null == mediaRecorder)
            return;
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        fab.setVisibility(View.INVISIBLE);
    }

    private void turnOnCam(){
        if(!checkCameraHardware(getContext())){
            Toast.makeText(getContext(), "This device has not equipped with a camera!", Toast.LENGTH_LONG);
            return;
        }
        new CameraDialog(getContext()).show();
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
