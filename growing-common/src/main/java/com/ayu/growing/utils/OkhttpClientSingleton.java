package com.ayu.growing.utils;

/**
 * Created by carloszhang
 * on 2018/5/9.
 */

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * okhttpclient单例模式
 */
public class OkhttpClientSingleton {


    protected static final int DEFAULT_CONNECTION_TIMEOUT_IN_MS = 3000;

    protected static final int DEFAULT_SOCKET_TIMEOUT_IN_MS = 3000;

    private OkhttpClientSingleton() {

    }

    public static final OkHttpClient getOkHttpClient() {
        return OkHttpClientHolder.INSTANCE;
    }

    private static class OkHttpClientHolder {
        private static final OkHttpClient INSTANCE = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECTION_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_SOCKET_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .build();
    }

}
