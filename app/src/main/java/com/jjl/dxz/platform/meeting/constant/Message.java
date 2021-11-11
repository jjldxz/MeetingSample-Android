package com.jjl.dxz.platform.meeting.constant;

public interface Message {
    /**
     * 最外类别
     */
    interface Category {
        /**
         * 控制消息
         */
        public static final String CONTROL = "control";
        /**
         * 白板消息
         */
        public static final String WHITEBOARD = "whiteboard";
        /**
         * 聊天消息
         */
        public static final String CHAT = "chat";
    }

    /**
     * 消息类别
     */
    interface Kind {
        String ROOT = "kind";
        String MSG_CONTROL = "msg_control";
        String MSG_DRAWING = "msg_drawing";
        String MSG_CHAT = "msg_chat";
    }

    /**
     * 消息类别对应的子类别
     */
    interface Type {
        String CONTROL_UPDATE = "ctl_update";
        String CTL_AUDIO_CHANGE = "ctl_audio_change";
        String CTL_VIDEO_CHANGE = "ctl_video_change";
        String CTL_NAME_CHANGE = "ctl_name_change";
        String CTL_ROLE_CHANGE = "ctl_role_change";
    }

    /**
     * 更新类型
     */
    interface UpdateType {
        String UPDATE_WHITEBOARD = "upd_whiteboard";
    }
}
