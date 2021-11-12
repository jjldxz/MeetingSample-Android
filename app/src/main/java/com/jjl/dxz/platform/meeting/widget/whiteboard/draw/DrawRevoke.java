package com.jjl.dxz.platform.meeting.widget.whiteboard.draw;

import com.jjl.dxz.platform.meeting.constant.Message;
import com.jjl.dxz.platform.sdk.mananger.callback.ResultCallback;
import com.jjl.dxz.platform.sdk.util.GsonUtils;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Revoke;
import com.jjl.dxz.platform.meeting.widget.whiteboard.constant.WhiteBoardDrawType;
import com.jjl.dxz.platform.meeting.widget.whiteboard.manage.WhiteboardManager;

import java.util.UUID;

public class DrawRevoke {

    public static void revoke(String targetId, int groupId) {
        Revoke revoke = new Revoke();
        revoke.setFinish(true);
        revoke.setId(UUID.randomUUID().toString());
        revoke.setGroupId(groupId);
        revoke.setTargetId(targetId);
        revoke.setKind(Message.Kind.MSG_DRAWING);
        revoke.setType(WhiteBoardDrawType.DELETE);
        WhiteboardManager.getInstance().sendWhiteboardMsg(GsonUtils.toJson(revoke), resultCallback);
        WhiteboardManager.getInstance().onRevoke(groupId, revoke);
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
