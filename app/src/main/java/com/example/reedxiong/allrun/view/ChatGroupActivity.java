package com.example.reedxiong.allrun.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.reedxiong.allrun.R;
import com.example.reedxiong.allrun.TApplication;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class ChatGroupActivity extends Activity implements View.OnClickListener {

    private EditText chat_body;
    private Button btn_send;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_group);
        setupView();
    }

    private void setupView() {
        //
        chat_body=(EditText)findViewById(R.id.et_chat_body);
        btn_send=(Button)findViewById(R.id.btn_chat_send);
        btn_send.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        menu.add("roomname").setIcon(R.drawable.ic_launcher);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_chat_send :
                // 1. send message to openfire
                //2. save the Msg
                sendMessage(chat_body.getText().toString());
                break;
        }
    }

    private void sendMessage(final String msgbody) {
        new Thread(){
            @Override
            public void run() {

                Message message=new Message("Reed",Message.Type.groupchat);
                String room= TApplication.multiUserChat.getRoom();
                message.setTo(room);
                message.setBody(msgbody);
                try {
                    TApplication.multiUserChat.sendMessage(message);
                    // send a state to activity

                } catch (XMPPException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
