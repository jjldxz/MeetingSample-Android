package com.jjl.dxz.platform.meeting.service;

import com.jjl.dxz.platform.meeting.bean.req.RegisterReq;
import com.jjl.dxz.platform.meeting.bean.resp.LoginResp;
import com.jjl.dxz.platform.meeting.bean.resp.VerifyUserNameResp;

import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    /**
     * 登录
     *
     * @param params ["username":String,
     *               "password":String]
     * @return 数据流
     */
    @POST("common/login/")
    Flowable<LoginResp> login(@Body Map<String, String> params);

    /**
     * 注册验证用户名是否被占用
     *
     * @param params ["username":String]
     * @return 数据流
     */
    @POST("common/verify_username/")
    Flowable<VerifyUserNameResp> verifyUerName(@Body Map<String, String> params);

    /**
     * 用户注册
     *
     * @param req 请求实体
     * @return 数据流
     */
    @POST("common/register/")
    Flowable<LoginResp> register(@Body RegisterReq req);
}
