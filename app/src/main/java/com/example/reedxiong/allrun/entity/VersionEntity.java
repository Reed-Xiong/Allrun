package com.example.reedxiong.allrun.entity;

import java.io.Serializable;

/**
 * Created by Reed.Xiong on 2016/7/19.
 */
public class VersionEntity implements Serializable {
    /*
    {"status":"0",
    "msg":"成功",
    "version":"9.0",
"changeLog":"增加了二维码扫描功能 修改了某些机型登录失败问题",
"apkUrl":"http://172.60.50.223:8080/allRunServer/allrun1511.apk"}
     */
    private int version;
    private String changeLog;
    private String apkUrl;

    public void setVersion(int version) {
        this.version = version;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public int getVersion() {
        return version;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public String getApkUrl() {
        return apkUrl;
    }
}
