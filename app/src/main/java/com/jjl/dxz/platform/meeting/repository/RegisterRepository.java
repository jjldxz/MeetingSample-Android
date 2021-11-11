package com.jjl.dxz.platform.meeting.repository;

import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.net.BaseRepository;
import com.jjl.dxz.platform.meeting.bean.req.RegisterReq;
import com.jjl.dxz.platform.meeting.bean.resp.LoginResp;
import com.jjl.dxz.platform.meeting.bean.resp.VerifyUserNameResp;
import com.jjl.dxz.platform.meeting.service.UserService;

import java.util.Map;

public class RegisterRepository extends BaseRepository<UserService> {
    /**
     * 注册验证用户名是否被占用
     *
     * @param params     ["username":username]
     * @param subscriber 订阅
     */
    public void verifyUserName(Map<String, String> params, MyDisposableSubscriber<VerifyUserNameResp> subscriber) {
        addDisposable(mService.verifyUerName(params).subscribeWith(subscriber));
    }

    /**
     * 注册
     *
     * @param req        注册实体
     * @param subscriber 订阅
     */
    public void register(RegisterReq req, MyDisposableSubscriber<LoginResp> subscriber) {
        addDisposable(mService.register(req).subscribeWith(subscriber));
    }
}
