package com.jjl.dxz.platform.meeting.vm;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.frame.BaseViewModel;
import com.jjl.dxz.platform.meeting.base.net.ErrorResp;
import com.jjl.dxz.platform.meeting.bean.req.NewMeetingReq;
import com.jjl.dxz.platform.meeting.bean.resp.JoinMeetingResp;
import com.jjl.dxz.platform.meeting.bean.resp.NewMeetingResp;
import com.jjl.dxz.platform.meeting.constant.BundleKey;
import com.jjl.dxz.platform.meeting.constant.ReqParamsKey;
import com.jjl.dxz.platform.meeting.constant.SpKey;
import com.jjl.dxz.platform.meeting.repository.QuickMeetingRepository;
import com.jjl.dxz.platform.meeting.ui.MeetingActivity;
import com.jjl.dxz.platform.meeting.util.SpUtils;
import com.jjl.dxz.platform.meeting.util.TimeUtils;
import com.jjl.dxz.platform.meeting.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

public class QuickMeetingViewModel extends BaseViewModel {

    private QuickMeetingRepository mRepo;
    public MutableLiveData<Boolean> openCamera = new MutableLiveData<>(true);
    public MutableLiveData<Integer> meetingDuration = new MutableLiveData<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E HH:mm", Locale.getDefault());
    /**
     * 会议默认时长120
     */
    private int duration = 120;

    public QuickMeetingViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void init() {
        mRepo = new QuickMeetingRepository();
        meetingDuration.setValue(duration);
    }

    public void createAndJoin() {
        NewMeetingReq req = new NewMeetingReq();
        req.setName(getString(R.string.placeholder_meeting_name, SpUtils.getInt(SpKey.USER_ID, 0)));
        Date nowDate = new Date();
        req.setBeginAt(TimeUtils.getUtcTime(nowDate));
        long endTime = (long) duration * 60 * 1000 + nowDate.getTime();
        Date endDate = new Date(endTime);
        req.setEndAt(TimeUtils.getUtcTime(endDate));
        mRepo.newMeeting(req, new MyDisposableSubscriber<NewMeetingResp>() {
            @Override
            public void onBefore() {
                showProgress();
            }

            @Override
            public void onSuccess(NewMeetingResp newMeetingResp) {
                join(newMeetingResp);
            }

            @Override
            public void onError(ErrorResp errorResp) {
                ToastUtils.showShort(getContext(), errorResp.getCode() + ":" + errorResp.getMessage());
                dismissProgress();
            }

            @Override
            public void onCompleted() {

            }
        });
    }

    private void join(NewMeetingResp newMeetingResp) {
        Map<String, Object> params = new HashMap<>();
        params.put(ReqParamsKey.NUMBER, Integer.parseInt(newMeetingResp.getNumber()));
        mRepo.joinMeeting(params, new MyDisposableSubscriber<JoinMeetingResp>() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onSuccess(JoinMeetingResp joinMeetingResp) {
                Bundle bundle = new Bundle();
                bundle.putString(BundleKey.MEETING_TOKEN, joinMeetingResp.getToken());
                bundle.putString(BundleKey.APP_KEY, joinMeetingResp.getAppKey());
                bundle.putInt(BundleKey.ROOM_ID, joinMeetingResp.getRoomId());
                bundle.putInt(BundleKey.SHARE_USER_ID, joinMeetingResp.getShareUserId());
                bundle.putString(BundleKey.SHARE_USER_TOKEN, joinMeetingResp.getShareUserToken());
                bundle.putBoolean(BundleKey.IS_BREAKOUT, joinMeetingResp.isBreakout());
                bundle.putBoolean(BundleKey.CLOSE_CAMERA, openCamera.getValue() != null && !openCamera.getValue());
                startActivity(MeetingActivity.class, bundle);
                finish();
            }

            @Override
            public void onError(ErrorResp errorResp) {
                ToastUtils.showShort(getContext(), errorResp.getCode() + ":" + errorResp.getMessage());
            }

            @Override
            public void onCompleted() {
                dismissProgress();
            }
        });
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        meetingDuration.setValue(duration);
    }
}
