package com.jjl.dxz.platform.meeting.repository;

import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.net.BaseRepository;
import com.jjl.dxz.platform.meeting.bean.resp.LoginResp;
import com.jjl.dxz.platform.meeting.service.UserService;

import java.util.Map;

public class LoginRepository extends BaseRepository<UserService> {

    /**
     * 登录
     *
     * @param params     ["username":username,"password":pwd]
     * @param subscriber 订阅
     */
    public void login(Map<String, String> params, MyDisposableSubscriber<LoginResp> subscriber) {
        addDisposable(mService.login(params).subscribeWith(subscriber));
    }
}
