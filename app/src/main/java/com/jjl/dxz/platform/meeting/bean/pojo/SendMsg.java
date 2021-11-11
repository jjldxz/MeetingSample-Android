package com.jjl.dxz.platform.meeting.bean.pojo;

import com.jjl.dxz.platform.meeting.bean.BaseCommand;

public class SendMsg extends BaseCommand {

    private String content;
    private String time;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
