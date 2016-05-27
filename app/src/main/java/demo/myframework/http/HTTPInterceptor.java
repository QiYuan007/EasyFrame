package demo.myframework.http;


import java.io.IOException;
import java.util.Locale;

import demo.myframework.utils.L;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @Author: lizhipeng
 * @Data: 16/4/12 下午5:19
 * @Description: 定义http拦截器，用于设置http协议和日志调试
 */
public class HTTPInterceptor implements Interceptor {
    private static String TAG = "HTTP-Interceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        //封装headers
        Request request = chain.request().newBuilder()
//                .addHeader("Content-Type", "application/json") //TODO 添加请求头信息,和后台协调编码，不过通常都是utf-8
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .build();
////        Headers headers = request.headers();
//        String requestUrl = request.url().toString(); //获取请求url地址
//        String methodStr = request.method(); //获取请求方式
//        RequestBody body = request.body(); //获取请求body
//        String bodyStr = (body==null?"":body.toString());
//        //打印Request数据
//        L.i("HTTP-Interceptor","requestUrl=====>"+requestUrl);
//        L.i("HTTP-Interceptor","requestMethod=====>"+methodStr);
        L.i(TAG,"requestHeaders=====>"+request.headers());

        L.i(TAG, "request:" + request.toString());
        long t1 = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        L.i(TAG, String.format(Locale.getDefault(), "Received response for %s in (%.1fms)%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        Long content_length = response.body().contentLength();
        ResponseBody responseBody;
        if (content_length < 0) {
            responseBody = response.peekBody(1024 * 4);
        }else {
            responseBody = response.peekBody(content_length);
        }

        L.i(TAG, "response body:" + responseBody.string());
        return response;
    }
}
