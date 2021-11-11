package com.jjl.dxz.platform.meeting.vm.meeting;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.net.ErrorResp;
import com.jjl.dxz.platform.meeting.bean.pojo.ChatMsg;
import com.jjl.dxz.platform.meeting.bean.pojo.ControlCommand;
import com.jjl.dxz.platform.meeting.bean.pojo.SendMsg;
import com.jjl.dxz.platform.meeting.bean.pojo.UserVideo;
import com.jjl.dxz.platform.meeting.bean.resp.BooleanResp;
import com.jjl.dxz.platform.meeting.bean.resp.GroupDetailResp;
import com.jjl.dxz.platform.meeting.constant.BundleKey;
import com.jjl.dxz.platform.meeting.constant.Message;
import com.jjl.dxz.platform.meeting.constant.ReqParamsKey;
import com.jjl.dxz.platform.meeting.constant.SpKey;
import com.jjl.dxz.platform.meeting.sdk.mananger.PlatformManager;
import com.jjl.dxz.platform.meeting.sdk.mananger.bean.ShareScreenConfig;
import com.jjl.dxz.platform.meeting.sdk.mananger.callback.PlatformListener;
import com.jjl.dxz.platform.meeting.sdk.mananger.callback.ResultCallback;
import com.jjl.dxz.platform.meeting.sdk.mananger.constant.AudioOutputRoute;
import com.jjl.dxz.platform.meeting.sdk.mananger.constant.FrameRate;
import com.jjl.dxz.platform.meeting.sdk.mananger.constant.VideoDimensions;
import com.jjl.dxz.platform.meeting.sdk.mananger.constant.VideoOrientation;
import com.jjl.dxz.platform.meeting.sdk.mananger.constant.VideoQualityLevel;
import com.jjl.dxz.platform.meeting.sdk.util.GsonUtils;
import com.jjl.dxz.platform.meeting.ui.LoginActivity;
import com.jjl.dxz.platform.meeting.util.SpUtils;
import com.jjl.dxz.platform.meeting.util.ToastUtils;
import com.jjl.dxz.platform.meeting.widget.whiteboard.manage.WhiteboardManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MeetingViewModel extends MeetingExtPollViewModel {

    private int roomId;
    private boolean isAdmin;
    private boolean isOpenAudio;
    private boolean isOpenVideo;
    private boolean isReceiver;
    private final List<UserVideo> userVideos = new ArrayList<>();
    private final MutableLiveData<Integer> addVideo = new MutableLiveData<>();
    private final MutableLiveData<Integer> refreshVideo = new MutableLiveData<>();
    private final MutableLiveData<Integer> removeVideo = new MutableLiveData<>();
    public final MutableLiveData<Boolean> btnAudioOpen = new MutableLiveData<>();
    public final MutableLiveData<Boolean> btnVideoOpen = new MutableLiveData<>();
    public final MutableLiveData<String> memberCount = new MutableLiveData<>();
    public final MutableLiveData<Integer> showWhiteboard = new MutableLiveData<>(View.GONE);
    public final MutableLiveData<Integer> showShareScreen = new MutableLiveData<>(View.GONE);
    public final MutableLiveData<Integer> showMyShareScreen = new MutableLiveData<>(View.GONE);
    private boolean isShareWhiteboard;
    private boolean isShareScreen;
    private int shareUserId;
    private String shareUserToken;
    private boolean isInitShareScreen;
    public final MutableLiveData<SurfaceView> shareScreen = new MutableLiveData<>();
    private boolean isSyncWhiteboard;
    private final List<ChatMsg> chatMsg = new ArrayList<>();
    private final MutableLiveData<Integer> addChat = new MutableLiveData<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private final MutableLiveData<Void> notifyUserVideo = new MutableLiveData<>();
    private final MutableLiveData<Boolean> myMenuMic = new MutableLiveData<>();
    private final MutableLiveData<Boolean> myMenuCamera = new MutableLiveData<>();
    private boolean isBreakout;

    public MeetingViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void init() {
    }

    public void init(Bundle bundle) {

        roomId = bundle.getInt(BundleKey.ROOM_ID);
        isAdmin = bundle.getBoolean(BundleKey.IS_OWNER);
        shareUserId = bundle.getInt(BundleKey.SHARE_USER_ID);
        shareUserToken = bundle.getString(BundleKey.SHARE_USER_TOKEN);

        isOpenAudio = !bundle.getBoolean(BundleKey.CLOSE_MIC);
        btnAudioOpen.setValue(isOpenAudio);
        isOpenVideo = !bundle.getBoolean(BundleKey.CLOSE_CAMERA);
        btnVideoOpen.setValue(isOpenVideo);
        isReceiver = bundle.getBoolean(BundleKey.OPEN_RECEIVER);

        isBreakout = bundle.getBoolean(BundleKey.IS_BREAKOUT);
        if (isBreakout) {
            Map<String, Integer> params = new HashMap<>();
            params.put("number", roomId);
            mMeetingRepo.getGroupDetails(params, new MyDisposableSubscriber<GroupDetailResp>() {
                @Override
                public void onBefore() {

                }

                @Override
                public void onSuccess(GroupDetailResp groupDetailResp) {
                    groupDetail = groupDetailResp;
                    for (GroupDetailResp.GroupInfo groupInfo : groupDetailResp.getGroup()) {
                        for (Integer user : groupInfo.getUsers()) {
                            if (user.equals(SpUtils.getInt(SpKey.USER_ID, -1))) {
                                myGroupId = groupInfo.getId();
                            }
                        }
                    }
                }

                @Override
                public void onError(ErrorResp errorResp) {

                }

                @Override
                public void onCompleted() {

                }
            });
        }

        PlatformManager.getInstance().init(bundle.getString(BundleKey.APP_KEY), bundle.getString(BundleKey.MEETING_TOKEN), platformListener);

        Map<String, Object> userAttrs = new HashMap<>();
        //音频状态
        userAttrs.put("audio", isOpenAudio);
        //视频状态
        userAttrs.put("video", isOpenVideo);
        //分组id
        userAttrs.put("groupId", 0);
        //举手状态
        userAttrs.put("hand", false);
        //用户名
        userAttrs.put("name", SpUtils.getStr(SpKey.BEFORE_LOGIN_USER_NAME, ""));
        //角色
        userAttrs.put("role", isAdmin ? "host" : "attendee");
        //共享状态
        userAttrs.put("share", "none");

        PlatformManager.getInstance().join(roomId, SpUtils.getInt(SpKey.USER_ID, -1), isAdmin, userAttrs);
    }

    private void syncWhiteboard() {
        if (!isSyncWhiteboard) {
            PlatformManager.getInstance().syncWhiteboard(new ResultCallback() {
                @Override
                public void onSuccess() {
                    isSyncWhiteboard = true;
                }

                @Override
                public void onFail(int errCode, String errDes) {

                }
            });
        }
    }

    private final PlatformListener platformListener = new PlatformListener() {
        @Override
        public void onJoinRoomSuccess(Map<Integer, Map<String, Object>> usersAttr, Map<String, Object> roomAttr) {
            memberCount.setValue(getString(R.string.member_count, usersAttr.size()));
            PlatformManager.getInstance().setLocalRenderer(VideoQualityLevel.LOW, VideoOrientation.PORTRAIT);

            PlatformManager.getInstance().setAudioOutputRoute(isReceiver ? AudioOutputRoute.RECEIVER : AudioOutputRoute.SPEAKER);

            if (!isOpenAudio) {
                PlatformManager.getInstance().muteLocalAudio(true);
            }
            if (!isOpenVideo) {
                PlatformManager.getInstance().muteLocalVideo(true);
            }
            for (Map.Entry<Integer, Map<String, Object>> integerMapEntry : usersAttr.entrySet()) {
                UserVideo userVideo = new UserVideo();
                userVideo.setUserName(integerMapEntry.getValue().get("name").toString());
                userVideo.setRole(integerMapEntry.getValue().get("role").toString());
                userVideo.setUserId(Integer.parseInt(integerMapEntry.getValue().get("user_ext_id").toString()));
                userVideo.setOpenAudio(Boolean.parseBoolean(integerMapEntry.getValue().get("audio").toString()));
                userVideo.setOpenVideo(Boolean.parseBoolean(integerMapEntry.getValue().get("video").toString()));
                if (Integer.parseInt(integerMapEntry.getValue().get("user_ext_id").toString()) == SpUtils.getInt(SpKey.USER_ID, -1)) {
                    userVideo.setSurfaceView(PlatformManager.getInstance().getLocalRenderer());
                } else {
                    userVideo.setSurfaceView(PlatformManager.getInstance().getRemoteRenderer(userVideo.getUserId()));
                }
                userVideos.add(userVideo);
                addVideo.setValue(userVideos.size() - 1);

                Map<String, Object> attr = integerMapEntry.getValue();
                if (attr.containsKey("share")) {
                    Object obj = attr.get("share");
                    if (obj == null) {
                        continue;
                    }
                    switch (obj.toString()) {
                        case "whiteboard":
                            showWhiteboard.setValue(View.VISIBLE);
                            syncWhiteboard();
                            break;
                        case "desktop":
                            isShareScreen = true;
                            if (isInitShareScreen) {
                                showShareScreen.setValue(View.VISIBLE);
                                shareScreen.setValue(PlatformManager.getInstance().getShareScreenRenderer());
                            } else {
                                ShareScreenConfig config = new ShareScreenConfig.Builder()
                                        .setVideoDimensions(VideoDimensions.VD_640x480)
                                        .setFrameRate(FrameRate.FPS_15)
                                        .setVideoOrientation(VideoOrientation.ADAPTIVE).build();
                                PlatformManager.getInstance().initShareScreen(shareUserId, shareUserToken, config, new ResultCallback() {
                                    @Override
                                    public void onSuccess() {
                                        isInitShareScreen = true;
                                        showShareScreen.setValue(View.VISIBLE);
                                        shareScreen.setValue(PlatformManager.getInstance().getShareScreenRenderer());
                                    }

                                    @Override
                                    public void onFail(int errCode, String errDes) {

                                    }
                                });
                            }
                            break;
                    }
                }
            }
        }

        @Override
        public void onJoinRoomFail(int errCode, String errDes) {
            ToastUtils.showShort(getContext(), errCode + ":" + errDes);
        }

        @Override
        public void onRoomClose() {
            Log.e("macrobin", "onRoomClose");
            ToastUtils.showLong(getContext(), R.string.room_closed);
            finish();
        }

        @Override
        public void onUserJoinRoom(int userId, Map<String, Object> userAttr) {
            Log.e("macrobin", "onUserJoinRoom:userId=" + userId);
            ToastUtils.showShort(getContext(), userId + "进入房间了");
            memberCount.setValue(getString(R.string.member_count, PlatformManager.getInstance().getAllUsers().size()));
            UserVideo userVideo = new UserVideo();
            userVideo.setUserId(userId);
            userVideo.setUserName(userAttr.get("name").toString());
            userVideo.setRole(userAttr.get("role").toString());
            userVideo.setGroupId(Integer.parseInt(userAttr.get("groupId").toString()));
            userVideo.setOpenVideo(Boolean.parseBoolean(userAttr.get("video").toString()));
            userVideo.setOpenAudio(Boolean.parseBoolean(userAttr.get("audio").toString()));
            userVideos.add(userVideo);
        }

        @Override
        public void onUserLeaveRoom(int userId) {
            Log.e("macrobin", "onUserLeaveRoom:userId=" + userId);
            ToastUtils.showShort(getContext(), userId + "离开房间了");
            int position = -1;
            for (int i = 0; i < userVideos.size(); i++) {
                if (userVideos.get(i).getUserId() == userId) {
                    position = i;
                    break;
                }
            }
            if (position != -1) {
                userVideos.remove(position);
                removeVideo.setValue(position);
            }
            memberCount.setValue(getString(R.string.member_count, PlatformManager.getInstance().getAllUsers().size()));
        }

        @Override
        public void onRoomAttrChanged(int roomId, Map<String, Object> roomAttr) {

        }

        @Override
        public void onUserAttrChanged(int userId, Map<String, Object> userAttr) {
            Log.e("macrobin", "onUserAttrChanged:userId=" + userId + ",attrs=" + userAttr);
            if (userAttr.containsKey("audio")) {
                for (int i = 0; i < userVideos.size(); i++) {
                    UserVideo userVideo = userVideos.get(i);
                    if (userVideo.getUserId() == userId) {
                        userVideo.setOpenAudio(Boolean.parseBoolean(userAttr.get("audio").toString()));
                        refreshVideo.setValue(i);
                        break;
                    }
                }
            } else if (userAttr.containsKey("video")) {
                for (int i = 0; i < userVideos.size(); i++) {
                    UserVideo userVideo = userVideos.get(i);
                    if (userVideo.getUserId() == userId) {
                        userVideo.setOpenVideo(Boolean.parseBoolean(userAttr.get("video").toString()));
                        refreshVideo.setValue(i);
                        break;
                    }
                }
            } else if (userAttr.containsKey("share")) {
                Object obj = userAttr.get("share");
                if (obj == null) {
                    return;
                }
                switch (obj.toString()) {
                    case "whiteboard":
                        showWhiteboard.setValue(View.VISIBLE);
                        syncWhiteboard();
                        break;
                    case "desktop":
                        isShareScreen = true;
                        if (isInitShareScreen) {
                            showShareScreen.setValue(View.VISIBLE);
                            shareScreen.setValue(PlatformManager.getInstance().getShareScreenRenderer());
                        } else {
                            ShareScreenConfig config = new ShareScreenConfig.Builder()
                                    .setVideoDimensions(VideoDimensions.VD_640x480)
                                    .setFrameRate(FrameRate.FPS_15)
                                    .setVideoOrientation(VideoOrientation.ADAPTIVE).build();
                            PlatformManager.getInstance().initShareScreen(shareUserId, shareUserToken, config, new ResultCallback() {
                                @Override
                                public void onSuccess() {
                                    isInitShareScreen = true;
                                    showShareScreen.setValue(View.VISIBLE);
                                    shareScreen.setValue(PlatformManager.getInstance().getShareScreenRenderer());
                                }

                                @Override
                                public void onFail(int errCode, String errDes) {

                                }
                            });
                        }
                        break;
                    case "none":
                        showWhiteboard.setValue(View.GONE);
                        showShareScreen.setValue(View.GONE);
                        if (isShareScreen) {
                            isShareScreen = false;
                            notifyUserVideo.setValue(null);
                        }
                        break;
                }
            }
        }

        @Override
        public void onKickOut(int senderId) {
            Log.e("macrobin", "onKickOut");
            ToastUtils.showLong(getContext(), R.string.kick_out);
            finish();
        }

        @Override
        public void onRepeatLogin() {
            Log.e("macrobin", "onRepeatLogin");
            ToastUtils.showLong(getContext(), R.string.repeat_login);
            startActivity(LoginActivity.class);
            finish();
        }

        @Override
        public void onUserStartPublishStream(int userId) {
            Log.e("macrobin", "onUserStartPublishStream:userId=" + userId);
            for (int i = 0; i < userVideos.size(); i++) {
                UserVideo userVideo = userVideos.get(i);
                if (userVideo.getUserId() == userId) {
                    userVideo.setSurfaceView(PlatformManager.getInstance().getRemoteRenderer(userId));
                    addVideo.setValue(i);
                    break;
                }
            }
        }

        @Override
        public void onUserStopPublishStream(int userId) {
            Log.e("macrobin", "onUserStopPublishStream:userId=" + userId);
        }

//        @Override
//        public void onRoomControlMsg(int receiveRoomId, int senderId, String message) {
//            Log.e("macrobin", "onRoomControlMsg:receiveRoomId=" + receiveRoomId + ",senderId=" + senderId + ",message=" + message);
//        }
//
//        @Override
//        public void onControlMsg(int senderId, String message) {
//            Log.e("macrobin", "onControlMsg:senderId=" + senderId + ",message=" + message);
//        }
//
//        @Override
//        public void onRoomChatMsg(int receiveRoomId, int senderId, String message) {
//            Log.e("macrobin", "onRoomChatMsg:receiveRoomId=" + receiveRoomId + ",senderId=" + senderId + ",message=" + message);
//            SendMsg sendMsg = GsonUtils.getBean(message, SendMsg.class);
//            ChatMsg chatMsg = new ChatMsg();
//            chatMsg.setType(1);
//            chatMsg.setContent(sendMsg.getContent());
//            chatMsg.setName(PlatformManager.getInstance().getUser(senderId).get("name").toString() + "对所有人说");
//            chatMsg.setPrivacy(0);
//            getChatMsg().add(chatMsg);
//            addChat.setValue(getChatMsg().size() - 1);
//        }
//
//        @Override
//        public void onChatMsg(int senderId, String message) {
//            Log.e("macrobin", "onChatMsg:senderId=" + senderId + ",message=" + message);
//            SendMsg sendMsg = GsonUtils.getBean(message, SendMsg.class);
//            ChatMsg chatMsg = new ChatMsg();
//            chatMsg.setType(1);
//            chatMsg.setContent(sendMsg.getContent());
//            chatMsg.setName(PlatformManager.getInstance().getUser(senderId).get("name").toString() + "对我说");
//            chatMsg.setPrivacy(1);
//            getChatMsg().add(chatMsg);
//            addChat.setValue(getChatMsg().size() - 1);
//        }
//
//        @Override
//        public void onRoomCustomMsg(int receiveRoomId, String category, int senderId, String message) {
//            Log.e("macrobin", "onRoomCustomMsg:receiveRoomId=" + receiveRoomId + ",category=" + category + ",senderId=" + senderId + ",message=" + message);
//        }
//
//        @Override
//        public void onCustomMsg(String category, int senderId, String message) {
//            Log.e("macrobin", "onCustomMsg:category=" + category + ",senderId=" + senderId + ",message=" + message);
//        }

        @Override
        public void onPrivateMessage(String category, int senderId, String message) {
            Log.e("macrobin", "onUserMessage:category=" + category + ",senderId=" + senderId + ",message=" + message);
            switch (category) {
                case Message.Category.WHITEBOARD:
                    WhiteboardManager.getInstance().analysisMsg(message);
                case Message.Category.CONTROL:
                    controlMsgHandler(senderId, message, false);
                    break;
                case Message.Category.CHAT:
                    chatMsgHandler(senderId, message, false);
                    break;
            }
        }

        @Override
        public void onRoomMessage(String category, int senderId, String message) {
            Log.e("macrobin", "onRoomMessage:category=" + category + ",senderId=" + senderId + ",message=" + message);
            if (isBreakout) {
                if (!isAdmin && (myGroupId == -1 || getSenderInGroupId(senderId) != myGroupId)) {
                    return;
                }
            }

            switch (category) {
                case Message.Category.WHITEBOARD:
                    WhiteboardManager.getInstance().analysisMsg(message);
                    break;
                case Message.Category.CONTROL:
                    controlMsgHandler(senderId, message, true);
                    break;
                case Message.Category.CHAT:
                    chatMsgHandler(senderId, message, true);
                    break;
            }
        }

        @Override
        public void onUserSpeak(Map<String, Integer>[] maps) {

        }
    };

    private void chatMsgHandler(int senderId, String message, boolean isRoomMsg) {

        SendMsg sendMsg = GsonUtils.getBean(message, SendMsg.class);
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setType(1);
        chatMsg.setContent(sendMsg.getContent());
        if (isRoomMsg) {
            chatMsg.setName(PlatformManager.getInstance().getUser(senderId).get("name").toString() + "对所有人说");
            chatMsg.setPrivacy(0);
        } else {
            chatMsg.setName(PlatformManager.getInstance().getUser(senderId).get("name").toString() + "对我说");
            chatMsg.setPrivacy(1);
        }
        getChatMsg().add(chatMsg);
        addChat.setValue(getChatMsg().size() - 1);
    }

    private void controlMsgHandler(int senderId, String message, boolean isRoomMsg) {
        ControlCommand controlCommand = GsonUtils.getBean(message, ControlCommand.class);
        if (controlCommand.getUserId() != SpUtils.getInt(SpKey.USER_ID, -1)) {
            return;
        }
        if (Message.Kind.MSG_CONTROL.equals(controlCommand.getKind())) {
            switch (controlCommand.getType()) {
                case Message.Type.CTL_AUDIO_CHANGE:
                    changeMicState(0);
                    break;
                case Message.Type.CTL_VIDEO_CHANGE:
                    changeCameraState(0);
                    break;
                case Message.Type.CTL_NAME_CHANGE:
                    Map<String, Object> attrs = new HashMap<>();
                    attrs.put("name", controlCommand.getValue().toString());
                    PlatformManager.getInstance().updateUserAttrs(attrs, new ResultCallback() {
                        @Override
                        public void onSuccess() {
                            userVideos.get(0).setUserName(controlCommand.getValue().toString());
                            refreshVideo.setValue(0);
                        }

                        @Override
                        public void onFail(int i, String s) {

                        }
                    });
                    break;
                case Message.Type.CTL_ROLE_CHANGE:
                    attrs = new HashMap<>();
                    attrs.put("role", controlCommand.getValue().toString());
                    PlatformManager.getInstance().updateUserAttrs(attrs, new ResultCallback() {
                        @Override
                        public void onSuccess() {
                            userVideos.get(0).setRole(controlCommand.getValue().toString());
                            refreshVideo.setValue(0);
                        }

                        @Override
                        public void onFail(int i, String s) {

                        }
                    });
                    break;
            }
        }
    }

    public void shareWhiteboard() {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("share", !isShareWhiteboard ? "whiteboard" : "none");
        PlatformManager.getInstance().updateUserAttrs(attrs, new ResultCallback() {
            @Override
            public void onSuccess() {
                isShareWhiteboard = !isShareWhiteboard;
                showWhiteboard.setValue(isShareWhiteboard ? View.VISIBLE : View.GONE);
                syncWhiteboard();
            }

            @Override
            public void onFail(int errCode, String errDes) {

            }
        });
    }

    public void shareScreen() {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("share", !isShareScreen ? "desktop" : "none");
        PlatformManager.getInstance().updateUserAttrs(attrs, new ResultCallback() {
            @Override
            public void onSuccess() {
                isShareScreen = !isShareScreen;
                if (isShareScreen) {
                    if (!isInitShareScreen) {
                        ShareScreenConfig config = new ShareScreenConfig.Builder()
                                .setVideoDimensions(VideoDimensions.VD_640x360)
                                .setFrameRate(FrameRate.FPS_15)
                                .setVideoOrientation(VideoOrientation.ADAPTIVE).build();
                        PlatformManager.getInstance().initShareScreen(shareUserId, shareUserToken, config, new ResultCallback() {
                            @Override
                            public void onSuccess() {
                                isInitShareScreen = true;
                                execShareScreen();
                            }

                            @Override
                            public void onFail(int errCode, String errDes) {

                            }
                        });
                    } else {
                        execShareScreen();
                    }
                } else {
                    execShareScreen();
                }
            }

            @Override
            public void onFail(int errCode, String errDes) {

            }
        });
    }

    private void execShareScreen() {
        PlatformManager.getInstance().shareScreen(isShareScreen, new ResultCallback() {
            @Override
            public void onSuccess() {
                showMyShareScreen.setValue(isShareScreen ? View.VISIBLE : View.GONE);
                if (!isShareScreen) {
                    notifyUserVideo.setValue(null);
                }
            }

            @Override
            public void onFail(int errCode, String errDes) {

            }
        });
    }

    public void setSoundOutputRoute(MenuItem item) {
        PlatformManager.getInstance().setAudioOutputRoute(isReceiver ? AudioOutputRoute.SPEAKER : AudioOutputRoute.RECEIVER);
        isReceiver = !isReceiver;
        item.setIcon(isReceiver ? R.mipmap.ic_receiver : R.mipmap.ic_speaker);
    }

    public void switchCamera() {
        PlatformManager.getInstance().switchCamera();
    }

    public void chat(int userId, String msg) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setKind(Message.Kind.MSG_CHAT);
        sendMsg.setType("chat_text_msg");
        sendMsg.setContent(msg);
        sendMsg.setTime(sdf.format(new Date()));
        if (userId == -1) {
            PlatformManager.getInstance().sendMessageToRoom(Message.Category.CHAT, GsonUtils.toJson(sendMsg), new ResultCallback() {
                @Override
                public void onSuccess() {
                    ChatMsg chatMsg = new ChatMsg();
                    chatMsg.setType(0);
                    chatMsg.setContent(msg);
                    chatMsg.setName(SpUtils.getStr(SpKey.BEFORE_LOGIN_USER_NAME, "") + "对所有人说");
                    chatMsg.setPrivacy(0);
                    getChatMsg().add(chatMsg);
                    addChat.setValue(getChatMsg().size() - 1);
                }

                @Override
                public void onFail(int errCode, String errDes) {

                }
            });
        } else {
            PlatformManager.getInstance().sendMessageToUser(Message.Category.CHAT, userId, GsonUtils.toJson(sendMsg), new ResultCallback() {
                @Override
                public void onSuccess() {
                    ChatMsg chatMsg = new ChatMsg();
                    chatMsg.setType(0);
                    chatMsg.setContent(msg);
                    chatMsg.setName(SpUtils.getStr(SpKey.BEFORE_LOGIN_USER_NAME, "") + "对" + PlatformManager.getInstance().getUser(userId).get("name").toString() + "说");
                    chatMsg.setPrivacy(1);
                    getChatMsg().add(chatMsg);
                    addChat.setValue(getChatMsg().size() - 1);
                }

                @Override
                public void onFail(int errCode, String errDes) {

                }
            });
        }
    }

    public void changeMicState(int position) {
        UserVideo userVideo = userVideos.get(position);
        if (userVideo.getUserId() == SpUtils.getInt(SpKey.USER_ID, -1)) {
            Map<String, Object> attrs = new HashMap<>();
            attrs.put("audio", !userVideo.isOpenAudio());
            PlatformManager.getInstance().updateUserAttrs(attrs, new ResultCallback() {
                @Override
                public void onSuccess() {
                    if (PlatformManager.getInstance().muteLocalAudio(userVideo.isOpenAudio()) == 0) {
                        userVideo.setOpenAudio(!userVideo.isOpenAudio());
                        refreshVideo.setValue(position);
                        btnAudioOpen.setValue(userVideo.isOpenAudio());
                        myMenuMic.setValue(userVideo.isOpenAudio());
                    }
                }

                @Override
                public void onFail(int errCode, String errDes) {

                }
            });
        } else {
            ControlCommand controlCommand = new ControlCommand();
            controlCommand.setKind(Message.Kind.MSG_CONTROL);
            controlCommand.setType(Message.Type.CTL_AUDIO_CHANGE);
            controlCommand.setRoomId(roomId);
            controlCommand.setUserId(userVideo.getUserId());
            controlCommand.setValue(!userVideo.isOpenAudio());
            PlatformManager.getInstance().sendMessageToRoom(Message.Category.CONTROL, GsonUtils.toJson(controlCommand), new ResultCallback() {
                @Override
                public void onSuccess() {
                    userVideo.setOpenAudio(!userVideo.isOpenAudio());
                    refreshVideo.setValue(position);
                    btnAudioOpen.setValue(userVideo.isOpenAudio());
                }

                @Override
                public void onFail(int i, String s) {

                }
            });
        }
    }

    public void changeCameraState(int position) {
        UserVideo userVideo = userVideos.get(position);
        if (userVideo.getUserId() == SpUtils.getInt(SpKey.USER_ID, -1)) {
            Map<String, Object> attrs = new HashMap<>();
            attrs.put("video", !userVideo.isOpenVideo());
            PlatformManager.getInstance().updateUserAttrs(attrs, new ResultCallback() {
                @Override
                public void onSuccess() {
                    if (PlatformManager.getInstance().muteLocalVideo(userVideo.isOpenVideo()) == 0) {
                        userVideo.setOpenVideo(!userVideo.isOpenVideo());
                        refreshVideo.setValue(position);
                        myMenuCamera.setValue(userVideo.isOpenVideo());
                    }
                }

                @Override
                public void onFail(int errCode, String errDes) {

                }
            });
        } else {
            ControlCommand controlCommand = new ControlCommand();
            controlCommand.setKind(Message.Kind.MSG_CONTROL);
            controlCommand.setType(Message.Type.CTL_VIDEO_CHANGE);
            controlCommand.setRoomId(roomId);
            controlCommand.setUserId(userVideo.getUserId());
            controlCommand.setValue(!userVideo.isOpenVideo());
            PlatformManager.getInstance().sendMessageToRoom(Message.Category.CONTROL, GsonUtils.toJson(controlCommand), new ResultCallback() {
                @Override
                public void onSuccess() {
                    userVideo.setOpenAudio(!userVideo.isOpenVideo());
                    refreshVideo.setValue(position);
                }

                @Override
                public void onFail(int i, String s) {

                }
            });
        }
    }

    public void closeRoom() {
        Map<String, Integer> params = new HashMap<>();
        params.put(ReqParamsKey.NUMBER, roomId);
        mMeetingRepo.closeRoom(params, new MyDisposableSubscriber<BooleanResp>() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onSuccess(BooleanResp booleanResp) {
                if (booleanResp.isSuccess()) {
                    PlatformManager.getInstance().closeRoom(new ResultCallback() {
                        @Override
                        public void onSuccess() {
                            finish();
                        }

                        @Override
                        public void onFail(int errCode, String errDes) {

                        }
                    });
                }
            }

            @Override
            public void onError(ErrorResp errorResp) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }

    /**
     * 获取发送者所在分组的id
     *
     * @param senderId 发送人id
     * @return 分组id
     */
    private int getSenderInGroupId(int senderId) {
        for (GroupDetailResp.GroupInfo groupInfo : groupDetail.getGroup()) {
            for (Integer user : groupInfo.getUsers()) {
                if (user.equals(senderId)) {
                    return groupInfo.getId();
                }
            }
        }
        return -1;
    }

    public List<UserVideo> getUserVideos() {
        return userVideos;
    }

    public LiveData<Integer> getAddVideo() {
        return addVideo;
    }

    public LiveData<Integer> getRefreshVideo() {
        return refreshVideo;
    }

    public LiveData<Integer> getRemoveVideo() {
        return removeVideo;
    }

    public LiveData<SurfaceView> getShareScreen() {
        return shareScreen;
    }

    public List<ChatMsg> getChatMsg() {
        return chatMsg;
    }

    public LiveData<Integer> getAddChat() {
        return addChat;
    }

    public LiveData<Void> getNotifyUserVideo() {
        return notifyUserVideo;
    }

    public LiveData<Boolean> getMyMenuMic() {
        return myMenuMic;
    }

    public LiveData<Boolean> getMyMenuCamera() {
        return myMenuCamera;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        PlatformManager.getInstance().leaveRoom();
        WhiteboardManager.getInstance().clearWhiteBoardListener();
    }
}
