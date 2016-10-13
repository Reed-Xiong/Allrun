package com.example.reedxiong.allrun.biz;

import android.app.IntentService;
import android.content.Intent;

import com.example.reedxiong.allrun.TApplication;
import com.example.reedxiong.allrun.util.Const;

import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * Created by Reed.Xiong on 2016/7/21.
 */
public class ClubBiz extends IntentService {
    public ClubBiz() {
        super("ClubBiz");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // work on work Thread....
        //1.connect the openfire server
        //2.new the chatgroup
        int sttus=0;
        String roonName=intent.getStringExtra("roomName");
        String name=intent.getStringExtra("nickName");
        try {
            String room=roonName+"@conference."+ TApplication.domain;
            MultiUserChat multiUserChat=new MultiUserChat(TApplication.xmppConnection, room);
            multiUserChat.join(name);
            //在别的地方会用到，作成全局变量
            TApplication.multiUserChat=multiUserChat;
            //send notify


        } catch (Exception e) {
            sttus=100;
            e.printStackTrace();
        }
        finally {
            Intent intent1=new Intent(Const.ACTION_CLUB);
            intent.putExtra(Const.KEY_DATA,sttus);
            sendBroadcast(intent1);
        }
    }
}
