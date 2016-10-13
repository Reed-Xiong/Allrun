package com.example.reedxiong.allrun.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reedxiong.allrun.R;
import com.example.reedxiong.allrun.biz.LoginBiz;
import com.example.reedxiong.allrun.entity.UserEntity;
import com.example.reedxiong.allrun.util.Const;
import com.example.reedxiong.allrun.util.Tools;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private Button btn_Login;
    private Button btn_register;
    private LoginReceiver loginReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginReceiver=new LoginReceiver();
        registerReceiver(loginReceiver,new IntentFilter(Const.ACTION_LOGIN));
        setup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(loginReceiver);
    }

    private void setup() {
        username=(EditText)findViewById(R.id.et_login_username);
        password=(EditText)findViewById(R.id.et_login_password);
        btn_Login=(Button)findViewById(R.id.btn_login_submit);
        btn_register=(Button)findViewById(R.id.btn_register_submit);
        btn_Login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login_register :
                Intent intent =new Intent(this,RegisterActivity.class);
                startActivity(intent);
            case R.id.btn_login_submit :
                Tools.startDialog(this, "正在登录...");
                UserEntity userEntity=new UserEntity();
                userEntity.setUsername(username.getText().toString());
                userEntity.setPassword(password.getText().toString());
                LoginBiz.onLogin(userEntity);
                // code the logbiz
                // new loginBiz
        }
    }

       class LoginReceiver extends BroadcastReceiver{

           @Override
           public void onReceive(Context context, Intent intent) {
               if(intent==null){
                   int status=intent.getIntExtra(Const.KEY_DATA,-1);
                   if(status==Const.STATUS_OK){
                       //login successfully ,you need next step
                       Intent intent1=new Intent(LoginActivity.this,MainFragmentActivity.class);
                       startActivity(intent);
                   }
                   if(status==Const.STATUS_REGISTER_FAILURE){
                       //
                       Toast.makeText(LoginActivity.this,"ON Fail",Toast.LENGTH_LONG);
                   }
               }
           }
       }


}
