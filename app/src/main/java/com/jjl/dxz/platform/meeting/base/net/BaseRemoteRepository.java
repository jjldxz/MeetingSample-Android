package com.jjl.dxz.platform.meeting.base.net;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class BaseRemoteRepository {

    private CompositeDisposable mDisposable;

    protected <T> T getService(Class<T> service) {
        return HttpHelper.getInstance().getService(service);
    }

    protected void addDisposable(Disposable disposable) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(disposable);
    }

    public void clearDisposable() {
        mDisposable.dispose();
        mDisposable.clear();
        mDisposable = null;
    }
}
