package com.jjl.dxz.platform.meeting.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class SingleLiveData<T> extends MutableLiveData<T> {
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, t -> {
            if (t == null) {
                return;
            }
            observer.onChanged(t);
        });
    }

    @Override
    public void postValue(T value) {
        super.postValue(value);
        super.postValue(null);
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
        super.setValue(null);
    }
}
