package com.jjl.dxz.platform.meeting.base;

import android.app.Application;
import android.text.TextUtils;

import com.jjl.dxz.platform.meeting.base.net.HttpHelper;
import com.jjl.dxz.platform.meeting.constant.Constants;
import com.jjl.dxz.platform.meeting.constant.SpKey;
import com.jjl.dxz.platform.meeting.util.SpUtils;

import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SpUtils.init(this, "meetingSimple");
        HttpHelper.getInstance().init(new HttpHelper.HttpHelperOption().setBaseUrl(Constants.BASE_URL).addInterceptor(chain -> {
            //Bearer
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Content-Type", "application/json; charset=utf-8");
            String userToken = SpUtils.getStr(SpKey.USER_TOKEN, "");
            if (!TextUtils.isEmpty(userToken)) {
                builder.addHeader("Authorization", "Bearer " + userToken);
            }
            return chain.proceed(builder.build());
        }).addInterceptor(new HttpLoggingInterceptor()));
    }
}
