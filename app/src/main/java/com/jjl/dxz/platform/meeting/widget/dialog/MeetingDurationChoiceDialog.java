package com.jjl.dxz.platform.meeting.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jjl.dxz.platform.meeting.R;

public class MeetingDurationChoiceDialog extends DialogFragment {

    /**
     * 会议时长
     */
    private final int beforeDuration;
    private int choice;

    public MeetingDurationChoiceDialog(int beforeDuration) {
        this.beforeDuration = beforeDuration;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        int[] durations = getResources().getIntArray(R.array.meeting_duration_value);
        for (int i = 0; i < durations.length; i++) {
            if (durations[i] == beforeDuration) {
                choice = i;
                break;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.choice_meeting_duration);
        builder.setSingleChoiceItems(R.array.meeting_duration_desc, choice, (dialog, which) -> choice = which)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    if (onChoiceDurationListener != null) {
                        onChoiceDurationListener.onChoiceDuration(durations[choice]);
                    }
                }).setNegativeButton(R.string.cancel, null);
        return builder.create();
    }

    public interface OnChoiceDurationListener {
        void onChoiceDuration(int duration);
    }

    private OnChoiceDurationListener onChoiceDurationListener;

    public void setOnChoiceDurationListener(OnChoiceDurationListener onChoiceDurationListener) {
        this.onChoiceDurationListener = onChoiceDurationListener;
    }
}
