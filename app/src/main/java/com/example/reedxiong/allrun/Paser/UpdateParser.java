package com.example.reedxiong.allrun.Paser;

import com.example.reedxiong.allrun.entity.VersionEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Reed.Xiong on 2016/7/19.
 */
public class UpdateParser {
    public static VersionEntity parser(String versionInfo){
        VersionEntity versionEntity=new VersionEntity();
        try {
            JSONObject jsonObject=new JSONObject(versionInfo);
            versionEntity.setVersion(jsonObject.getInt("version"));
            versionEntity.setChangeLog(jsonObject.getString("changeLog"));
            versionEntity.setApkUrl(jsonObject.getString("apkUrl"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return versionEntity;
    }
}
