package com.jjl.dxz.platform.meeting.widget.dialog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jjl.dxz.platform.meeting.databinding.DialogWhiteboardTextBinding;

public class WhiteboardTextDialog extends BottomSheetDialogFragment {

    private DialogWhiteboardTextBinding mBinding;
    private boolean isClickConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DialogWhiteboardTextBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBinding.btnConfirm.setOnClickListener(v -> {
            isClickConfirm = true;
            Editable editable = mBinding.et.getText();
            if (onConfirmListener != null) {
                if (editable != null && !TextUtils.isEmpty(editable.toString().trim())) {
                    onConfirmListener.onConfirm(editable.toString());
                } else {
                    onConfirmListener.onNothing();
                }
            }
            dismiss();
        });
        if (getDialog() == null) {
            return;
        }
        getDialog().setOnDismissListener(dialog -> {
            if (!isClickConfirm && onConfirmListener != null) {
                onConfirmListener.onNothing();
            }
        });
    }

    public interface OnConfirmListener {
        void onConfirm(String text);

        void onNothing();
    }

    private OnConfirmListener onConfirmListener;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }
}
