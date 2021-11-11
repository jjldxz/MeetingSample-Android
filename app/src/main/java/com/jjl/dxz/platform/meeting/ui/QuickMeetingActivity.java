package com.jjl.dxz.platform.meeting.ui;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

import com.jjl.dxz.platform.meeting.BR;
import com.jjl.dxz.platform.meeting.base.frame.BaseActivity;
import com.jjl.dxz.platform.meeting.databinding.ActivityQuickMeetingBinding;
import com.jjl.dxz.platform.meeting.vm.QuickMeetingViewModel;
import com.jjl.dxz.platform.meeting.widget.dialog.MeetingDurationChoiceDialog;

public class QuickMeetingActivity extends BaseActivity<ActivityQuickMeetingBinding, QuickMeetingViewModel> {

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
        public void meetingDuration() {
            MeetingDurationChoiceDialog durationChoiceDialog = new MeetingDurationChoiceDialog(mViewModel.getDuration());
            durationChoiceDialog.setOnChoiceDurationListener(duration -> mViewModel.setDuration(duration));
            durationChoiceDialog.show(getSupportFragmentManager(), "duration");
        }
    }
}