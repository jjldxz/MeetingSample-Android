package com.jjl.dxz.platform.meeting.vm;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.frame.BaseViewModel;
import com.jjl.dxz.platform.meeting.base.net.ErrorResp;
import com.jjl.dxz.platform.meeting.bean.resp.JoinMeetingResp;
import com.jjl.dxz.platform.meeting.bean.resp.MeetingInfoResp;
import com.jjl.dxz.platform.meeting.constant.BundleKey;
import com.jjl.dxz.platform.meeting.constant.ReqParamsKey;
import com.jjl.dxz.platform.meeting.constant.SpKey;
import com.jjl.dxz.platform.meeting.repository.JoinMeetingRepository;
import com.jjl.dxz.platform.meeting.ui.MeetingActivity;
import com.jjl.dxz.platform.meeting.util.SpUtils;
import com.jjl.dxz.platform.meeting.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

public class JoinMeetingViewModel extends BaseViewModel {

    private JoinMeetingRepository mRepo;
    public MutableLiveData<String> number = new MutableLiveData<>();
    public MutableLiveData<Boolean> openMic = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> openSpeaker = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> openCamera = new MutableLiveData<>(true);

    public JoinMeetingViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void init() {
        mRepo = new JoinMeetingRepository();
    }

    public void join() {
        if (number.getValue() == null || TextUtils.isEmpty(number.getValue().trim())) {
            ToastUtils.showShort(getContext(), R.string.please_input_meeting_number);
            return;
        }

        mRepo.meetingInfo(Integer.parseInt(number.getValue()), new MyDisposableSubscriber<MeetingInfoResp>() {
            @Override
            public void onBefore() {
                showProgress();
            }

            @Override
            public void onSuccess(MeetingInfoResp meetingInfoResp) {
                join(meetingInfoResp);
            }

            @Override
            public void onError(ErrorResp errorResp) {
                dismissProgress();
                ToastUtils.showShort(getContext(), errorResp.getCode() + ":" + errorResp.getMessage());
            }

            @Override
            public void onCompleted() {

            }
        });
    }

    private void join(MeetingInfoResp meetingInfoResp) {
        Map<String, Object> params = new HashMap<>();
        params.put(ReqParamsKey.NUMBER, meetingInfoResp.getNumber());
        mRepo.joinMeeting(params, new MyDisposableSubscriber<JoinMeetingResp>() {
            @Override
            public void onBefore() {
                showProgress();
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
                bundle.putBoolean(BundleKey.CLOSE_MIC, openMic.getValue() != null && !openMic.getValue());
                bundle.putBoolean(BundleKey.OPEN_RECEIVER, openSpeaker.getValue() != null && !openSpeaker.getValue());
                bundle.putBoolean(BundleKey.IS_OWNER, meetingInfoResp.getOwnerId() == SpUtils.getInt(SpKey.USER_ID, -1));
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
}
