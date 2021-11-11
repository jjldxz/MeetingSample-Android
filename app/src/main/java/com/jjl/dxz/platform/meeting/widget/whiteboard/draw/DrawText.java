package com.jjl.dxz.platform.meeting.widget.whiteboard.draw;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.jjl.dxz.platform.meeting.constant.Message;
import com.jjl.dxz.platform.meeting.sdk.mananger.callback.ResultCallback;
import com.jjl.dxz.platform.meeting.sdk.util.GsonUtils;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Text;
import com.jjl.dxz.platform.meeting.widget.whiteboard.constant.WhiteBoardDrawType;
import com.jjl.dxz.platform.meeting.widget.whiteboard.manage.WhiteboardManager;

import java.util.UUID;

public class DrawText {

    public static void draw(String paintColor, int fontSize, float width, float height, String drawText, float textW, float textH, MotionEvent event, PointF point, int groupId) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Text text = new Text();
                text.setId(UUID.randomUUID().toString());
                text.setFinish(true);
                text.setGroupId(groupId);
                text.setContent(drawText);
                text.setLineColor(paintColor);
                text.setLineWidth(fontSize);
                text.setKind(Message.Kind.MSG_DRAWING);
                text.setType(WhiteBoardDrawType.TEXT);
                text.setTime(System.currentTimeMillis());
                text.setStartDot(new Text.StartDot(point.x / width, point.y / height));
                text.setTextW(textW / width);
                text.setTextH(textH / height);
                WhiteboardManager.getInstance().sendWhiteboardMsg(GsonUtils.toJson(text), resultCallback);
                WhiteboardManager.getInstance().onDrawText(groupId, text);
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
