package com.jjl.dxz.platform.meeting.widget.whiteboard.manage;

import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Line;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Rect;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Revoke;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Round;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.StraightLine;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Text;

public interface WhiteboardListener {

    void onDrawLine(int groupId, Line line);

    void onDrawStraightLine(int groupId, StraightLine straightLine);

    void onDrawRect(int groupId, Rect rect);

    void onDrawRound(int groupId, Round round);

    void onDrawText(int groupId, Text text);

    void onClear(int groupId);

    void onRevoke(int groupId, Revoke revoke);
}
