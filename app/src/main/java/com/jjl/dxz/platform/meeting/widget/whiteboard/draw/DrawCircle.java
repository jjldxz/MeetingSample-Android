package com.jjl.dxz.platform.meeting.widget.whiteboard.draw;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.jjl.dxz.platform.meeting.constant.Message;
import com.jjl.dxz.platform.sdk.mananger.callback.ResultCallback;
import com.jjl.dxz.platform.sdk.util.GsonUtils;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Round;
import com.jjl.dxz.platform.meeting.widget.whiteboard.constant.WhiteBoardDrawType;
import com.jjl.dxz.platform.meeting.widget.whiteboard.manage.WhiteboardManager;

import java.util.UUID;

public class DrawCircle {

    private static Round round;
    private static float downX;
    private static float downY;

    public static void draw(String paintColor, int fontSize, float width, float height, MotionEvent event, PointF point, int groupId) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                downX = event.getX();
                downY = event.getY();
                round = new Round();
                round.setId(UUID.randomUUID().toString());
                round.setFinish(true);
                round.setGroupId(groupId);
                round.setLineColor(paintColor);
                round.setLineWidth(fontSize);
                round.setTime(System.currentTimeMillis());
                round.setKind(Message.Kind.MSG_DRAWING);
                round.setType(WhiteBoardDrawType.ROUND);
                round.setStartDot(new Round.StartDot(point.x / width, point.y / height));
                round.setEndDot(new Round.EndDot(point.x / width, point.y / height));
                WhiteboardManager.getInstance().sendWhiteboardMsg(GsonUtils.toJson(round), resultCallback);
                WhiteboardManager.getInstance().onDrawRound(groupId, round);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                round.setFinish(false);
                float moveX = point.x;
                float moveY = point.y;
                round.setStartDot(new Round.StartDot(downX / width, downY / height));
                round.setEndDot(new Round.EndDot(moveX / width, moveY / height));
                WhiteboardManager.getInstance().sendWhiteboardMsg(GsonUtils.toJson(round), resultCallback);
                WhiteboardManager.getInstance().onDrawRound(groupId, round);
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
