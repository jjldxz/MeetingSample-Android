package com.jjl.dxz.platform.meeting.service;

import com.jjl.dxz.platform.meeting.bean.req.CreatePollReq;
import com.jjl.dxz.platform.meeting.bean.req.PollCommitReq;
import com.jjl.dxz.platform.meeting.bean.req.PollUpdateReq;
import com.jjl.dxz.platform.meeting.bean.resp.PollAnswerResp;
import com.jjl.dxz.platform.meeting.bean.resp.PollDetailResp;
import com.jjl.dxz.platform.meeting.bean.resp.PollResp;
import com.jjl.dxz.platform.meeting.bean.resp.PollResultResp;
import com.jjl.dxz.platform.meeting.bean.resp.PollStartOrStopResp;
import com.jjl.dxz.platform.meeting.constant.Constants;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PollService {

    /**
     * 获取投票答案
     *
     * @param pollId 投票id
     * @return 数据流
     */
    @GET("poll/answer/")
    Flowable<PollAnswerResp> getAnswer(@Query("id") int pollId);

    /**
     * 提交投票
     *
     * @param req 提交投票请求实体
     * @return 数据流
     */
    @POST("poll/commit/")
    Flowable<PollAnswerResp> commit(@Body PollCommitReq req);

    /**
     * 删除投票
     *
     * @param params 删除投票 ["id":投票id]
     * @return 数据流
     */
    @POST("poll/commit/")
    Flowable<Void> delete(@Body Map<String, Integer> params);

    /**
     * 获取投票详情
     *
     * @param pollId 投票id
     * @return 数据流
     */
    @GET("poll/detail/")
    Flowable<PollDetailResp> getDetail(@Query("id ") int pollId);

    /**
     * 获取投票列表
     *
     * @param meetingNumber 会议号
     * @return 数据流
     */
    @GET("poll/list/")
    Flowable<List<PollResp>> getPolls(@Query("number") int meetingNumber);

    /**
     * 创建投票
     *
     * @param req 创建投票实体
     * @return 数据流
     */
    @POST("poll/new/")
    Flowable<PollResp> create(@Body CreatePollReq req);

    /**
     * 获取投票结果
     *
     * @param voteId 投票id
     * @return 数据流
     */
    @GET("poll/result/")
    Flowable<PollResultResp> getResult(@Query("id ") int voteId);

    /**
     * 更改投票分享状态
     *
     * @param params ["id":投票id(integer),
     *               "share":true/false]
     * @return 数据流
     */
    @POST("poll/share/")
    Flowable<PollResp> share(@Body Map<String, Object> params);

    /**
     * 开始投票
     *
     * @param params ["id":投票id]
     * @return 数据流
     */
    @POST("poll/start/")
    Flowable<PollStartOrStopResp> start(@Body Map<String, Integer> params);

    /**
     * 结束投票
     *
     * @param params ["id":投票id]
     * @return 数据流
     */
    @POST("poll/stop/")
    Flowable<PollStartOrStopResp> stop(@Body Map<String, Integer> params);

    /**
     * 更新投票
     *
     * @param req 更新投票实体
     * @return 数据流
     */
    @POST("poll/stop/")
    Flowable<PollStartOrStopResp> update(@Body PollUpdateReq req);
}
