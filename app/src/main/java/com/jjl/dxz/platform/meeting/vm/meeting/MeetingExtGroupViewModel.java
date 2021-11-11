package com.jjl.dxz.platform.meeting.vm.meeting;

import android.app.Application;

import androidx.annotation.NonNull;

import com.jjl.dxz.platform.meeting.base.frame.BaseViewModel;
import com.jjl.dxz.platform.meeting.bean.resp.GroupDetailResp;
import com.jjl.dxz.platform.meeting.repository.MeetingRepository;

public class MeetingExtGroupViewModel extends BaseViewModel {

    protected MeetingRepository mMeetingRepo;
    protected GroupDetailResp groupDetail;
    protected int myGroupId = -1;

    public MeetingExtGroupViewModel(@NonNull Application application) {
        super(application);
        mMeetingRepo = new MeetingRepository();
    }

    @Override
    protected void init() {

    }
}
