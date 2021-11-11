package com.jjl.dxz.platform.meeting.service;


import com.jjl.dxz.platform.meeting.bean.req.GroupStartReq;
import com.jjl.dxz.platform.meeting.bean.req.GroupStopReq;
import com.jjl.dxz.platform.meeting.bean.req.MoveMemberReq;
import com.jjl.dxz.platform.meeting.bean.resp.BooleanResp;
import com.jjl.dxz.platform.meeting.bean.resp.GroupDetailResp;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GroupService {
    /**
     * 获取分组信息
     *
     * @param params ["number"(会议号):Integer]
     * @return 数据流
     */
    @POST("group/detail/")
    Flowable<GroupDetailResp> getDetail(@Body Map<String, Integer> params);

    /**
     * 移动组员到另一组
     *
     * @param req 请求实体
     * @return 数据流
     */
    @POST("group/move_member/")
    Flowable<BooleanResp> moveMember(@Body MoveMemberReq req);

    /**
     * 开始分组
     *
     * @param req 请求实体
     * @return 数据流
     */
    @POST("group/start/")
    Flowable<BooleanResp> start(@Body GroupStartReq req);

    /**
     * 停止分组
     *
     * @param req 请求实体
     * @return 数据流
     */
    @POST("group/stop/")
    Flowable<BooleanResp> stop(@Body GroupStopReq req);
}
