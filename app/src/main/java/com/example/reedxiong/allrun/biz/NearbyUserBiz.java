package com.example.reedxiong.allrun.biz;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.reedxiong.allrun.Paser.JsonConst;
import com.example.reedxiong.allrun.Paser.JsonParser;
import com.example.reedxiong.allrun.entity.UserEntity;
import com.example.reedxiong.allrun.util.Const;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Reed.Xiong on 2016/7/18.
 */
public class NearbyUserBiz extends IntentService {
    private String uri="";
    private ArrayList<UserEntity> list;

    public NearbyUserBiz() {
        super("NearbyUserBiz");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        // http access the tomact
        // back the json data
        RequestParams params=new RequestParams();
       params.addBodyParameter("username","");
        params.addBodyParameter("md5password","");
        params.addBodyParameter("pageIndex","");
        params.addBodyParameter("rowNum","");
        params.addBodyParameter("latitude","");

        params.addBodyParameter("longitude","");
        HttpUtils httpUtils=new HttpUtils(4000);
        httpUtils.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, uri, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
            // back the json
                //2. and then ,handle these json by Jsonparser object
                Intent intent1=new Intent(Const.ACTION_NEARBYUSER);
                try {
                    JSONObject jsonobject=new JSONObject(responseInfo.result);
                    if(jsonobject!=null){
                        if(jsonobject.getString(JsonConst.STATUS).equals("0")){
                            list=JsonParser.userParser(responseInfo.result);
                            intent.putExtra(Const.KEY_DATA,list);
                        }
                    }
                } catch (JSONException e) {
                    intent.putExtra(Const.KEY_DATA,"");
                    e.printStackTrace();
                }
                finally {
                    sendBroadcast(intent1);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("info","NearByException："+s);
                e.printStackTrace();
            }
        });
    }
}
