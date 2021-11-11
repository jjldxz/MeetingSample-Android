package com.jjl.dxz.platform.meeting.bean.req;

import java.util.List;

public class MoveMemberReq {
    /**
     * 会议号
     */
    private int number;
    /**
     * 待移动的组员id
     */
    private List<Integer> members;
    /**
     * 从哪个组离开的小组id
     */
    private int fromGroup;
    /**
     * 去到哪个组的小组id
     */
    private int toGroup;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Integer> getMembers() {
        return members;
    }

    public void setMembers(List<Integer> members) {
        this.members = members;
    }

    public int getFromGroup() {
        return fromGroup;
    }

    public void setFromGroup(int fromGroup) {
        this.fromGroup = fromGroup;
    }

    public int getToGroup() {
        return toGroup;
    }

    public void setToGroup(int toGroup) {
        this.toGroup = toGroup;
    }
}
