package com.jjl.dxz.platform.meeting.widget.whiteboard.draw;

import android.graphics.PointF;
import android.text.TextUtils;
import android.view.MotionEvent;

import com.jjl.dxz.platform.meeting.constant.Message;
import com.jjl.dxz.platform.meeting.sdk.mananger.callback.ResultCallback;
import com.jjl.dxz.platform.meeting.sdk.util.GsonUtils;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Line;
import com.jjl.dxz.platform.meeting.widget.whiteboard.constant.WhiteBoardDrawType;
import com.jjl.dxz.platform.meeting.widget.whiteboard.manage.WhiteboardManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DrawLine {

    public static String id = "";

    public static void draw(String paintColor, int fontSize, float width, float height, MotionEvent event, PointF point, int groupId) {
        List<Line.Point> points = new ArrayList<>();
        Line line = new Line();
        line.setGroupId(groupId);
        line.setLineColor(paintColor);
        line.setLineWidth(fontSize);
        line.setTime(System.currentTimeMillis());
        line.setKind(Message.Kind.MSG_DRAWING);
        line.setType(WhiteBoardDrawType.LINE);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                id = UUID.randomUUID().toString();
                points.add(new Line.Point(point.x / width, point.y / height));
                line.setPoints(points);
                line.setFinish(true);
                break;
            case MotionEvent.ACTION_MOVE:
                points.add(new Line.Point(point.x / width, point.y / height));
                line.setPoints(points);
                line.setFinish(false);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                id = "";
                break;
        }

        if (!TextUtils.isEmpty(id)) {
            line.setId(id);
            WhiteboardManager.getInstance().sendWhiteboardMsg(GsonUtils.toJson(line), resultCallback);
            WhiteboardManager.getInstance().onDrawLine(groupId, line);
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
