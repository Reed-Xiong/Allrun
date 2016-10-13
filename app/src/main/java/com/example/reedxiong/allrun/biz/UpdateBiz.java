package com.example.reedxiong.allrun.biz;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.reedxiong.allrun.Paser.UpdateParser;
import com.example.reedxiong.allrun.entity.VersionEntity;
import com.example.reedxiong.allrun.util.Const;
import com.example.reedxiong.allrun.view.MeFragment;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.io.File;
import java.util.UUID;

/**
 * Created by Reed.Xiong on 2016/7/19.
 */
public class UpdateBiz {
    private VersionEntity versionInfo=null;

    public void queryVersion(final Handler handler){
        new Thread(){
            @Override
            public void run() {
                //1.connect the tomcat
                //2. provider connect the params
                //3 back the json
                // 4 .handler.sendMsseage()
                // fixed the url is not benfit
                String url="http://124.207.192.18:8080/allRunServer/apkUpdate.jsp?username=Reed";
                //new requsest params
                RequestParams params=new RequestParams();

                HttpUtils httpUtils=new HttpUtils(40000);
                httpUtils.send(HttpRequest.HttpMethod.GET, url,
                        new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                try {
                                    String updateInfo=responseInfo.result;
                                    JSONObject jsonObject=new JSONObject(updateInfo);
                                    int status=jsonObject.getInt("status");
                                    if(status==0){
                                        versionInfo=UpdateParser.parser(updateInfo);

                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                finally {
                                    Message msg=handler.obtainMessage();
                                   // Message msg=new Message();
                                    msg.what= MeFragment.SHOW_VERSION_INFO;
                                    Bundle bundle=new Bundle();
                                    bundle.putSerializable(Const.KEY_DATA,versionInfo);
                                    msg.setData(bundle);
                                    handler.sendMessage(msg);
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {

                            }
                        });
            }
        }.start();
    }
    public void getApp(final Handler handler, final String ApkUrl){
        new Thread(){
            @Override
            public void run() {
                //download the apk
                // 1.access the tomcat
                //2.download apk
                String fileName=ApkUrl.substring(ApkUrl.lastIndexOf("/")+1);
                //String url=ApkUrl;
                String url="http://p.gdown.baidu.com/e760af129c2662122510ac18581cbda2946e9a2abb02a6cd141cf0cf4368df618b1cfbca469ef464e1fcdcc63d279f298f4fb34926524dfa25baf4d70ddd49e61a077c7e453e6ed6b44d2aa8f0b055184bbc032c23218d2795410b42cc5b3aeceb3602a1998c7af0";
                final String targetUrl=Const.APK_PATH+"/"+ UUID.randomUUID();
                HttpUtils httpUtils=new HttpUtils(40000);
                httpUtils.download(ApkUrl, targetUrl, new RequestCallBack<File>() {
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        //if the success ,the file would be saved the target path
                        if(responseInfo!=null){
                            Message message=handler.obtainMessage();
                            message.what=MeFragment.INSTALL;
                            Bundle bundle=new Bundle();
                            bundle.putString(Const.KEY_DATA,targetUrl);
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                });
            }
        }.start();
    }
}
