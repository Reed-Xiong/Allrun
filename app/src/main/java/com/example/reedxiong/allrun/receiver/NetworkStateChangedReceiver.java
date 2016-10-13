package com.example.reedxiong.allrun.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Reed.Xiong on 2016/7/18.
 */
public class NetworkStateChangedReceiver extends BroadcastReceiver {
    private ConnectivityManager conManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        //接受网络状态，并以响应状态告诉运用程序
        conManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //Network activeNetWork = conManager.getActiveNetwork();

        NetworkInfo activeNetworkInfo = conManager.getActiveNetworkInfo();
        if(activeNetworkInfo==null){

        }else {
            NetworkInfo wifi = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(wifi!=null&& wifi.isConnected()){
                //
            }
            NetworkInfo gsm4G = conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(gsm4G!=null&& gsm4G.isConnected()){
                //
            }
        }

    }
}
