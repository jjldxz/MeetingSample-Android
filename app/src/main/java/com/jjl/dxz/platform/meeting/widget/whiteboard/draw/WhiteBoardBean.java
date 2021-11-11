package com.jjl.dxz.platform.meeting.widget.whiteboard.draw;

import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Line;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.WhiteBoardCommand;

import java.util.ArrayList;
import java.util.List;

public class WhiteBoardBean {

    private final String type;
    private final WhiteBoardCommand whiteBoardCommon;
    private List<Line> lines = new ArrayList<>();

    public WhiteBoardBean(String type, WhiteBoardCommand whiteBoardCommon) {
        this.type = type;
        this.whiteBoardCommon = whiteBoardCommon;
    }

    public WhiteBoardBean(String type, WhiteBoardCommand whiteBoardCommon, List<Line> lines) {
        this.type = type;
        this.whiteBoardCommon = whiteBoardCommon;
        this.lines = lines;
    }

    public String getType() {
        return type;
    }

    public WhiteBoardCommand getWhiteBoardCommon() {
        return whiteBoardCommon;
    }

    public List<Line> getLines() {
        return lines;
    }
}
