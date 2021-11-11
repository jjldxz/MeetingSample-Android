package com.jjl.dxz.platform.meeting.widget.whiteboard.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Line;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Rect;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Revoke;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Round;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.StraightLine;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.Text;
import com.jjl.dxz.platform.meeting.widget.whiteboard.bean.WhiteBoardCommand;
import com.jjl.dxz.platform.meeting.widget.whiteboard.constant.WhiteBoardDrawType;
import com.jjl.dxz.platform.meeting.widget.whiteboard.constant.WhiteBoardTool;
import com.jjl.dxz.platform.meeting.widget.whiteboard.draw.DrawCircle;
import com.jjl.dxz.platform.meeting.widget.whiteboard.draw.DrawClear;
import com.jjl.dxz.platform.meeting.widget.whiteboard.draw.DrawLine;
import com.jjl.dxz.platform.meeting.widget.whiteboard.draw.DrawRect;
import com.jjl.dxz.platform.meeting.widget.whiteboard.draw.DrawRevoke;
import com.jjl.dxz.platform.meeting.widget.whiteboard.draw.DrawStraight;
import com.jjl.dxz.platform.meeting.widget.whiteboard.draw.DrawText;
import com.jjl.dxz.platform.meeting.widget.whiteboard.draw.WhiteBoardBean;
import com.jjl.dxz.platform.meeting.widget.whiteboard.manage.WhiteboardListener;
import com.jjl.dxz.platform.meeting.widget.whiteboard.manage.WhiteboardManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class WhiteboardView extends View implements WhiteboardListener {

    private final Map<String, WhiteBoardBean> drawSource = new ConcurrentHashMap<>();
    private final List<Map.Entry<String, WhiteBoardBean>> sortDrawSource = new ArrayList<>();
    private int groupId;
    private float width = 0;
    private float height = 0;
    private WhiteBoardTool whiteBoardTool = WhiteBoardTool.NONE;
    private String paintColor = "#FF0D19";

    private int fontSize = 4;
    private String text = "";

    private final Paint drawPaint;

    private final int defaultTextSize = 40;

    public WhiteboardView(Context context) {
        this(context, null);
    }

    public WhiteboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WhiteboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        drawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setDither(true);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setStrokeWidth(fontSize);
        drawPaint.setPathEffect(new CornerPathEffect(1));
        drawPaint.setTextSize(defaultTextSize);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WhiteboardManager.getInstance().removeWhiteBoardListener(groupId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    public void setTypeface(Typeface typeface) {
        drawPaint.setTypeface(typeface);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getMeasuredWidth();
        height = getMeasuredHeight();

        canvas.drawColor(getResources().getColor(android.R.color.transparent));

        for (Map.Entry<String, WhiteBoardBean> mapping : sortDrawSource) {
            try {
                WhiteBoardBean value = mapping.getValue();

                switch (value.getType()) {
                    case WhiteBoardDrawType.LINE:
                        drawLine(canvas, value.getLines());
                        break;
                    case WhiteBoardDrawType.STRAIGHT_LINE:
                        drawStraightLine(canvas, (StraightLine) value.getWhiteBoardCommon());
                        break;
                    case WhiteBoardDrawType.RECT:
                        drawRect(canvas, (Rect) value.getWhiteBoardCommon());
                        break;
                    case WhiteBoardDrawType.ROUND:
                        drawRound(canvas, (Round) value.getWhiteBoardCommon());
                        break;
                    case WhiteBoardDrawType.TEXT:
                        drawText(canvas, (Text) value.getWhiteBoardCommon());
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void drawLine(Canvas canvas, List<Line> lines) {

        float startX = 0;
        float startY = 0;

        if (lines != null && !lines.isEmpty()) {
            for (int i = 0; i < lines.size(); i++) {
                Line line = lines.get(i);
                setColor(line.getLineColor());
                drawPaint.setStrokeWidth(line.getLineWidth());

                List<Line.Point> points = line.getPoints();
                if (points != null && !points.isEmpty()) {
                    if (points.size() == 1) {
                        Line.Point linePoint = points.get(0);
                        float stopX = linePoint.getX() * width;
                        float stopY = linePoint.getY() * height;
                        if (i > 0) {//自己绘制的时候 points一直等于1  需要判断i>0的时候在连接线段
                            canvas.drawLine(startX, startY, stopX, stopY, drawPaint);
                        }
                        startX = stopX;
                        startY = stopY;
                    } else {
                        for (int j = 0; j < points.size(); j++) {
                            float stopX = points.get(j).getX() * width;
                            float stopY = points.get(j).getY() * height;
                            if (j > 0) {
                                canvas.drawLine(startX, startY, stopX, stopY, drawPaint);
                            }
                            startX = stopX;
                            startY = stopY;
                        }
                    }
                }
            }
        }
    }

    private void drawStraightLine(Canvas canvas, StraightLine line) {
        if (line != null) {
            setColor(line.getLineColor());
            drawPaint.setStrokeWidth(line.getLineWidth());
            canvas.drawLine(line.getStartDot().getX() * width, line.getStartDot().getY() * height, line.getEndDot().getX() * width, line.getEndDot().getY() * height, drawPaint);
        }
    }

    private void drawRound(Canvas canvas, Round round) {
        if (round != null) {
            setColor(round.getLineColor());
            drawPaint.setStrokeWidth(round.getLineWidth());
            drawPaint.setStyle(Paint.Style.STROKE);

            Round.StartDot startDot = round.getStartDot();
            Round.EndDot endDot = round.getEndDot();

            float startX = startDot.getX() * width;
            float startY = startDot.getY() * height;
            float endX = endDot.getX() * width;
            float endY = endDot.getY() * height;
            float DX = endX - startX;
            float DY = endY - startY;
            float r = (float) Math.sqrt(DX * DX + DY * DY);
            canvas.drawCircle(startX, startY, r, drawPaint);
        }
    }

    private void drawRect(Canvas canvas, Rect rect) {
        if (rect != null) {
            setColor(rect.getLineColor());
            drawPaint.setStrokeWidth(rect.getLineWidth());
            drawPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(rect.getStartDot().getX() * width, rect.getStartDot().getY() * height, rect.getEndDot().getX() * width, rect.getEndDot().getY() * height, drawPaint);
        }
    }

    private void drawText(Canvas canvas, Text text) {
        if (text != null) {
            setColor(text.getLineColor());
            drawPaint.setStrokeWidth(2);
            drawPaint.setStyle(Paint.Style.FILL);//实心矩形框
            Text.StartDot startDot = text.getStartDot();

            float startX = startDot.getX() * width;
            float startY = startDot.getY() * height;

            float realW = text.getTextW() * width;

            int size = 1;
            String content = text.getContent();
            if (TextUtils.isEmpty(content)) {
                return;
            }
            android.graphics.Rect rect = new android.graphics.Rect();
            while (true) {
                drawPaint.setTextSize(size++);
                drawPaint.getTextBounds(content, 0, content.length(), rect);
                float w = rect.width();
                if (w >= realW) {
                    break;
                }
            }

            android.graphics.Rect bounds = new android.graphics.Rect();
            drawPaint.getTextBounds(content, 0, content.length(), bounds);
            canvas.drawText(content, startX, startY, drawPaint);
        }
        drawPaint.setTextSize(defaultTextSize);
    }

    private void setColor(String color) {
        try {
            drawPaint.setColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
            //默认颜色值-黑色
            drawPaint.setColor(getResources().getColor(android.R.color.black));
        }
    }

    private int eventPointId = -1;
    private final PointF point = new PointF(0, 0);
    private boolean havePoint = false;

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!WhiteBoardTool.NONE.equals(whiteBoardTool)) {
            int index = event.getActionIndex();
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (havePoint) {
                        return true;
                    }
                    eventPointId = event.getPointerId(index);
                    point.set(event.getX(index), event.getY(index));

                    havePoint = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (havePoint) {
                        int findNum = -1;
                        for (int i = 0; i < event.getPointerCount(); i++) {
                            if (eventPointId == event.getPointerId(i)) {
                                findNum = i;
                                break;
                            }
                        }

                        if (findNum != -1) {
                            point.set(event.getX(findNum), event.getY(findNum));
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    if (event.getPointerId(index) == eventPointId) {
                        eventPointId = -1;
                        havePoint = false;
                    } else {
                        havePoint = false;
                        return true;
                    }
            }

            switch (whiteBoardTool) {
                case PENCIL:
                    DrawLine.draw(paintColor, fontSize, this.width, this.height, event, point, groupId);
                    break;
                case STRAIGHT:
                    DrawStraight.draw(paintColor, fontSize, this.width, this.height, event, point, groupId);
                    break;
                case RECT:
                    DrawRect.draw(paintColor, fontSize, this.width, this.height, event, point, groupId);
                    break;
                case CIRCLE:
                    DrawCircle.draw(paintColor, fontSize, this.width, this.height, event, point, groupId);
                    break;
                case TEXT:
                    android.graphics.Rect rect = new android.graphics.Rect();
                    drawPaint.getTextBounds(text, 0, text.length(), rect);
                    float textW = rect.width();
                    Paint.FontMetrics fontMetrics = drawPaint.getFontMetrics();
                    float textH = fontMetrics.bottom - fontMetrics.top;
                    DrawText.draw(paintColor, fontSize, this.width, this.height, text, textW, textH, event, point, groupId);
                    break;
            }
            return true;
        } else {
            return performClick();
        }
    }

    public void selectTool(WhiteBoardTool tool) {
        this.whiteBoardTool = tool;
    }

    public void setPaintColor(String color) {
        this.paintColor = color;
    }

    public void revoke() {
        if (sortDrawSource.size() == 0) {
            return;
        }
        sortWhiteBoardBean();
        Map.Entry<String, WhiteBoardBean> item = sortDrawSource.remove(sortDrawSource.size() - 1);
        WhiteBoardBean boardBean = item.getValue();
        DrawRevoke.revoke(boardBean.getWhiteBoardCommon().getId(), groupId);
    }

    public void clear() {
        if (sortDrawSource.size() == 0) {
            return;
        }
        DrawClear.clear(groupId);
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void onDrawLine(int groupId, Line line) {
        if (drawSource.containsKey(line.getId())) {
            WhiteBoardBean boardBean = drawSource.get(line.getId());
            Objects.requireNonNull(boardBean).getLines().add(line);
        } else {
            WhiteBoardBean boardBean = new WhiteBoardBean(line.getType(), line, new ArrayList<>());
            boardBean.getLines().add(line);
            drawSource.put(line.getId(), boardBean);
        }
        sortWhiteBoardBean();
        postInvalidate();
    }

    @Override
    public void onDrawStraightLine(int groupId, StraightLine straightLine) {
        addWhiteBoardBeanAndRefresh(straightLine);
    }

    @Override
    public void onDrawRect(int groupId, Rect rect) {
        addWhiteBoardBeanAndRefresh(rect);
    }

    @Override
    public void onDrawRound(int groupId, Round round) {
        addWhiteBoardBeanAndRefresh(round);
    }

    @Override
    public void onDrawText(int groupId, Text text) {
        addWhiteBoardBeanAndRefresh(text);
    }

    @Override
    public void onClear(int groupId) {
        drawSource.clear();
        sortDrawSource.clear();
        postInvalidate();
    }

    @Override
    public void onRevoke(int groupId, Revoke revoke) {
        drawSource.remove(revoke.getTargetId());
        postInvalidate();
    }

    private void addWhiteBoardBeanAndRefresh(WhiteBoardCommand whiteBoardCommon) {
        WhiteBoardBean boardBean = new WhiteBoardBean(whiteBoardCommon.getType(), whiteBoardCommon);
        drawSource.put(whiteBoardCommon.getId(), boardBean);
        sortWhiteBoardBean();
        postInvalidate();
    }

    private void sortWhiteBoardBean() {
        sortDrawSource.clear();
        sortDrawSource.addAll(drawSource.entrySet());
        Collections.sort(sortDrawSource, (v1, v2) -> Long.compare(v1.getValue().getWhiteBoardCommon().getTime(), v2.getValue().getWhiteBoardCommon().getTime()));
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
        WhiteboardManager.getInstance().addWhiteBoardListener(groupId, this);
    }
}
