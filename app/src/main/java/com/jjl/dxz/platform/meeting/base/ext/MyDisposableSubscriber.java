package com.jjl.dxz.platform.meeting.base.ext;

import com.jjl.dxz.platform.meeting.base.net.ErrorResp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class MyDisposableSubscriber<T> extends DisposableSubscriber<T> {

    @Override
    protected void onStart() {
        onBefore();
        super.onStart();
    }

    @Override
    public void onError(Throwable t) {
        ErrorResp resp = new ErrorResp();
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            Response<?> response = httpException.response();
            try {
                if (response != null) {
                    String errorBody = response.errorBody().string();
                    JSONObject jsonObject = new JSONObject(errorBody);
                    if (jsonObject.has("code")) {
                        resp.setCode(jsonObject.optInt("code"));
                        resp.setData(jsonObject.optString("data"));
                        resp.setMessage(jsonObject.optString("message"));
                    } else {
                        resp.setCode(response.code());
                        resp.setMessage(response.errorBody().string());
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                resp.setCode(response.code());
                resp.setMessage(response.message());
            }
        } else {
            resp.setCode(-1000);
            resp.setMessage(t.getMessage());
        }
        onError(resp);
        onCompleted();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        onCompleted();
    }

    public abstract void onBefore();

    public abstract void onSuccess(T t);

    public abstract void onError(ErrorResp errorResp);

    public abstract void onCompleted();

    @Override
    public void onComplete() {

    }
}
