package com.jjl.dxz.platform.meeting.base.ext;

import androidx.annotation.NonNull;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;

public class CallAdapterFactory<R, W> implements CallAdapter<R, W> {

    private final CallAdapter<R, W> mAdapter;
    private final Function<W, W> mFunction;

    public static <R, W> CallAdapterFactory<R, W> create(CallAdapter<R, W> mAdapter, Function<W, W> mFunction) {
        return new CallAdapterFactory<>(mAdapter, mFunction);
    }

    private CallAdapterFactory(CallAdapter<R, W> mAdapter, Function<W, W> mFunction) {
        this.mAdapter = mAdapter;
        this.mFunction = mFunction;
    }

    @NonNull
    @Override
    public Type responseType() {
        return mAdapter.responseType();
    }

    @NonNull
    @Override
    public W adapt(@NonNull Call<R> call) {
        W adapt = mAdapter.adapt(call);
        if (mFunction == null) {
            return adapt;
        }
        try {
            adapt = mFunction.apply(mAdapter.adapt(call));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return adapt;
    }
}
