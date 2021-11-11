package com.jjl.dxz.platform.meeting.base.net;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseRepository2<T1, T2> extends BaseRemoteRepository {

    protected T1 mService1;
    protected T2 mService2;

    public BaseRepository2() {
        createService();
    }

    @SuppressWarnings("unchecked")
    private void createService() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            Class<T1> clazz1 = (Class<T1>) types[0];
            mService1 = getService(clazz1);

            Class<T2> clazz2 = (Class<T2>) types[1];
            mService2 = getService(clazz2);
        }
    }
}
