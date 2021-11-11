package com.jjl.dxz.platform.meeting.bean.pojo;

import android.view.SurfaceView;

public class UserVideo implements Cloneable {
    private int userId;
    private String userName;
    private String role;
    private int groupId;
    private SurfaceView surfaceView;
    private boolean isOpenVideo;
    private boolean isOpenAudio;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public SurfaceView getSurfaceView() {
        return surfaceView;
    }

    public void setSurfaceView(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }

    public boolean isOpenVideo() {
        return isOpenVideo;
    }

    public void setOpenVideo(boolean openVideo) {
        isOpenVideo = openVideo;
    }

    public boolean isOpenAudio() {
        return isOpenAudio;
    }

    public void setOpenAudio(boolean openAudio) {
        isOpenAudio = openAudio;
    }
}
