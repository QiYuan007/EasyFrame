package com.qy.easyframe.http;


import com.qy.easyframe.common.ResultSubscriber;
import com.qy.easyframe.model.IModel;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HTTPHelper {

    /**
     * 这一部分配置常量，可以抽取出常量类
     */
    private static final long DEFAULT_TIMEOUT = 5000;//默认超时时间(毫秒)

    private Retrofit mRetrofit;

    private Interceptor mInterceptor;

    private HTTPHelper() {
    }

    /**
     * 单例控制器
     */
    private static class SingletonHolder {
        private static final HTTPHelper INSTANCE = new HTTPHelper();
    }

    /**
     * 获取拦截器
     */
    public Interceptor getInterceptor() {
        return mInterceptor;
    }

    /**
     * 设置拦截器
     */
    public HTTPHelper setInterceptor(Interceptor mInterceptor) {
        this.mInterceptor = mInterceptor;
        return this;
    }

    /**
     * 初始化
     */
    public <I> I init(String baseUrl, Class<I> clazz){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (mInterceptor != null){
            okHttpClient.addNetworkInterceptor(mInterceptor);
        }else {
            okHttpClient.addNetworkInterceptor(new HTTPInterceptor());
        }
        okHttpClient.addNetworkInterceptor(new HTTPInterceptor());
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);//等待服务器响应的时间
        okHttpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        I mNetService = mRetrofit.create(clazz);
        return mNetService;
    }
    /**
     * 初始化
     */
    public <I> I init(String baseUrl, Class<I> clazz,long timeOut){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (mInterceptor != null){
            okHttpClient.addNetworkInterceptor(mInterceptor);
        }else {
            okHttpClient.addNetworkInterceptor(new HTTPInterceptor());
        }
        okHttpClient.connectTimeout(timeOut, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(timeOut, TimeUnit.MILLISECONDS);//等待服务器响应的时间
        okHttpClient.writeTimeout(timeOut, TimeUnit.MILLISECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        I mNetService = mRetrofit.create(clazz);
        return mNetService;
    }

    /**
     *
     * @param baseUrl host地址
     * @param clazz 接口类
     * @param headers 封装的请求头(okHttp3)
     * @param <I>
     * @return 返回接口实体类
     */
    public <I> I init(String baseUrl, Class<I> clazz, Headers headers){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (mInterceptor != null){
            okHttpClient.addNetworkInterceptor(mInterceptor);
        }else {
            HTTPInterceptor interceptor = new HTTPInterceptor();
            interceptor.setHeaders(headers);
            okHttpClient.addNetworkInterceptor(interceptor);
        }
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);//等待服务器响应的时间
        okHttpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        I mNetService = mRetrofit.create(clazz);
        return mNetService;
    }

    /**
     *
     * @param baseUrl host地址
     * @param clazz 接口类
     * @param headers 封装的请求头(okHttp3)
     * @param <I>
     * @return 返回接口实体类
     */
    public <I> I init(String baseUrl, Class<I> clazz, Map<String,String> headers){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (mInterceptor != null){
            okHttpClient.addNetworkInterceptor(mInterceptor);
        }else {
            HTTPInterceptor interceptor = new HTTPInterceptor();
            interceptor.setMapHeaders(headers);
            okHttpClient.addNetworkInterceptor(interceptor);
        }
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);//等待服务器响应的时间
        okHttpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        I mNetService = mRetrofit.create(clazz);
        return mNetService;
    }

    /**
     *
     * @param baseUrl host地址
     * @param clazz 接口类
     * @param timeOut 超时时间
     * @param headers 封装的请求头(okHttp3)
     * @param <I>
     * @return 返回接口实体类
     */
    public <I> I init(String baseUrl, Class<I> clazz,long timeOut, Headers headers){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (mInterceptor != null){
            okHttpClient.addNetworkInterceptor(mInterceptor);
        }else {
            HTTPInterceptor interceptor = new HTTPInterceptor();
            interceptor.setHeaders(headers);
            okHttpClient.addNetworkInterceptor(interceptor);
        }
        okHttpClient.connectTimeout(timeOut, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(timeOut, TimeUnit.MILLISECONDS);//等待服务器响应的时间
        okHttpClient.writeTimeout(timeOut, TimeUnit.MILLISECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        I mNetService = mRetrofit.create(clazz);
        return mNetService;
    }

    /**
     *
     * @param baseUrl host地址
     * @param clazz 接口类
     * @param timeOut 超时时间
     * @param headers 封装的请求头(okHttp3)
     * @param <I>
     * @return 返回接口实体类
     */
    public <I> I init(String baseUrl, Class<I> clazz,long timeOut, Map<String,String> headers){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (mInterceptor != null){
            okHttpClient.addNetworkInterceptor(mInterceptor);
        }else {
            HTTPInterceptor interceptor = new HTTPInterceptor();
            interceptor.setMapHeaders(headers);
            okHttpClient.addNetworkInterceptor(interceptor);
        }
        okHttpClient.connectTimeout(timeOut, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(timeOut, TimeUnit.MILLISECONDS);//等待服务器响应的时间
        okHttpClient.writeTimeout(timeOut, TimeUnit.MILLISECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        I mNetService = mRetrofit.create(clazz);
        return mNetService;
    }
    /**
     * 获取单例对象
     *
     * @return
     */
    public static HTTPHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }
    /**
     * 类型转换，用来统一处理返回值，通常为公共message返回字段等。具体业务这里要具体操作
     *
     * @param <T> Subscriber真正需要的数据类型，也就是返回值针对的model
     */
    private class HttpResultFunc<T> implements Func1<IModel, T> {
        @Override
        public T call(IModel iModel) {
            if (iModel == null) {
                try {
                    throw new Exception("result model is null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            T t = (T) iModel;
            if (t == null) {
                try {
                    throw new Exception("cast to the model is null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return t;
        }
    }

    /**
     * 初始化观察者
     * @param observable
     * @param resultType
     * @param listener
     */
    public <T extends IModel> Subscriber doRequest(Observable<T> observable, int resultType, ResultSubscriber.OnResultListener listener) {
        ResultSubscriber subscriber = new ResultSubscriber();
        subscriber.setOnResultListener(listener);
        subscriber.setRequestType(resultType);
        observable
//                .map(new HttpResultFunc<WeatherResponse>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        return subscriber;
    }



}
