package com.jjl.dxz.platform.meeting.widget.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jjl.dxz.platform.meeting.databinding.DialogColorPickerBinding;

public class ColorPickerDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogColorPickerBinding mBinding = DialogColorPickerBinding.inflate(inflater, container, false);
        mBinding.setEvent(new Event());
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public class Event {
        public void color(String color) {
            if (onColorListener != null) {
                onColorListener.onColor(color);
            }
            dismiss();
        }
    }

    public interface OnColorListener {
        void onColor(String color);
    }

    private OnColorListener onColorListener;

    public void setOnColorListener(OnColorListener onColorListener) {
        this.onColorListener = onColorListener;
    }
}
