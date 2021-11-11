package com.jjl.dxz.platform.meeting.repository;

import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.net.BaseRepository;
import com.jjl.dxz.platform.meeting.bean.req.NewMeetingReq;
import com.jjl.dxz.platform.meeting.bean.resp.JoinMeetingResp;
import com.jjl.dxz.platform.meeting.bean.resp.NewMeetingResp;
import com.jjl.dxz.platform.meeting.service.MeetingService;

import java.util.Map;

public class QuickMeetingRepository extends BaseRepository<MeetingService> {

    public void newMeeting(NewMeetingReq req, MyDisposableSubscriber<NewMeetingResp> subscriber) {
        addDisposable(mService.newMeeting(req).subscribeWith(subscriber));
    }

    public void joinMeeting(Map<String, Object> params, MyDisposableSubscriber<JoinMeetingResp> subscriber) {
        addDisposable(mService.join(params).subscribeWith(subscriber));
    }
}
