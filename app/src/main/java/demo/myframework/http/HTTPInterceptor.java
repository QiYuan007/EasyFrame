package demo.myframework.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Author: lizhipeng
 * @Data: 16/4/12 下午5:19
 * @Description: 定义http拦截器，用于设置http协议和日志调试
 */
public class HTTPInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //封装headers
        Request request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json") //添加请求头信息
                .build();
//        Headers headers = request.headers();
        String requestUrl = request.url().toString(); //获取请求url地址
        String methodStr = request.method(); //获取请求方式
        RequestBody body = request.body(); //获取请求body
        String bodyStr = (body==null?"":body.toString());
        //打印Request数据
        Log.i("HTTP-Interceptor","requestUrl=====>"+requestUrl);
        Log.i("HTTP-Interceptor","requestMethod=====>"+methodStr);
        Log.i("HTTP-Interceptor","requestBody=====>"+body);

        Response response = chain.proceed(request);
        return response;
    }
}
