package demo.myframework.http;


import com.qy.easyframe.common.ResultSubscriber;
import com.qy.easyframe.http.HTTPHelper;

import java.util.HashMap;
import java.util.Map;

import demo.myframework.common.Constant;
import demo.myframework.interfaces.INetInterface;
import demo.myframework.model.WeatherRequest;
import demo.myframework.model.WeatherResponse;
import rx.Observable;
import rx.Subscriber;

/**
 * @Author: lizhipeng
 * @Data: 16/10/25 下午3:24
 * @Description: HTTP请求实现类
 */

public class HttpRequest {

    public HttpRequest() {
    }

    /**
     * 单例控制器
     */
    private static class SingletonHolder {
        private static final HttpRequest INSTANCE = new HttpRequest();
    }
    /**
     * 获取单例对象
     *
     * @return
     */
    public static HttpRequest getInstance() {
        return HttpRequest.SingletonHolder.INSTANCE;
    }

    /**
     * get获取网络数据的方法
     *
     * @param cityId
     */
    public Subscriber getWeather(String cityId, int resultType, ResultSubscriber.OnResultListener listener) {
        Observable<WeatherResponse> observable =  HTTPHelper.getInstance().init(Constant.BASE_PATH, INetInterface.class).getWeather(cityId);
        return HTTPHelper.getInstance().doRequest(observable, resultType, listener);
    }

    /**
     * post获取网络数据的方法
     *
     * @param body
     */
    public Subscriber postWeather(WeatherRequest body, int resultType, ResultSubscriber.OnResultListener listener) {
        Observable<WeatherResponse> observable = HTTPHelper.getInstance().init(Constant.BASE_PATH, INetInterface.class).postWeather(body);
        return HTTPHelper.getInstance().doRequest(observable, resultType, listener);
    }

    /**
     * 自定义headers
     * @param cityId
     * @param resultType
     * @param listener
     * @return
     */
    public Subscriber getRecommendDetails(String cityId, int resultType, ResultSubscriber.OnResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("X-Token", "595a6e93-bddb-47af-a7ce-e63801315fd9");
//        Headers headers = Headers.of(map);
//        Observable<T> observable = (Observable<T>) HTTPHelper.getInstance().init(Constant.BASE_PATH, INetInterface.class, headers).getRecommendDetails(assetId);
        Observable<WeatherResponse> observable = HTTPHelper.getInstance().init(Constant.BASE_PATH, INetInterface.class, map).getWeather(cityId);
        return HTTPHelper.getInstance().doRequest(observable, resultType, listener);
    }

}
