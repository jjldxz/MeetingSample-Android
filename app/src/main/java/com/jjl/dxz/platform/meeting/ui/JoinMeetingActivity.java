package com.jjl.dxz.platform.meeting.ui;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

import com.jjl.dxz.platform.meeting.BR;
import com.jjl.dxz.platform.meeting.base.frame.BaseActivity;
import com.jjl.dxz.platform.meeting.databinding.ActivityJoinMeetingBinding;
import com.jjl.dxz.platform.meeting.vm.JoinMeetingViewModel;

public class JoinMeetingActivity extends BaseActivity<ActivityJoinMeetingBinding, JoinMeetingViewModel> {

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

    }
}