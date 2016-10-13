package com.example.reedxiong.allrun.biz;

import android.content.Intent;

import com.example.reedxiong.allrun.TApplication;
import com.example.reedxiong.allrun.entity.UserEntity;
import com.example.reedxiong.allrun.util.Const;

import org.jivesoftware.smack.XMPPException;

/**
 * Created by Reed.Xiong on 2016/7/15.
 */
public class LoginBiz {
    private  static int status;
    public static void onLogin(final UserEntity userEntity){
        new Thread("LoginBiz"){
            @Override
            public void run() {
                // get the entity details
                if(TApplication.xmppConnection.isConnected()){
                    try {
                        // for to do login and confirm the user
                        TApplication.xmppConnection.login(userEntity.getUsername(),userEntity.getPassword());
                        boolean flag=TApplication.xmppConnection.isAuthenticated();
                        if(flag){
                            status= Const.STATUS_OK;
                            //and then go into the main page for the application
                        }
                        else
                            status=Const.STATUS_REGISTER_FAILURE;
                    } catch (XMPPException e) {
                        e.printStackTrace();
                        status=Const.STATUS_REGISTER_FAILURE;
                    }
                    finally {
                        Intent intent1=new Intent(Const.ACTION_LOGIN);
                        intent1.putExtra(Const.KEY_DATA,status);
                        TApplication.instance.sendBroadcast(intent1);
                    }
                }
            }
        }.start();
    }
}
