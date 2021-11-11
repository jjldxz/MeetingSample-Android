package com.jjl.dxz.platform.meeting.bean.resp;

public class PollStartOrStopResp {
    private int id;
    private String title;
    private int round;
    private int status;
    private boolean isAnonymous;
    private boolean share;
    private int meeting;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public int getMeeting() {
        return meeting;
    }

    public void setMeeting(int meeting) {
        this.meeting = meeting;
    }
}
