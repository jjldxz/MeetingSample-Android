package com.jjl.dxz.platform.meeting.vm.poll;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.frame.BaseViewModel;
import com.jjl.dxz.platform.meeting.base.net.ErrorResp;
import com.jjl.dxz.platform.meeting.bean.resp.PollResp;
import com.jjl.dxz.platform.meeting.repository.PollRepository;

import java.util.List;

public class PollMainViewModel extends BaseViewModel {

    private final PollRepository mRepo;
    private final MutableLiveData<List<PollResp>> polls = new MutableLiveData<>();

    public MutableLiveData<String> pollTheme = new MutableLiveData<>("");
    public MutableLiveData<Boolean> isAnonymous = new MutableLiveData<>(false);

    public PollMainViewModel(@NonNull Application application) {
        super(application);
        mRepo = new PollRepository();
    }

    @Override
    protected void init() {

    }

    public void getPollList(int meetingNumber) {
        mRepo.getPollList(meetingNumber, new MyDisposableSubscriber<List<PollResp>>() {
            @Override
            public void onBefore() {
                showProgress();
            }

            @Override
            public void onSuccess(List<PollResp> pollResp) {
                polls.setValue(pollResp);
            }

            @Override
            public void onError(ErrorResp errorResp) {

            }

            @Override
            public void onCompleted() {
                dismissProgress();
            }
        });
    }

    public void addQuestion() {

    }

    public void commit() {

    }

    public LiveData<List<PollResp>> getPolls() {
        return polls;
    }
}
