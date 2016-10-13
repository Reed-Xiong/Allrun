package com.example.reedxiong.allrun.biz;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.reedxiong.allrun.TApplication;
import com.example.reedxiong.allrun.entity.UserEntity;
import com.example.reedxiong.allrun.util.Const;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.jivesoftware.smack.AccountManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Reed.Xiong on 2016/7/14.
 */
public class RegisterBiz extends IntentService {
    public RegisterBiz() {
        super("RegisterBiz");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // data from  the register activtiy
        // to perform the register
        //1. first ,parser data
        int  status=Const.STATUS_OK;
        try {
            UserEntity userEntity=(UserEntity)intent.getSerializableExtra(Const.KEY_DATA);
            String user=userEntity.getUsername();
            String password=userEntity.getPassword();
            HashMap<String ,String> map=new HashMap<>();
            map.put("name", userEntity.getName());
            //2.use the data to finish the register
            AccountManager accountManager= TApplication.xmppConnection.getAccountManager();
            accountManager.createAccount(user,password,map);
            //3. register to another server : Tomcat
            // (1),提交给谁
            String url = "http://" + TApplication.host
                    + ":8080/allRunServer/register.jsp";
            // (2)发什么数据
            // httpClient,asyncHttpClient,java.net.HttpUrlConnection
            HttpUtils httpUtils = new HttpUtils(60000);
            RequestParams requestEntity = new RequestParams();
            requestEntity
                    .addBodyParameter("username", userEntity.getUsername());
            String pwd = userEntity.getPassword();
            //pwd = MD5Util.getStringMD5(pwd);
            requestEntity.addBodyParameter("md5password", pwd);
            requestEntity.addBodyParameter("nickname", userEntity.getName());
            //get image
            byte[] imagedata=intent.getByteArrayExtra(Const.IMAGE_DATA);
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(imagedata);
            String filename= UUID.randomUUID().toString()+".png";
            requestEntity.addBodyParameter("file", byteArrayInputStream,
                    byteArrayInputStream.available(), filename, "image/png");
            try{
                httpUtils.send(HttpRequest.HttpMethod.POST, url, requestEntity,
                        new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Log.i("info","reply: " + responseInfo.result);
                                //back Json object
                                JSONObject jsonObject= null;
                                try {
                                    jsonObject = new JSONObject(responseInfo.result);
                                   int  status =jsonObject.getInt("status");
                                    Log.i("info","JSON status"+status);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Log.i("info",e.getExceptionCode()+":"+s);
                            }
                        });
            }
            catch (Exception e){
                status=Const.STATUS_REGISTER_FAILURE;
            }

        }
        catch (Exception e){
            e.printStackTrace();
            status=Const.STATUS_REGISTER_FAILURE;
        }
       finally {
            //3.send the result for register
            Intent intent1=new Intent(Const.ACTION_REGISTER);
            intent1.putExtra(Const.KEY_DATA,status);
            sendBroadcast(intent);
        }
    }
}
