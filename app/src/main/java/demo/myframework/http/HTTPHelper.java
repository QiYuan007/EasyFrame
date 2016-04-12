package demo.myframework.http;

import java.util.concurrent.TimeUnit;

import demo.myframework.common.ResultSubscriber;
import demo.myframework.interfaces.INetService;
import demo.myframework.model.IModel;
import demo.myframework.model.WeatherModel;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Author: lizhipeng
 * @Data: 16/4/12 下午3:27
 * @Description: 网络请求数据类（单例）
 */
public class HTTPHelper {

    /**
     * 这一部分配置常量，可以抽取出常量类
     */
    private static final String BASE_PATH = "http://www.weather.com.cn/";//访问的地址
    private static final long DEFAULT_TIMEOUT = 5000;//默认超时时间(毫秒)


    private Retrofit mRetrofit;
    private INetService mNetService;

    private HTTPHelper(){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new HTTPInterceptor());
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_PATH)
                .build();

        mNetService = mRetrofit.create(INetService.class);

    }

    /**
     * 单例控制器
     */
    private static class SingletonHolder{
        private static final HTTPHelper INSTANCE = new HTTPHelper();
    }

    /**
     * 获取单例对象
     * @return
     */
    public static HTTPHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }


    /**
     * 用来统一处理返回值，通常为公共message返回字段等。具体业务这里要具体操作
     *
     * @param <T> Subscriber真正需要的数据类型，也就是返回值针对的model
     */
    private class HttpResultFunc<T> implements Func1<IModel, T> {
        @Override
        public T call(IModel iModel) {
            if (iModel == null){
                try {
                    throw new Exception("result model is null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return (T)iModel;
        }
    }


    //********************************对应 INetService接口中定义的请求方法*************************************************//
    /**
     * 获取网络数据的方法
     * @param cityId
     */
    public void getWeather(String cityId, int resultType, ResultSubscriber.OnResultListener listener){
        ResultSubscriber<IModel> subscriber = new ResultSubscriber<>(resultType);
        subscriber.setOnResultListener(listener);
        mNetService.getWeather(cityId)
                .map(new HttpResultFunc<WeatherModel>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
