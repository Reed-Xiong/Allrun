package com.example.reedxiong.allrun.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

import com.example.reedxiong.allrun.R;
import com.example.reedxiong.allrun.adapter.NearbyUserAdapter;
import com.example.reedxiong.allrun.entity.UserEntity;
import com.example.reedxiong.allrun.util.Const;

import java.util.ArrayList;

public class NearbyUserActivity extends Activity {
    ArrayList<UserEntity> list=null;
    private NearbyUserReceiver nearbyUserReceiver;
    private ListView UserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_user);
        nearbyUserReceiver=new NearbyUserReceiver();
        registerReceiver(nearbyUserReceiver,new IntentFilter(Const.ACTION_NEARBYUSER));
        setupView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        unregisterReceiver(nearbyUserReceiver);
    }

    private void setupView() {
        int i=0;
        UserList=(ListView)findViewById(R.id.lv_nearby_user);
        // 1.get broadcast
        //2. get userentity list
        while (list==null&&i<10){
            try {
               i++;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        NearbyUserAdapter nearbyUserAdapter=new NearbyUserAdapter(this,list);
        UserList.setAdapter(nearbyUserAdapter);

    }
    class NearbyUserReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
           list= (ArrayList<UserEntity>)intent.getSerializableExtra(Const.KEY_DATA);
        }
    }
}
