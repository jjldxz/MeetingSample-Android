package com.jjl.dxz.platform.meeting.base.net;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseRepository<T> extends BaseRemoteRepository {

    protected T mService;

    public BaseRepository() {
        createService();
    }

    @SuppressWarnings("unchecked")
    private void createService() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            Class<T> clazz = (Class<T>) types[0];
            mService = getService(clazz);
        }
    }
}
