package com.example.reedxiong.allrun.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.example.reedxiong.allrun.R;
import com.example.reedxiong.allrun.util.Const;

public class ClubActivity extends Activity {

    private EditText nickname;
    private EditText roomname;
    private ClubReceiver clubReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);
        setupView();
       clubReceiver= new ClubReceiver();
        registerReceiver(clubReceiver,new IntentFilter(Const.ACTION_CLUB));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(clubReceiver);
    }

    public void submit(View view){
        //1.connect the openfire server
        //2.new the chatgroup
        //3.if it is ok
        // goto the chat activity

    }
    private void setupView() {
        roomname=(EditText)findViewById(R.id.et_input_room_roomName);
        nickname=(EditText)findViewById(R.id.et_input_room_roomName);

    }
    class ClubReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int status=intent.getIntExtra(Const.KEY_DATA,-1);
            if(status==Const.STATUS_OK){
                // goto
            }
            else if(status==Const.STATUS_REGISTER_FAILURE){

            }
            else{
                //exception
            }
        }
    }
}
