package com.jjl.dxz.platform.meeting.widget.whiteboard.bean;

import com.jjl.dxz.platform.meeting.bean.BaseCommand;

public class WhiteBoardCommand extends BaseCommand {

    private String id;
    private int pageId = 1;
    private int groupId = -1;
    private long time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
