package com.example.reedxiong.qr_scan;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    private SurfaceHolder mHolder;
    private Camera mCarema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCarema.stopPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCarema.release();
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mCarema.setPreviewDisplay(mHolder);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // get the size for preview
        Camera.Parameters paramer = mCarema.getParameters();
        List<Camera.Size> size = paramer.getSupportedPreviewSizes();
        Camera.Size mySize = size.get(0);
        paramer.setPreviewSize(mySize.width,mySize.height);
        mCarema.setParameters(paramer);
        mCarema.startPreview();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
