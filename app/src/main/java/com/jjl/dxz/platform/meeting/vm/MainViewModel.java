package com.jjl.dxz.platform.meeting.vm;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.frame.BaseViewModel;
import com.jjl.dxz.platform.meeting.base.net.ErrorResp;
import com.jjl.dxz.platform.meeting.bean.resp.JoinMeetingResp;
import com.jjl.dxz.platform.meeting.bean.resp.MeetingInfoResp;
import com.jjl.dxz.platform.meeting.bean.resp.MeetingListResp;
import com.jjl.dxz.platform.meeting.constant.BundleKey;
import com.jjl.dxz.platform.meeting.constant.ReqParamsKey;
import com.jjl.dxz.platform.meeting.constant.SpKey;
import com.jjl.dxz.platform.meeting.repository.MainRepository;
import com.jjl.dxz.platform.meeting.ui.AppointMeetingActivity;
import com.jjl.dxz.platform.meeting.ui.JoinMeetingActivity;
import com.jjl.dxz.platform.meeting.ui.MeetingActivity;
import com.jjl.dxz.platform.meeting.ui.QuickMeetingActivity;
import com.jjl.dxz.platform.meeting.util.SingleClick;
import com.jjl.dxz.platform.meeting.util.SpUtils;
import com.jjl.dxz.platform.meeting.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainViewModel extends BaseViewModel {

    private MainRepository mainRepo;
    private final MutableLiveData<List<MeetingListResp>> meetingList = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void init() {
        mainRepo = new MainRepository();
        refreshMeeting();
    }

    public void refreshMeeting() {
//        mainRepo.getMeetingList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Meeting>>() {
//            @Override
//            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Meeting> meetings) {
//                meetingList.setValue(meetings);
//            }
//
//            @Override
//            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//
//            }
//        });
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = sdf.format(new Date());

        mainRepo.getMeetingList(today + "T00:00:00Z", today + "T23:59:59Z", new MyDisposableSubscriber<List<MeetingListResp>>() {
            @Override
            public void onBefore() {
                showProgress();
            }

            @Override
            public void onSuccess(List<MeetingListResp> meetingListResp) {
                meetingList.setValue(meetingListResp);
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

    public void joinMeeting(MeetingListResp meetingListResp) {

        mainRepo.meetingInfo(meetingListResp.getNumber(), new MyDisposableSubscriber<MeetingInfoResp>() {
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
                ToastUtils.showShort(getContext(), errorResp + ":" + errorResp.getMessage());
                dismissProgress();
            }

            @Override
            public void onCompleted() {

            }
        });
    }

    private void join(MeetingInfoResp meetingInfoResp) {
        Map<String, Object> params = new HashMap<>();
        params.put(ReqParamsKey.NUMBER, meetingInfoResp.getNumber());
        mainRepo.joinMeeting(params, new MyDisposableSubscriber<JoinMeetingResp>() {
            @Override
            public void onBefore() {
                showProgress();
            }

            @Override
            public void onSuccess(JoinMeetingResp joinMeetingResp) {
                Bundle bundle = new Bundle();
                bundle.putInt(BundleKey.MEETING_NUMBER, meetingInfoResp.getNumber());
                bundle.putString(BundleKey.MEETING_TOKEN, joinMeetingResp.getToken());
                bundle.putString(BundleKey.APP_KEY, joinMeetingResp.getAppKey());
                bundle.putInt(BundleKey.ROOM_ID, joinMeetingResp.getRoomId());
                bundle.putInt(BundleKey.SHARE_USER_ID, joinMeetingResp.getShareUserId());
                bundle.putString(BundleKey.SHARE_USER_TOKEN, joinMeetingResp.getShareUserToken());
                bundle.putBoolean(BundleKey.IS_BREAKOUT, joinMeetingResp.isBreakout());
                bundle.putBoolean(BundleKey.IS_OWNER, meetingInfoResp.getOwnerId() == SpUtils.getInt(SpKey.USER_ID, -1));
                startActivity(MeetingActivity.class, bundle);
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

    public void goJoinMeeting() {
        if (!SingleClick.isSingleClick()) {
            return;
        }
        startActivity(JoinMeetingActivity.class);
    }

    public void goQuickMeeting() {
        if (!SingleClick.isSingleClick()) {
            return;
        }
        startActivity(QuickMeetingActivity.class);
    }

    public void goAppointMeeting() {
        if (!SingleClick.isSingleClick()) {
            return;
        }
        startActivity(AppointMeetingActivity.class);
    }

    public LiveData<List<MeetingListResp>> getMeetingList() {
        return meetingList;
    }
}
