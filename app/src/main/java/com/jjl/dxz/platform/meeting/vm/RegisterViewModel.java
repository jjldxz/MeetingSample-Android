package com.jjl.dxz.platform.meeting.vm;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.frame.BaseViewModel;
import com.jjl.dxz.platform.meeting.base.net.ErrorResp;
import com.jjl.dxz.platform.meeting.bean.req.RegisterReq;
import com.jjl.dxz.platform.meeting.bean.resp.LoginResp;
import com.jjl.dxz.platform.meeting.bean.resp.VerifyUserNameResp;
import com.jjl.dxz.platform.meeting.constant.SpKey;
import com.jjl.dxz.platform.meeting.repository.RegisterRepository;
import com.jjl.dxz.platform.meeting.ui.MainActivity;
import com.jjl.dxz.platform.meeting.util.SingleClick;
import com.jjl.dxz.platform.meeting.util.SpUtils;
import com.jjl.dxz.platform.meeting.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

public class RegisterViewModel extends BaseViewModel {

    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> pwd = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> firstName = new MutableLiveData<>();
    public MutableLiveData<String> lastName = new MutableLiveData<>();

    private RegisterRepository registerRepo;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void init() {
        registerRepo = new RegisterRepository();
    }

    public void registerAndLogin() {
        if (!SingleClick.isSingleClick()) {
            return;
        }
        if (userName.getValue() == null || TextUtils.isEmpty(userName.getValue().trim())) {
            ToastUtils.showShort(getContext(), R.string.please_input_user_name);
            return;
        }
        if (pwd.getValue() == null || TextUtils.isEmpty(pwd.getValue().trim())) {
            ToastUtils.showShort(getContext(), R.string.please_input_pwd);
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", userName.getValue());
        registerRepo.verifyUserName(params, new MyDisposableSubscriber<VerifyUserNameResp>() {
            @Override
            public void onBefore() {
                showProgress();
            }

            @Override
            public void onSuccess(VerifyUserNameResp verifyUserNameResp) {
                if (verifyUserNameResp.isValid()) {
                    register();
                } else {
                    ToastUtils.showShort(getContext(), R.string.user_name_is_used);
                }
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

    private void register() {
        RegisterReq req = new RegisterReq();
        req.setUsername(userName.getValue());
        req.setPassword(pwd.getValue());
        if (email.getValue() != null && !TextUtils.isEmpty(email.getValue().trim())) {
            req.setEmail(email.getValue());
        }
        if (firstName.getValue() != null && !TextUtils.isEmpty(firstName.getValue().trim())) {
            req.setFirstName(firstName.getValue());
        }
        if (lastName.getValue() != null && !TextUtils.isEmpty(lastName.getValue().trim())) {
            req.setLastName(lastName.getValue());
        }
        registerRepo.register(req, new MyDisposableSubscriber<LoginResp>() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onSuccess(LoginResp loginResp) {
                SpUtils.putStr(SpKey.BEFORE_LOGIN_USER_NAME, userName.getValue());
                SpUtils.putStr(SpKey.BEFORE_LOGIN_PWD, pwd.getValue());
                SpUtils.putInt(SpKey.USER_ID, loginResp.getUserId());
                SpUtils.putStr(SpKey.USER_TOKEN, loginResp.getToken());
                SpUtils.putStr(SpKey.USER_REFRESH_TOKEN, loginResp.getRefreshToken());
                startActivity(MainActivity.class);
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
