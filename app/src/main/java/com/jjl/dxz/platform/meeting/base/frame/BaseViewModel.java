package com.jjl.dxz.platform.meeting.base.frame;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.util.SingleLiveData;

import java.lang.ref.WeakReference;

public abstract class BaseViewModel extends AndroidViewModel {
    private WeakReference<Context> mContext;
    private final SingleLiveData<Boolean> showDialog = new SingleLiveData<>();
    private final SingleLiveData<Class<?>> startActivity = new SingleLiveData<>();
    private final SingleLiveData<SparseArray<Object>> startActivityByBundle = new SingleLiveData<>();
    private final SingleLiveData<Boolean> finish = new SingleLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mContext = new WeakReference<>(application.getApplicationContext());
    }

    protected Context getContext() {
        return mContext.get();
    }

    protected abstract void init();

    @Override
    protected void onCleared() {
        super.onCleared();
        mContext.clear();
        mContext = null;
    }

    protected String getString(@StringRes int resId) {
        return getContext().getString(resId);
    }

    protected String getString(@StringRes int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    protected void showProgress() {
        showDialog.setValue(true);
    }

    protected void dismissProgress() {
        showDialog.setValue(false);
    }

    protected void startActivity(Class<?> clazz) {
        startActivity.setValue(clazz);
    }

    protected void startActivity(Class<?> clazz, Bundle bundle) {
        SparseArray<Object> sparseArray = new SparseArray<>();
        sparseArray.put(0, clazz);
        sparseArray.put(1, bundle);
        startActivityByBundle.setValue(sparseArray);
    }

    protected void showAlertDialog(@StringRes int messageResId, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog
                .Builder(getContext())
                .setTitle(R.string.alert)
                .setMessage(messageResId)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, onClickListener)
                .show();
    }

    protected void finish() {
        finish.setValue(true);
    }

    public LiveData<Boolean> getFinish() {
        return finish;
    }

    public LiveData<Class<?>> getStartActivity() {
        return startActivity;
    }

    public LiveData<SparseArray<Object>> getStartActivityByBundle() {
        return startActivityByBundle;
    }

    public LiveData<Boolean> getShowDialog() {
        return showDialog;
    }
}
