package com.jjl.dxz.platform.meeting.widget.dialog;

import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.databinding.DialogTimePickerBinding;

import java.util.Calendar;
import java.util.Locale;

public class TimePickerDialog extends BottomSheetDialogFragment implements NumberPicker.Formatter {

    private DialogTimePickerBinding mBinding;
    private BottomSheetBehavior<FrameLayout> behavior;
    private final SparseIntArray beforeChooseTime;

    public TimePickerDialog(SparseIntArray beforeChooseTime) {
        this.beforeChooseTime = beforeChooseTime;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DialogTimePickerBinding.inflate(inflater, container, false);
        mBinding.setEvent(new Event());
        mBinding.dp.setMinDate(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        if (beforeChooseTime == null) {
            mBinding.dp.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), null);
        } else {
            mBinding.dp.init(
                    beforeChooseTime.get(0)/*年份*/,
                    beforeChooseTime.get(1)/*月份*/,
                    beforeChooseTime.get(2)/*天数*/,
                    null);
        }
        //小时选择器
        mBinding.npHour.setMinValue(0);//最小0时
        mBinding.npHour.setMaxValue(23);//最大23时
        mBinding.npHour.setFormatter(this);
        mBinding.npHour.setValue(beforeChooseTime == null ? calendar.get(Calendar.HOUR_OF_DAY) : beforeChooseTime.get(3)/*小时*/);
        //分钟选择器
        mBinding.npMinute.setMinValue(0);//最小0分
        mBinding.npMinute.setMaxValue(59);//最大59分
        mBinding.npMinute.setFormatter(this);
        mBinding.npMinute.setValue(beforeChooseTime == null ? calendar.get(Calendar.MINUTE) : beforeChooseTime.get(4)/*分钟*/);
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            FrameLayout frameLayout = getDialog().findViewById(R.id.design_bottom_sheet);
            behavior = BottomSheetBehavior.from(frameLayout);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });
        }
    }

    @Override
    public String format(int value) {
        String temp = String.valueOf(value);
        if (value < 10) {
            return "0" + temp;
        }
        return temp;
    }

    public class Event {
        public void cancel() {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }

        public void confirm() {
            if (onChooseDateListener != null) {
                SparseIntArray chooseTime = new SparseIntArray();
                chooseTime.put(0, mBinding.dp.getYear());
                chooseTime.put(1, mBinding.dp.getMonth());
                chooseTime.put(2, mBinding.dp.getDayOfMonth());
                chooseTime.put(3, mBinding.npHour.getValue());
                chooseTime.put(4, mBinding.npMinute.getValue());
                onChooseDateListener.onChooseDate(chooseTime);
            }
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    public interface OnChooseDateListener {
        void onChooseDate(SparseIntArray chooseTime);
    }

    private OnChooseDateListener onChooseDateListener;

    public void setOnChooseDateListener(OnChooseDateListener onChooseDateListener) {
        this.onChooseDateListener = onChooseDateListener;
    }
}
