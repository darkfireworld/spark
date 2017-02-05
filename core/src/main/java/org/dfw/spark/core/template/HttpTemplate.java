package org.dfw.spark.core.template;


import okhttp3.*;
import org.dfw.spark.core.annotation.NotNull;
import org.dfw.spark.core.annotation.Nullable;
import org.dfw.spark.core.callback.Callback;
import org.dfw.spark.core.tuple.Tuple2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class HttpTemplate {
    OkHttpClient httpClient;

    public HttpTemplate() {
        httpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    Map<String, List<Cookie>> cache = new ConcurrentHashMap<String, List<Cookie>>();

                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        cache.put(httpUrl.host(), list);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        List<Cookie> cookieList = cache.get(httpUrl.host());
                        if (cookieList == null) {
                            cookieList = new ArrayList<Cookie>();
                        }
                        return cookieList;
                    }
                })
                .connectTimeout(6, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 发起 GET 请求
     */
    public void get(@NotNull String url, @Nullable Map<String, String> headerMap, @Nullable final Callback<Tuple2<Integer, String>, Exception> callback) {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                builder.addHeader(key, headerMap.get(key));
            }
        }
        httpClient.newCall(builder.build()).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final int code = response.code();
                final String string = response.body().string();
                if (callback != null) {
                    callback.onSuccess(new Tuple2<Integer, String>(code, string));
                }
            }
        });
    }

    /**
     * 发起 GET 请求
     */
    public Call get(@NotNull String url, @Nullable Map<String, String> headerMap) {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                builder.addHeader(key, headerMap.get(key));
            }
        }
        return httpClient.newCall(builder.build());
    }

    /**
     * 发起 POST
     */
    public void post(@NotNull String url, @Nullable Map<String, String> headerMap, @Nullable RequestBody requestBody, @Nullable final Callback<Tuple2<Integer, String>, Exception> callback) {
        //如果请求的数据为空，则给定一个空的表单，进行提交
        if (requestBody == null) {
            requestBody = new FormBody.Builder().build();
        }
        //构造请求
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(requestBody);
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                builder.addHeader(key, headerMap.get(key));
            }
        }
        httpClient.newCall(builder.build()).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final int code = response.code();
                final String string = response.body().string();
                if (callback != null) {
                    callback.onSuccess(new Tuple2<Integer, String>(code, string));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    /**
     * 发起 POST
     */
    public Call post(@NotNull String url, @Nullable Map<String, String> headerMap, @Nullable RequestBody requestBody) {
        //如果请求的数据为空，则给定一个空的表单，进行提交
        if (requestBody == null) {
            requestBody = new FormBody.Builder().build();
        }
        //构造请求
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(requestBody);
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                builder.addHeader(key, headerMap.get(key));
            }
        }
        return httpClient.newCall(builder.build());
    }
}
