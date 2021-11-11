package com.jjl.dxz.platform.meeting.repository;

import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.net.BaseRepository;
import com.jjl.dxz.platform.meeting.bean.resp.GroupDetailResp;
import com.jjl.dxz.platform.meeting.service.GroupService;

import java.util.Map;

public class GroupDetailRepository extends BaseRepository<GroupService> {

    public void getGroupDetails(Map<String, Integer> params, MyDisposableSubscriber<GroupDetailResp> subscriber) {
        addDisposable(mService.getDetail(params).subscribeWith(subscriber));
    }
}
