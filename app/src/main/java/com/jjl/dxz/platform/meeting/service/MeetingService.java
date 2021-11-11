package com.jjl.dxz.platform.meeting.service;

import com.jjl.dxz.platform.meeting.bean.req.NewMeetingReq;
import com.jjl.dxz.platform.meeting.bean.resp.BooleanResp;
import com.jjl.dxz.platform.meeting.bean.resp.JoinMeetingResp;
import com.jjl.dxz.platform.meeting.bean.resp.MeetingInfoResp;
import com.jjl.dxz.platform.meeting.bean.resp.MeetingListResp;
import com.jjl.dxz.platform.meeting.bean.resp.NewMeetingResp;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MeetingService {

    /**
     * 获取会议列表
     *
     * @param beginAt 开始时间
     * @param endAt   结束时间
     * @return 数据流
     */
    @GET("meeting/list/")
    Flowable<List<MeetingListResp>> getList(@Query("beginAt") String beginAt, @Query("endAt") String endAt);

    /**
     * 创建会议
     *
     * @param number 会议号
     * @return 数据流
     */
    @GET("meeting/info/")
    Flowable<MeetingInfoResp> meetingInfo(@Query("number") int number);

    /**
     * 创建会议
     *
     * @param req 参数实体
     * @return 数据流
     */
    @POST("meeting/new/")
    Flowable<NewMeetingResp> newMeeting(@Body NewMeetingReq req);

    /**
     * 加入会议
     *
     * @param params ["number":Integer,
     *               "password":String(Not required)]
     * @return 数据流
     */
    @POST("meeting/join/")
    Flowable<JoinMeetingResp> join(@Body Map<String, Object> params);

    /**
     * 停止会议
     *
     * @param params ["number":Integer]
     * @return 数据流
     */
    @POST("meeting/stop/")
    Flowable<BooleanResp> stop(@Body Map<String, Integer> params);

    /**
     * 开始分享
     *
     * @param params ["number":Integer]
     * @return 数据流
     */
    @POST("meeting/start_share/")
    Flowable<BooleanResp> startShare(@Body Map<String, Integer> params);

    /**
     * 停止分享
     *
     * @param params ["number":Integer]
     * @return 数据流
     */
    @POST("meeting/stop_share/")
    Flowable<BooleanResp> stopShare(@Body Map<String, Integer> params);
}
