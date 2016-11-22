package com.qy.easyframe.http;


import com.qy.easyframe.utils.L;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HTTPInterceptor implements Interceptor {
    private static String TAG = "HTTP-Interceptor";
    private Headers mHeaders;
    private Map<String,String> mMapHeaders;

    @Override
    public Response intercept(Chain chain) throws IOException {
        //封装headers
        Request request;

        Request.Builder builder = chain.request().newBuilder();
        if (mHeaders != null) {
            Set<String> set = mHeaders.names();
            for (String name : set) {
                builder.addHeader(name, mHeaders.get(name));
            }
        } else if (mMapHeaders != null){
            for (String name: mMapHeaders.keySet()){
                builder.addHeader(name,mMapHeaders.get(name));
            }
        }
        request = builder
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .build();
        L.i("==========================================================requestHeaders=========================================================");
        L.d(TAG, "requestHeaders=====>" + request.headers());
        L.i("==========================================================requestHeaders=========================================================");
        L.i(" ");
        L.d("===========================================================requestBodys==========================================================");
        L.i(TAG, "request:" + request.toString());
        L.d("===========================================================requestBodys==========================================================");
        L.i(" ");
        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        L.i("==========================================================responseHeaders========================================================");
        L.d(TAG, String.format(Locale.getDefault(), "Received response for %s in (%.1fms)%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        L.i("==========================================================responseHeaders========================================================");
        L.i(" ");
        Long content_length = response.body().contentLength();
        ResponseBody responseBody;
        if (content_length < 0) {
            responseBody = response.peekBody(1024 * 4);
        } else {
            responseBody = response.peekBody(content_length);
        }
        L.d("==========================================================responseBodys==========================================================");
        L.i(TAG, "response body:" + responseBody.string());
        L.d("==========================================================responseBodys==========================================================");
        return response;
    }

    public Headers getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Headers headers) {
        this.mHeaders = headers;
    }

    public void setMapHeaders(Map<String, String> mapHeaders) {
        this.mMapHeaders = mapHeaders;
    }
}
