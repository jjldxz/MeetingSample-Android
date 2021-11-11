package com.jjl.dxz.platform.meeting.widget.whiteboard.draw;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.jjl.dxz.platform.meeting.constant.Message;
import com.jjl.dxz.platform.meeting.sdk.mananger.callback.ResultCallback;
import com.jjl.dxz.platform.meeting.sdk.util.GsonUtils;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.StraightLine;
import com.jjl.dxz.platform.meeting.widget.whiteboard.constant.WhiteBoardDrawType;
import com.jjl.dxz.platform.meeting.widget.whiteboard.manage.WhiteboardManager;

import java.util.UUID;

public class DrawStraight {

    private static StraightLine line;

    public static void draw(String paintColor, int fontSize, float width, float height, MotionEvent event, PointF point, int groupId) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                line = new StraightLine();
                line.setId(UUID.randomUUID().toString());
                line.setFinish(true);
                line.setGroupId(groupId);
                line.setLineColor(paintColor);
                line.setLineWidth(fontSize);
                line.setTime(System.currentTimeMillis());
                line.setKind(Message.Kind.MSG_DRAWING);
                line.setType(WhiteBoardDrawType.STRAIGHT_LINE);
                line.setStartDot(new StraightLine.StartDot(point.x / width, point.y / height));
                line.setEndDot(new StraightLine.EndDot(point.x / width, point.y / height));
                WhiteboardManager.getInstance().sendWhiteboardMsg(GsonUtils.toJson(line), resultCallback);
                WhiteboardManager.getInstance().onDrawStraightLine(groupId, line);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                line.setFinish(false);
                line.setEndDot(new StraightLine.EndDot(point.x / width, point.y / height));
                WhiteboardManager.getInstance().sendWhiteboardMsg(GsonUtils.toJson(line), resultCallback);
                WhiteboardManager.getInstance().onDrawStraightLine(groupId, line);
                break;
        }
    }

    private static final ResultCallback resultCallback = new ResultCallback() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onFail(int errCode, String errDes) {

        }
    };
}
