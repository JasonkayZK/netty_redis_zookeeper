package com.crazymakercircle.im.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.crazymakercircle.im.common.bean.msg.ProtoMsg;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
public class User implements Serializable {

    String uid;
    String devId;
    String token;
    String nickName;
    transient PLATTYPE platform;
    int intPlatFrom;

    public User() {
        nickName = "nickName";
        setPlatform(PLATTYPE.ANDROID);
    }

    // windows,mac,android, ios, web , other
    public enum PLATTYPE {
        WINDOWS, MAC, ANDROID, IOS, WEB, OTHER;
    }

    private String sessionId;

    @JSONField(serialize = false)
    public void setPlatform(PLATTYPE platform) {

        this.platform = platform;
        this.intPlatFrom = platform.ordinal();

    }

    @JSONField(serialize = false)
    public PLATTYPE getPlatform() {
        if(null==platform)
        {
            this.platform = PLATTYPE.values()[intPlatFrom];
        }
        return platform;
    }

    @JSONField(name = "intPlatFrom")
    public int getIntPlatFrom() {
        this.platform = PLATTYPE.values()[intPlatFrom];
        return intPlatFrom;
    }

    @JSONField(name = "intPlatFrom")
    public void setIntPlatFrom(int code) {
        this.intPlatFrom = code;
        this.platform = PLATTYPE.values()[code];
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + getUid() + '\'' +
                ", nickName='" + getNickName() + '\'' +
                ", platform=" + getPlatform() +
                ", intPlatFrom=" + getIntPlatFrom() +
                '}';
    }

    public static User fromMsg(ProtoMsg.LoginRequest info) {
        User user = new User();
        user.uid = new String(info.getUid());
        user.devId = new String(info.getDeviceId());
        user.token = new String(info.getToken());
        user.setIntPlatFrom(info.getPlatform());
        log.info("登录中: {}", user.toString());
        return user;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
