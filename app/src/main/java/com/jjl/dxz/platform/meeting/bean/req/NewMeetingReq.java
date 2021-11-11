package com.jjl.dxz.platform.meeting.bean.req;

public class NewMeetingReq {
    private String name;
    private String beginAt;
    private String endAt;
    /**
     * Not required
     */
    private int muteType;
    /**
     * Not required
     */
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public int getMuteType() {
        return muteType;
    }

    public void setMuteType(int muteType) {
        this.muteType = muteType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
