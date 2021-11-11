package com.jjl.dxz.platform.meeting.base.ext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class MyRxJava3CallAdapterFactory extends CallAdapter.Factory {

    private CallAdapter.Factory mFactory;

    public static MyRxJava3CallAdapterFactory create() {
        return new MyRxJava3CallAdapterFactory();
    }

    private MyRxJava3CallAdapterFactory() {
        mFactory = RxJava3CallAdapterFactory.create();
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        final CallAdapter<?, ?> callAdapter = mFactory.get(returnType, annotations, retrofit);
        Class<?> rawType = getRawType(returnType);
        boolean isFlowable = rawType == Flowable.class;
        boolean isObservable = rawType == Observable.class;
        if (null == callAdapter) {
            return null;
        }

        if (isObservable) {
            return CallAdapterFactory.create((CallAdapter<Observable<?>, Observable<?>>) callAdapter, f -> f.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()));
        }
        if (isFlowable) {
            return CallAdapterFactory.create((CallAdapter<Flowable<?>, Flowable<?>>) callAdapter, f -> f.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()));
        }
        return null;
    }
}
