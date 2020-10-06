package com.faiz.managesystem.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * The type Http util.
 * 封装了okhttp,方便之后数据请求工作
 */
public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
