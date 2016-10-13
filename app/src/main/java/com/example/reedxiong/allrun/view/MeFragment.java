package com.example.reedxiong.allrun.view;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.reedxiong.allrun.R;
import com.example.reedxiong.allrun.biz.UpdateBiz;
import com.example.reedxiong.allrun.entity.VersionEntity;
import com.example.reedxiong.allrun.util.Const;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    public static final int SHOW_VERSION_INFO = 1;
    public static final int INSTALL = 2;
    public static final int ERROR = 3;

    private View view;
    private Button upgrade;
    private UpdateBiz updateBiz;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case SHOW_VERSION_INFO:
                   Bundle bundle=msg.getData();
                   VersionEntity versionEntity=(VersionEntity)bundle.get(Const.KEY_DATA);
                   Log.i("升级", versionEntity.getApkUrl());
                   //
                    showUpgrade(versionEntity);
                   break;
               case INSTALL:
                    Bundle bundle1=msg.getData();
                   String targetpath=bundle1.getString(Const.KEY_DATA);
                   //goto this path
                   File file=new File(targetpath);
                   Uri uri=Uri.fromFile(file);

                   Intent intent = new Intent(Intent.ACTION_VIEW);
                   //type是mime
                   String type="application/vnd.android.package-archive";
                   intent.setDataAndType(uri, type);
                   getActivity().startActivity(intent);
                   //

                   break;
           }
        }
    };

    private void showUpgrade(final VersionEntity versionEntity) {
        int versionNumber=versionEntity.getVersion();
        if(versionNumber>7){
            // show this is new version
            final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setMessage(versionEntity.getVersion() + "\n"
                    + versionEntity.getChangeLog());
            builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // get the apk file from tomcat
                    // 1. this is correct the biz module
                    //2.so you need to call the biz class
                    //3. back to a URL
                    updateBiz.getApp(handler, versionEntity.getApkUrl());

                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    builder.setCancelable(true);
                    dialogInterface.cancel();

                }
            });
            builder.show();
        }
        else {
            Toast.makeText(getActivity(), "这是最新的的版本", Toast.LENGTH_SHORT).show();
        }
    }

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try{
            view= inflater.inflate(R.layout.fragment_me, container, false);
            setupView();
        }
        catch (Exception e){
            e.printStackTrace();
        }return view;
    }

    private void setupView() {
        //find the button
        upgrade=(Button)view.findViewById(R.id.btn_me_update);
        upgrade.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            //get the version inforation
        switch (view.getId()){
            case R.id.btn_me_update:
                updateBiz=new UpdateBiz();
                updateBiz.queryVersion(handler);
                    break;

        }
    }
}
