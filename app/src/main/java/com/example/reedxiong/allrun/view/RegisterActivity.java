package com.example.reedxiong.allrun.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.reedxiong.allrun.R;
import com.example.reedxiong.allrun.biz.RegisterBiz;
import com.example.reedxiong.allrun.entity.UserEntity;
import com.example.reedxiong.allrun.util.Const;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText uesrname;
    private EditText passwd;
    private EditText confim_pwd;
    private EditText nickname;
    private Button Btn_register;
    private Register_Receiver register_receiver;
    private ImageView image_icon;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        setupView();

       register_receiver=new Register_Receiver();
        registerReceiver(register_receiver,new IntentFilter(Const.ACTION_REGISTER));

    }

    private void setupView() {
        uesrname=(EditText)findViewById(R.id.et_register_username);
        passwd=(EditText)findViewById(R.id.et_register_password);
        confim_pwd=(EditText)findViewById(R.id.et_register_confirm_password);
        nickname=(EditText)findViewById(R.id.et_register_name);
        Btn_register=(Button)findViewById(R.id.btn_register_submit);
        Btn_register.setOnClickListener(this);
        image_icon=(ImageView)findViewById(R.id.image_icon);
    }
    public void selectIcon(View view){
        // open the allery to choose the picture
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 200) {
                // resultCode used to lable the activity where data is from
                bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), data.getData());
                image_icon.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register_submit:
                String username=uesrname.getText().toString();
                String pwd=passwd.getText().toString();
                String name=nickname.getText().toString();
                Intent intent=new Intent(this,RegisterBiz.class);// must be fix
                // take a user data
                UserEntity userEntity=new UserEntity();
                userEntity.setUsername(username);
                userEntity.setPassword(pwd);
                userEntity.setUser(name);
                intent.putExtra(Const.KEY_DATA,userEntity);
                //because the putExtra cannot input BitMap object
                // do you can transfer to byte to send
                ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                byte[] array=outputStream.toByteArray();
                intent.putExtra(Const.IMAGE_DATA,array);
                // start intent
                startService(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(register_receiver);
        // there should be entity ,dont a class object
    }

    class Register_Receiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
                if(intent!=null){
                    int status=intent.getIntExtra(Const.KEY_DATA,-1);
                    if(status==Const.STATUS_OK){
                        Toast.makeText(RegisterActivity.this,"OK",Toast.LENGTH_SHORT).show();
                    }
                    else if(status==Const.STATUS_REGISTER_FAILURE){
                        Toast.makeText(RegisterActivity.this,"Falie",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(RegisterActivity.this,"Exception",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
