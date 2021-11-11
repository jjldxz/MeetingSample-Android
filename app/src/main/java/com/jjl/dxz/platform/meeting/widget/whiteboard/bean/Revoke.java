package com.jjl.dxz.platform.meeting.widget.whiteboard.bean;

public class Revoke extends WhiteBoardCommand {

    private boolean finish;
    private String targetId;

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
}
