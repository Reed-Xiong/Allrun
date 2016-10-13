package com.example.reedxiong.allrun.entity;

/**
 * Created by Reed.Xiong on 2016/7/14.
 */
import java.io.Serializable;

public class UserEntity implements Serializable {
    /**
     * 在服务器上的用户名
     */
    private String username;
    /**
     * 呢称
     */
    private String name;
    private String user;
    private String group;
    private String password;
    private String iconUrl;



    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getIconUrl() {
        return iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }


}

