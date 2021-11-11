package com.jjl.dxz.platform.meeting.bean.resp;

import com.google.gson.annotations.SerializedName;

public class LoginResp {
    @SerializedName("user")
    private int userId;
    private String token;
    private String refreshToken;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
