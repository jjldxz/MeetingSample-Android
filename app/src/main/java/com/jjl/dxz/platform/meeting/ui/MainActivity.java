package com.jjl.dxz.platform.meeting.ui;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

import com.jjl.dxz.platform.meeting.BR;
import com.jjl.dxz.platform.meeting.adapter.MeetingListAdapter;
import com.jjl.dxz.platform.meeting.base.frame.BaseActivity;
import com.jjl.dxz.platform.meeting.databinding.ActivityMainBinding;
import com.jjl.dxz.platform.meeting.vm.MainViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private MeetingListAdapter meetingListAdapter;

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
        meetingListAdapter = new MeetingListAdapter();
        mBinding.rvMeetingList.setAdapter(meetingListAdapter);
        meetingListAdapter.setOnItemClickListener(mViewModel::joinMeeting);
        mViewModel.getMeetingList().observe(this, items -> meetingListAdapter.submit(items));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mViewModel.refreshMeeting();
    }
}