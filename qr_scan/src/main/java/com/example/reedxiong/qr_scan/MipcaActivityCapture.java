package com.example.reedxiong.qr_scan;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.reedxiong.qr_scan.view.CameraManager;
import com.example.reedxiong.qr_scan.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * Created by Reed.Xiong on 2016/10/13.
 */
public class MipcaActivityCapture extends Activity implements SurfaceHolder.Callback {
    private SurfaceView surfaceView;
    private ViewfinderView findView;
    private SurfaceHolder mHolder;
    private Camera camera;
    private CameraConfigurationManager configManager;
    private CaptureActivityHandler handler;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.content_capture);
        CameraManager.init(getApplication());
        // get preview
        surfaceView=(SurfaceView)findViewById(R.id.preview_view);

        mHolder=surfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        findView=(ViewfinderView)findViewById(R.id.viewfinder_view);
        // get define view for scan
        decodeFormats = null;
        characterSet = null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(camera==null){
            camera=Camera.open();
        }
        try {
            camera.setPreviewDisplay(mHolder);
            camera.startPreview();
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        characterSet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        Camera.Parameters params = camera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width, selected.height);
        camera.setParameters(params);
        camera.startPreview();
        //configManager=new CameraConfigurationManager(getApplication());
        //configManager.initFromCameraParameters(camera);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.release();
        camera=null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        camera.stopPreview();
    }
    public ViewfinderView getViewfinderView() {
        return findView;
    }
    public Handler getHandler(){
        return handler;
    }
}
