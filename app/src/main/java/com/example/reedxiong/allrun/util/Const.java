package com.example.reedxiong.allrun.util;

import android.os.Environment;

/**
 * Created by Reed.Xiong on 2016/7/13.
 */
public class Const {
    public static final String KEY_DATA="data";

    public static final String ACTION_REGISTER="com.tarena.allrun.register";
    public static final String ACTION_LOGIN="com.tarena.allrun.on_Login";
    public static final String ACTION_CLUB="com.tarena.allrun.on_Club";
    public static final String ACTION_NEARBYUSER="com.tarena.allrun.on_NearbyUser";


    public static final int STATUS_OK=0;
    public static final int STATUS_REGISTER_FAILURE=100;
    public static final String IMAGE_DATA="image_icon";
    //
    public static final String SDCARD_ROOT= Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String  APK_PATH=SDCARD_ROOT+"/allRun/apk";
    public static final String  AUDIO_PATH=SDCARD_ROOT+"/allRun/audio";
}
