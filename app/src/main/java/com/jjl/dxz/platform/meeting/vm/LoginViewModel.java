package com.jjl.dxz.platform.meeting.vm;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.base.ext.MyDisposableSubscriber;
import com.jjl.dxz.platform.meeting.base.frame.BaseViewModel;
import com.jjl.dxz.platform.meeting.base.net.ErrorResp;
import com.jjl.dxz.platform.meeting.bean.resp.LoginResp;
import com.jjl.dxz.platform.meeting.constant.ReqParamsKey;
import com.jjl.dxz.platform.meeting.constant.SpKey;
import com.jjl.dxz.platform.meeting.repository.LoginRepository;
import com.jjl.dxz.platform.meeting.ui.MainActivity;
import com.jjl.dxz.platform.meeting.ui.RegisterActivity;
import com.jjl.dxz.platform.meeting.util.SingleClick;
import com.jjl.dxz.platform.meeting.util.SpUtils;
import com.jjl.dxz.platform.meeting.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<String> userName = new MutableLiveData<>("");
    public MutableLiveData<String> pwd = new MutableLiveData<>("");
    private LoginRepository mRepo;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void init() {
        mRepo = new LoginRepository();
        if (!TextUtils.isEmpty(SpUtils.getStr(SpKey.BEFORE_LOGIN_USER_NAME, ""))) {
            userName.setValue(SpUtils.getStr(SpKey.BEFORE_LOGIN_USER_NAME, ""));
            pwd.setValue(SpUtils.getStr(SpKey.BEFORE_LOGIN_PWD, ""));
        }
    }

    public void login() {
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
        params.put(ReqParamsKey.USERNAME, userName.getValue());
        params.put(ReqParamsKey.PWD, pwd.getValue());
        mRepo.login(params, new MyDisposableSubscriber<LoginResp>() {
            @Override
            public void onBefore() {
                showProgress();
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

    public void register() {
        startActivity(RegisterActivity.class);
    }
}
