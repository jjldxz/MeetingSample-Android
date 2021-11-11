package com.jjl.dxz.platform.meeting.vm;

import android.app.Application;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.frame.BaseViewModel;
import com.jjl.dxz.platform.meeting.base.net.ErrorResp;
import com.jjl.dxz.platform.meeting.bean.req.NewMeetingReq;
import com.jjl.dxz.platform.meeting.bean.resp.NewMeetingResp;
import com.jjl.dxz.platform.meeting.constant.SpKey;
import com.jjl.dxz.platform.meeting.repository.AppointMeetingRepository;
import com.jjl.dxz.platform.meeting.util.SpUtils;
import com.jjl.dxz.platform.meeting.util.TimeUtils;
import com.jjl.dxz.platform.meeting.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class AppointMeetingViewModel extends BaseViewModel {

    private AppointMeetingRepository mRepo;
    public MutableLiveData<String> meetingTheme = new MutableLiveData<>();
    public ObservableBoolean needPwd = new ObservableBoolean(false);
    public MutableLiveData<Integer> showPwd = new MutableLiveData<>(View.GONE);
    public MutableLiveData<String> meetingPwd = new MutableLiveData<>();
    public MutableLiveData<Boolean> muteSound = new MutableLiveData<>(false);
    public MutableLiveData<String> beginTime = new MutableLiveData<>();
    public MutableLiveData<Integer> meetingDuration = new MutableLiveData<>();

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E HH:mm", Locale.getDefault());
    private SparseIntArray chooseBeginTime;
    /**
     * 会议时长 默认120分钟
     */
    private int duration = 120;
    private Date chooseBeginAtDate = new Date();
    private Date chooseEndAtDate = new Date(120 * 60 * 1000 + chooseBeginAtDate.getTime());

    public AppointMeetingViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void init() {
        mRepo = new AppointMeetingRepository();

        meetingTheme.setValue(getString(R.string.placeholder_meeting_name, SpUtils.getInt(SpKey.USER_ID, -1)));
        beginTime.setValue(sdf.format(new Date()));
        meetingDuration.setValue(duration);

        needPwd.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showPwd.setValue(needPwd.get() ? View.VISIBLE : View.GONE);
            }
        });
    }

    public SparseIntArray getChooseBeginTime() {
        return chooseBeginTime;
    }

    public void setChooseBeginTime(SparseIntArray chooseBeginTime) {
        this.chooseBeginTime = chooseBeginTime;
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, chooseBeginTime.get(0));
        calendar.set(Calendar.MONTH, chooseBeginTime.get(1));
        calendar.set(Calendar.DATE, chooseBeginTime.get(2));
        calendar.set(Calendar.HOUR_OF_DAY, chooseBeginTime.get(3));
        calendar.set(Calendar.MINUTE, chooseBeginTime.get(4));
        chooseBeginAtDate = calendar.getTime();
        beginTime.setValue(sdf.format(chooseBeginAtDate));
        setDuration(duration);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        long time = (long) duration * 60 * 1000 + chooseBeginAtDate.getTime();
        chooseEndAtDate = new Date(time);
        meetingDuration.setValue(duration);
    }

    public void done() {
        if (meetingTheme.getValue() == null || TextUtils.isEmpty(meetingTheme.getValue().trim())) {
            ToastUtils.showShort(getContext(), R.string.please_input_meeting_theme);
            return;
        }
        if (needPwd.get() && (meetingPwd.getValue() == null || TextUtils.isEmpty(meetingPwd.getValue()))) {
            ToastUtils.showShort(getContext(), R.string.please_input_join_meeting_pwd);
            return;
        }
        NewMeetingReq req = new NewMeetingReq();
        req.setName(meetingTheme.getValue());
        req.setBeginAt(TimeUtils.getUtcTime(chooseBeginAtDate));
        req.setEndAt(TimeUtils.getUtcTime(chooseEndAtDate));
        req.setMuteType(muteSound.getValue() != null && muteSound.getValue() ? 1 : 0);
        if (needPwd.get() && meetingPwd.getValue() != null && !TextUtils.isEmpty(meetingPwd.getValue())) {
            req.setPassword(meetingPwd.getValue());
        }
        mRepo.newMeeting(req, new MyDisposableSubscriber<NewMeetingResp>() {
            @Override
            public void onBefore() {
                showProgress();
            }

            @Override
            public void onSuccess(NewMeetingResp newMeetingResp) {
                ToastUtils.showLong(getContext(), R.string.appoint_meeting_success);
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
