package com.jjl.dxz.platform.meeting.widget.whiteboard.manage;

import com.jjl.dxz.platform.meeting.bean.BaseCommand;
import com.jjl.dxz.platform.meeting.constant.Message;
import com.jjl.dxz.platform.meeting.sdk.mananger.PlatformManager;
import com.jjl.dxz.platform.meeting.sdk.mananger.callback.ResultCallback;
import com.jjl.dxz.platform.meeting.sdk.util.GsonUtils;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Line;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Rect;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Revoke;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Round;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.StraightLine;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Text;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.UpdateWhiteboard;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.WhiteBoardCommand;
import com.jjl.dxz.platform.meeting.widget.whiteboard.constant.WhiteBoardDrawType;

import java.util.HashMap;
import java.util.Map;

public class WhiteboardManager implements WhiteboardListener {

    private static WhiteboardManager instance;
    private final Map<Integer, WhiteboardListener> mListeners;

    private WhiteboardManager() {
        mListeners = new HashMap<>();
    }

    public static WhiteboardManager getInstance() {
        if (instance == null) {
            synchronized (WhiteboardManager.class) {
                if (instance == null) {
                    instance = new WhiteboardManager();
                }
            }
        }
        return instance;
    }

    public void addWhiteBoardListener(int groupId, WhiteboardListener whiteBoardListener) {
        mListeners.put(groupId, whiteBoardListener);
    }

    public void removeWhiteBoardListener(int groupId) {
        mListeners.remove(groupId);
    }

    public void clearWhiteBoardListener() {
        mListeners.clear();
    }

    public void analysisMsg(String msg) {

        WhiteBoardCommand command = GsonUtils.getBean(msg, WhiteBoardCommand.class);
        switch (command.getKind()) {
            case Message.Kind.MSG_DRAWING:
                draw(command.getGroupId(), command.getType(), msg);
                break;
            case Message.Kind.MSG_CONTROL:
                if (command.getType().equals(Message.Type.CONTROL_UPDATE)) {
                    UpdateWhiteboard updateWhiteboard = GsonUtils.getBean(msg, UpdateWhiteboard.class);
                    if (updateWhiteboard.getUpdateType().equals(Message.UpdateType.UPDATE_WHITEBOARD)) {
                        for (UpdateWhiteboard.Pages page : updateWhiteboard.getPages()) {
                            if (!page.getState().equals("active")) {
                                continue;
                            }
                            for (String datum : page.getData()) {
                                BaseCommand baseCommand = GsonUtils.getBean(datum, BaseCommand.class);
                                draw(updateWhiteboard.getGroupId(), baseCommand.getType(), datum);
                            }
                        }
                    }
                }
                break;
        }
    }

    private void draw(int groupId, String type, String msg) {
        switch (type) {
            case WhiteBoardDrawType.LINE:
                onDrawLine(groupId, GsonUtils.getBean(msg, Line.class));
                break;
            case WhiteBoardDrawType.STRAIGHT_LINE:
                onDrawStraightLine(groupId, GsonUtils.getBean(msg, StraightLine.class));
                break;
            case WhiteBoardDrawType.RECT:
                onDrawRect(groupId, GsonUtils.getBean(msg, Rect.class));
                break;
            case WhiteBoardDrawType.ROUND:
                onDrawRound(groupId, GsonUtils.getBean(msg, Round.class));
                break;
            case WhiteBoardDrawType.TEXT:
                onDrawText(groupId, GsonUtils.getBean(msg, Text.class));
                break;
            case WhiteBoardDrawType.CLEAR_ALL:
                onClear(groupId);
                break;
            case WhiteBoardDrawType.DELETE:
                onRevoke(groupId, GsonUtils.getBean(msg, Revoke.class));
                break;
        }
    }

    @Override
    public void onDrawLine(int groupId, Line line) {
        for (Map.Entry<Integer, WhiteboardListener> entry : mListeners.entrySet()) {
            if (entry.getKey() == groupId) {
                entry.getValue().onDrawLine(groupId, line);
                break;
            }
        }
    }

    @Override
    public void onDrawStraightLine(int groupId, StraightLine straightLine) {
        for (Map.Entry<Integer, WhiteboardListener> entry : mListeners.entrySet()) {
            if (entry.getKey() == groupId) {
                entry.getValue().onDrawStraightLine(groupId, straightLine);
                break;
            }
        }
    }

    @Override
    public void onDrawRect(int groupId, Rect rect) {
        for (Map.Entry<Integer, WhiteboardListener> entry : mListeners.entrySet()) {
            if (entry.getKey() == groupId) {
                entry.getValue().onDrawRect(groupId, rect);
                break;
            }
        }
    }

    @Override
    public void onDrawRound(int groupId, Round round) {
        for (Map.Entry<Integer, WhiteboardListener> entry : mListeners.entrySet()) {
            if (entry.getKey() == groupId) {
                entry.getValue().onDrawRound(groupId, round);
                break;
            }
        }
    }

    @Override
    public void onDrawText(int groupId, Text text) {
        for (Map.Entry<Integer, WhiteboardListener> entry : mListeners.entrySet()) {
            if (entry.getKey() == groupId) {
                entry.getValue().onDrawText(groupId, text);
                break;
            }
        }
    }

    @Override
    public void onClear(int groupId) {
        for (Map.Entry<Integer, WhiteboardListener> entry : mListeners.entrySet()) {
            if (entry.getKey() == groupId) {
                entry.getValue().onClear(groupId);
                break;
            }
        }
    }

    @Override
    public void onRevoke(int groupId, Revoke revoke) {
        for (Map.Entry<Integer, WhiteboardListener> entry : mListeners.entrySet()) {
            if (entry.getKey() == groupId) {
                entry.getValue().onRevoke(groupId, revoke);
                break;
            }
        }
    }

    public void sendWhiteboardMsg(String jsonMsg, ResultCallback callback) {
        PlatformManager.getInstance().sendMessageToRoom(Message.Category.WHITEBOARD, jsonMsg, callback);
    }
}
