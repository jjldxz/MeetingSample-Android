package com.jjl.dxz.platform.meeting.vm;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.frame.BaseViewModel;
import com.jjl.dxz.platform.meeting.base.net.ErrorResp;
import com.jjl.dxz.platform.meeting.bean.resp.GroupDetailResp;
import com.jjl.dxz.platform.meeting.constant.BundleKey;
import com.jjl.dxz.platform.meeting.repository.GroupDetailRepository;

import java.util.HashMap;
import java.util.Map;

public class GroupDetailViewModel extends BaseViewModel {
    private GroupDetailRepository mRepository;
    private int roomId;
    private GroupDetailResp groupDetailResp;

    public GroupDetailViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void init() {
        mRepository = new GroupDetailRepository();
    }

    public void init(Bundle bundle) {
        roomId = bundle.getInt(BundleKey.MEETING_NUMBER);

        Map<String, Integer> params = new HashMap<>();
        params.put("number", roomId);
        mRepository.getGroupDetails(params, new MyDisposableSubscriber<GroupDetailResp>() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onSuccess(GroupDetailResp groupDetailResp) {
                GroupDetailViewModel.this.groupDetailResp = groupDetailResp;
                if (groupDetailResp.getGroup() != null && !groupDetailResp.getGroup().isEmpty()) {

                }
            }

            @Override
            public void onError(ErrorResp errorResp) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
