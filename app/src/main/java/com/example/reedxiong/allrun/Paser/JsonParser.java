package com.example.reedxiong.allrun.Paser;

import com.example.reedxiong.allrun.entity.UserEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Reed.Xiong on 2016/7/18.
 */
public class JsonParser {

    public static ArrayList<UserEntity> userParser(String JsonString){
        ArrayList<UserEntity> userList=new ArrayList<>();
       //get json object
        try {

            JSONObject jsonObject=new JSONObject(JsonString);
            if(jsonObject.getString(JsonConst.STATUS).equals("0")){
                JSONArray jsonArray=jsonObject.getJSONArray(JsonConst.DATA);
               for(int i=0;i<jsonArray.length();i++){
                   JSONObject jsonUserObject = jsonArray.getJSONObject(i);
                   UserEntity userEntity=new UserEntity();
                   if(jsonUserObject.get(JsonConst.username)!=null){
                       userEntity.setUsername(jsonUserObject.getString(JsonConst.username));
                   }
                   if(jsonUserObject.get("nickname")!=null){
                       userEntity.setName(jsonUserObject.getString("nickname"));
                   }
                   if(jsonUserObject.get(JsonConst.iconUrl)!=null){
                       userEntity.setIconUrl(jsonUserObject.getString(JsonConst.iconUrl));
                   }
                   userList.add(userEntity);
               }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  userList;
    }
}
