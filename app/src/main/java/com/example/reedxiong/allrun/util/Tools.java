package com.example.reedxiong.allrun.util;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by Reed.Xiong on 2016/7/13.
 */
public class Tools {
    private static ProgressDialog progressDialog;

    public static void startDialog(Activity activity,String msg){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(activity);
            progressDialog.setMessage(msg);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

    }
    public static void stopDialog(){
        if(progressDialog!=null){
            progressDialog.cancel();
            progressDialog=null;
            System.gc();
        }
    }
}
