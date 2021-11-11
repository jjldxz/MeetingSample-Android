package com.jjl.dxz.platform.meeting.widget.whiteboard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.databinding.ViewMyWhiteboardBinding;
import com.jjl.dxz.platform.meeting.widget.dialog.ColorPickerDialog;
import com.jjl.dxz.platform.meeting.widget.dialog.WhiteboardTextDialog;
import com.jjl.dxz.platform.meeting.widget.whiteboard.constant.WhiteBoardTool;

public class MyWhiteBoard extends RelativeLayout {

    private final ViewMyWhiteboardBinding mBinding;

    public MyWhiteBoard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWhiteBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyWhiteBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mBinding = ViewMyWhiteboardBinding.inflate(LayoutInflater.from(getContext()), this, true);
        mBinding.whiteboard.setPaintColor("#000000");
        mBinding.setEvent(new Event());
    }

    public class Event {
        public void selectTool(WhiteBoardTool tool) {
            refreshTools(tool);
        }

        public void revoke() {
            refreshTools(WhiteBoardTool.NONE);
            mBinding.whiteboard.revoke();
        }

        public void clear() {
            refreshTools(WhiteBoardTool.NONE);
            mBinding.whiteboard.clear();
        }

        public void color() {
            ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
            colorPickerDialog.setOnColorListener(color -> {
                mBinding.ivColor.setBackgroundColor(Color.parseColor(color));
                mBinding.whiteboard.setPaintColor(color);
            });
            colorPickerDialog.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "colorPicker");
        }

        public void close() {
            if (onCloseListener != null) {
                onCloseListener.onClose();
            }
        }
    }

    private void refreshTools(WhiteBoardTool tool) {
        mBinding.whiteboard.selectTool(tool);
        mBinding.ivPencil.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        mBinding.ivLine.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        mBinding.ivRect.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        mBinding.ivRing.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        mBinding.ivText.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        if (tool.equals(WhiteBoardTool.NONE)) {
            return;
        }
        switch (tool) {
            case PENCIL:
                mBinding.ivPencil.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue_316CE1)));
                break;
            case STRAIGHT:
                mBinding.ivLine.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue_316CE1)));
                break;
            case RECT:
                mBinding.ivRect.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue_316CE1)));
                break;
            case CIRCLE:
                mBinding.ivRing.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue_316CE1)));
                break;
            case TEXT:
                mBinding.ivText.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue_316CE1)));
                WhiteboardTextDialog whiteboardTextDialog = new WhiteboardTextDialog();
                whiteboardTextDialog.setOnConfirmListener(new WhiteboardTextDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        mBinding.whiteboard.setText(text);
                    }

                    @Override
                    public void onNothing() {
                        refreshTools(WhiteBoardTool.NONE);
                        mBinding.ivText.setImageTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.white)));
                    }
                });
                whiteboardTextDialog.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "whiteboardText");
                break;
        }
    }

    public interface OnCloseListener {
        void onClose();
    }

    private OnCloseListener onCloseListener;

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }
}
