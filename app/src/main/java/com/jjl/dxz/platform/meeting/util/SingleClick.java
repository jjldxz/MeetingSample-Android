package com.jjl.dxz.platform.meeting.util;

public class SingleClick {

    private static long firstTime;
    private static final int INTERVAL = 500;

    /**
     * 防连点 间隔500毫秒
     *
     * @return true:未连点, false:已连点
     */
    public static boolean isSingleClick() {
        if (System.currentTimeMillis() - firstTime >= INTERVAL) {
            firstTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
