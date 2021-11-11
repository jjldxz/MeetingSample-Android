package com.jjl.dxz.platform.meeting.repository;

import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.net.BaseRepository;
import com.jjl.dxz.platform.meeting.bean.resp.JoinMeetingResp;
import com.jjl.dxz.platform.meeting.bean.resp.MeetingInfoResp;
import com.jjl.dxz.platform.meeting.bean.resp.MeetingListResp;
import com.jjl.dxz.platform.meeting.service.MeetingService;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public class MainRepository extends BaseRepository<MeetingService> {

    /**
     * 获取会议列表
     *
     * @param beginAt    开始时间
     * @param endAt      结束时间
     * @param subscriber 订阅
     */
    public void getMeetingList(String beginAt, String endAt, MyDisposableSubscriber<List<MeetingListResp>> subscriber) {
        addDisposable(mService.getList(beginAt, endAt).subscribeWith(subscriber));
    }

    public void meetingInfo(int number, MyDisposableSubscriber<MeetingInfoResp> subscriber) {
        addDisposable(mService.meetingInfo(number).subscribeWith(subscriber));
    }

    public void joinMeeting(Map<String, Object> params, MyDisposableSubscriber<JoinMeetingResp> subscriber) {
        addDisposable(mService.join(params).subscribeWith(subscriber));
    }
}
