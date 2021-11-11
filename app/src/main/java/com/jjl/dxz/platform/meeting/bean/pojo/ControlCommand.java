package com.jjl.dxz.platform.meeting.bean.pojo;

import com.jjl.dxz.platform.meeting.bean.BaseCommand;

public class ControlCommand extends BaseCommand {

    private Object value;
    private int roomId;
    private int userId;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
