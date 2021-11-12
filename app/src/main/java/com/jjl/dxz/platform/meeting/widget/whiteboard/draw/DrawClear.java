package com.jjl.dxz.platform.meeting.widget.whiteboard.draw;


import com.jjl.dxz.platform.meeting.constant.Message;
import com.jjl.dxz.platform.sdk.mananger.callback.ResultCallback;
import com.jjl.dxz.platform.sdk.util.GsonUtils;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.WhiteBoardCommand;
import com.jjl.dxz.platform.meeting.widget.whiteboard.constant.WhiteBoardDrawType;
import com.jjl.dxz.platform.meeting.widget.whiteboard.manage.WhiteboardManager;

import java.util.UUID;

public class DrawClear {

    public static void clear(int groupId) {
        WhiteBoardCommand bean = new WhiteBoardCommand();
        bean.setId(UUID.randomUUID().toString());
        bean.setGroupId(groupId);
        bean.setKind(Message.Kind.MSG_DRAWING);
        bean.setType(WhiteBoardDrawType.CLEAR_ALL);
        WhiteboardManager.getInstance().sendWhiteboardMsg(GsonUtils.toJson(bean), resultCallback);
        WhiteboardManager.getInstance().onClear(groupId);
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
