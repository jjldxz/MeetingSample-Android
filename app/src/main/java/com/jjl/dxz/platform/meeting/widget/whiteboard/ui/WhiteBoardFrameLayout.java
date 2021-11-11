package com.jjl.dxz.platform.meeting.widget.whiteboard.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.widget.whiteboard.constant.WhiteBoardTool;

public class WhiteBoardFrameLayout extends FrameLayout {

    private WhiteboardView whiteBoardView;
    private int groupId;

    public WhiteBoardFrameLayout(Context context) {
        this(context, null);
    }

    public WhiteBoardFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WhiteBoardFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WhiteBoardFrameLayout);
            groupId = a.getInteger(R.styleable.WhiteBoardFrameLayout_group, -1);
            a.recycle();
        }
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.whiteboard_course_layout, this);
        whiteBoardView = findViewById(R.id.whiteboard_view);
        whiteBoardView.setGroupId(groupId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float viewWidth = getMeasuredWidth();
        float viewHeight = getMeasuredHeight();

        float courseWidth = 16;
        float courseHeight = 9;
        float parentHeight1 = viewWidth / courseWidth * courseHeight;

        float parentWidth2 = viewHeight / courseHeight * courseWidth;

        float parentWidth;
        float parentHeight;

        if (parentHeight1 > viewHeight) {
            parentWidth = parentWidth2;
            parentHeight = viewHeight;
        } else {
            parentWidth = viewWidth;
            parentHeight = parentHeight1;
        }

        //  必须测量子控件,确定子控件大小
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec((int) parentWidth, MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec((int) parentHeight, MeasureSpec.EXACTLY);
        measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
        if (getChildAt(0) != null) {
            getChildAt(0).scrollTo(0, 0);
        }
        // 测量
        setMeasuredDimension((int) parentWidth, (int) parentHeight);
    }

    public void selectTool(WhiteBoardTool tool) {
        whiteBoardView.selectTool(tool);
    }

    public void setPaintColor(String color) {
        whiteBoardView.setPaintColor(color);
    }

    public void setTypeface(Typeface typeface) {
        if (whiteBoardView != null) {
            whiteBoardView.setTypeface(typeface);
        }
    }

    public void revoke() {
        whiteBoardView.revoke();
    }

    public void clear() {
        whiteBoardView.clear();
    }

    public void setFontSize(int fontSize) {
        whiteBoardView.setFontSize(fontSize);
    }

    public void setText(String text) {
        whiteBoardView.setText(text);
    }
}
