package com.jjl.dxz.platform.meeting.repository;

import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.net.BaseRepository;
import com.jjl.dxz.platform.meeting.bean.req.NewMeetingReq;
import com.jjl.dxz.platform.meeting.bean.resp.NewMeetingResp;
import com.jjl.dxz.platform.meeting.service.MeetingService;

public class AppointMeetingRepository extends BaseRepository<MeetingService> {

    public void newMeeting(NewMeetingReq req, MyDisposableSubscriber<NewMeetingResp> subscriber) {
        addDisposable(mService.newMeeting(req).subscribeWith(subscriber));
    }
}
