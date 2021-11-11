package com.jjl.dxz.platform.meeting.ui;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

import com.jjl.dxz.platform.meeting.BR;
import com.jjl.dxz.platform.meeting.base.frame.BaseActivity;
import com.jjl.dxz.platform.meeting.databinding.ActivityAppointMeetingBinding;
import com.jjl.dxz.platform.meeting.vm.AppointMeetingViewModel;
import com.jjl.dxz.platform.meeting.widget.dialog.MeetingDurationChoiceDialog;
import com.jjl.dxz.platform.meeting.widget.dialog.TimePickerDialog;

public class AppointMeetingActivity extends BaseActivity<ActivityAppointMeetingBinding, AppointMeetingViewModel> {

    @Override
    protected int getViewModelVariable() {
        return BR.vm;
    }

    @Override
    protected LifecycleOwner getLifecycleOwner() {
        return this;
    }

    @Override
    protected ViewModelStoreOwner getViewModelStoreOwner() {
        return this;
    }

    @Override
    protected void init() {
        mBinding.setEvent(new Event());
    }

    public class Event {
        public void beginTime() {
            TimePickerDialog timePickerDialog = new TimePickerDialog(mViewModel.getChooseBeginTime());
            timePickerDialog.setOnChooseDateListener(chooseTime -> mViewModel.setChooseBeginTime(chooseTime));
            timePickerDialog.show(getSupportFragmentManager(), "timePicker");
        }

        public void meetingDuration() {
            MeetingDurationChoiceDialog durationChoiceDialog = new MeetingDurationChoiceDialog(mViewModel.getDuration());
            durationChoiceDialog.setOnChoiceDurationListener(duration -> mViewModel.setDuration(duration));
            durationChoiceDialog.show(getSupportFragmentManager(), "duration");
        }
    }
}