package com.jjl.dxz.platform.meeting.repository;

import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.net.BaseRepository;
import com.jjl.dxz.platform.meeting.bean.resp.PollResp;
import com.jjl.dxz.platform.meeting.service.PollService;

import java.util.List;

public class PollRepository extends BaseRepository<PollService> {

    public void getPollList(int meetingNumber, MyDisposableSubscriber<List<PollResp>> subscriber) {
        addDisposable(mService.getPolls(meetingNumber).subscribeWith(subscriber));
    }
}
