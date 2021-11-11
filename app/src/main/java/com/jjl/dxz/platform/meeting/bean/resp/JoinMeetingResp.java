package com.jjl.dxz.platform.meeting.bean.resp;

public class JoinMeetingResp {
    private String token;
    private String appKey;
    private int roomId;
    private int shareUserId;
    private String shareUserToken;
    private boolean isBreakout;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(int shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getShareUserToken() {
        return shareUserToken;
    }

    public void setShareUserToken(String shareUserToken) {
        this.shareUserToken = shareUserToken;
    }

    public boolean isBreakout() {
        return isBreakout;
    }

    public void setBreakout(boolean breakout) {
        isBreakout = breakout;
    }
}
