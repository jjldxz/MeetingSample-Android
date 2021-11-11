package com.jjl.dxz.platform.meeting.base.net;


import android.util.Log;

import com.jjl.dxz.platform.meeting.base.ext.MyRxJava3CallAdapterFactory;
import com.jjl.dxz.platform.meeting.base.ext.SSLSocketClient;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper {

    private static HttpHelper httpHelper;
    private Retrofit mRetrofit;

    public static HttpHelper getInstance() {
        if (httpHelper == null) {
            synchronized (HttpHelper.class) {
                if (httpHelper == null) {
                    httpHelper = new HttpHelper();
                }
            }
        }
        return httpHelper;
    }

    public void init(HttpHelperOption option) {

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier());

        for (Interceptor interceptor : option.interceptors) {
            httpBuilder.addInterceptor(interceptor);
        }
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(s -> {
            try {
                Log.e("meeting simple", URLDecoder.decode(s, "utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("meeting simple", s);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpBuilder.addInterceptor(loggingInterceptor);

        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(option.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(MyRxJava3CallAdapterFactory.create())
                .client(httpBuilder.build())
                .build();
    }

    public <T> T getService(Class<T> service) {
        return mRetrofit.create(service);
    }

    public static class HttpHelperOption {

        private String baseUrl;
        private final List<Interceptor> interceptors = new ArrayList<>();

        public HttpHelperOption setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public HttpHelperOption addInterceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
            return this;
        }
    }
}
