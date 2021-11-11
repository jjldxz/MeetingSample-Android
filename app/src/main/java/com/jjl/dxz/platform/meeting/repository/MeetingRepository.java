package com.jjl.dxz.platform.meeting.repository;

import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.net.BaseRepository2;
import com.jjl.dxz.platform.meeting.bean.resp.BooleanResp;
import com.jjl.dxz.platform.meeting.bean.resp.GroupDetailResp;
import com.jjl.dxz.platform.meeting.service.GroupService;
import com.jjl.dxz.platform.meeting.service.MeetingService;

import java.util.List;
import java.util.Map;

public class MeetingRepository extends BaseRepository2<MeetingService, GroupService> {

    public void closeRoom(Map<String, Integer> params, MyDisposableSubscriber<BooleanResp> subscriber) {
        addDisposable(mService1.stop(params).subscribeWith(subscriber));
    }

    public void getGroupDetails(Map<String, Integer> params, MyDisposableSubscriber<GroupDetailResp> subscriber) {
        addDisposable(mService2.getDetail(params).subscribeWith(subscriber));
    }
}
