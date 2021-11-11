package com.jjl.dxz.platform.meeting.widget.whiteboard.draw;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.jjl.dxz.platform.meeting.constant.Message;
import com.jjl.dxz.platform.meeting.sdk.mananger.callback.ResultCallback;
import com.jjl.dxz.platform.meeting.sdk.util.GsonUtils;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Rect;
import com.jjl.dxz.platform.meeting.widget.whiteboard.constant.WhiteBoardDrawType;
import com.jjl.dxz.platform.meeting.widget.whiteboard.manage.WhiteboardManager;

import java.util.UUID;

public class DrawRect {

    private static Rect rect;

    public static void draw(String paintColor, int fontSize, float width, float height, MotionEvent event, PointF point, int groupId) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                rect = new Rect();
                rect.setId(UUID.randomUUID().toString());
                rect.setFinish(true);
                rect.setGroupId(groupId);
                rect.setLineColor(paintColor);
                rect.setLineWidth(fontSize);
                rect.setTime(System.currentTimeMillis());
                rect.setKind(Message.Kind.MSG_DRAWING);
                rect.setType(WhiteBoardDrawType.RECT);
                rect.setStartDot(new Rect.StartDot(point.x / width, point.y / height));
                rect.setEndDot(new Rect.EndDot(point.x / width, point.y / height));
                WhiteboardManager.getInstance().sendWhiteboardMsg(GsonUtils.toJson(rect), resultCallback);
                WhiteboardManager.getInstance().onDrawRect(groupId, rect);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                rect.setFinish(false);
                rect.setEndDot(new Rect.EndDot(point.x / width, point.y / height));
                WhiteboardManager.getInstance().sendWhiteboardMsg(GsonUtils.toJson(rect), resultCallback);
                WhiteboardManager.getInstance().onDrawRect(groupId, rect);
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
