package com.jjl.dxz.platform.meeting.widget.whiteboard.bean;

public class Text extends WhiteBoardCommand {

    private String lineColor;
    private boolean finish;
    private int lineWidth;
    private StartDot startDot;
    private String content;
    //    文字的宽高
    private float textW;
    private float textH;

    public String getLineColor() {
        return lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public StartDot getStartDot() {
        return startDot;
    }

    public void setStartDot(StartDot startDot) {
        this.startDot = startDot;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getTextW() {
        return textW;
    }

    public void setTextW(float textW) {
        this.textW = textW;
    }

    public float getTextH() {
        return textH;
    }

    public void setTextH(float textH) {
        this.textH = textH;
    }

    public static class StartDot {

        private float x;
        private float y;

        public StartDot(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}
