package com.jjl.dxz.platform.meeting.repository;

import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.net.BaseRepository;
import com.jjl.dxz.platform.meeting.bean.resp.JoinMeetingResp;
import com.jjl.dxz.platform.meeting.bean.resp.MeetingInfoResp;
import com.jjl.dxz.platform.meeting.service.MeetingService;

import java.util.Map;

public class JoinMeetingRepository extends BaseRepository<MeetingService> {

    public void meetingInfo(int number, MyDisposableSubscriber<MeetingInfoResp> subscriber) {
        addDisposable(mService.meetingInfo(number).subscribeWith(subscriber));
    }

    public void joinMeeting(Map<String, Object> params, MyDisposableSubscriber<JoinMeetingResp> subscriber) {
        addDisposable(mService.join(params).subscribeWith(subscriber));
    }
}
