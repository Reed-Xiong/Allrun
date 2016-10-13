package com.example.reedxiong.qr_scan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {
    private Button button;
    private TextView mTextView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        button=(Button)findViewById(R.id.button1_red);
        mTextView=(TextView)findViewById(R.id.result);
        mImageView=(ImageView)findViewById(R.id.qr_bitmap);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MipcaActivityCapture.class);
                startActivityForResult(intent,200);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            if(resultCode==RESULT_OK){
                Bundle bundle = data.getExtras();
                //显示扫描到的内容
                mTextView.setText(bundle.getString("result"));
                //显示
                mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
            }
        }
    }
}
