package com.example.reedxiong.qr_scan;

import android.os.Handler;
import android.os.Message;

import com.google.zxing.BarcodeFormat;

import java.util.Vector;

/**
 * Created by Reed.Xiong on 2016/10/13.
 */
public class CaptureActivityHandler extends Handler {
    private MipcaActivityCapture activity;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    public CaptureActivityHandler(MipcaActivityCapture activityCapture,
                                  Vector<BarcodeFormat> decodeFormats,
                                  String characterSet
    ){
        this.activity=activityCapture;

    }

    @Override
    public void handleMessage(Message msg) {
        switch(msg.what){

        }
    }
}
