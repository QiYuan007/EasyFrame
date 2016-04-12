package demo.myframework.interfaces;

import demo.myframework.model.WeatherModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @Author: lizhipeng
 * @Data: 16/4/12 下午2:57
 * @Description:  网络请求接口
 */
public interface INetService {
    @GET("data/cityinfo/{city_id}.html")
    Observable<WeatherModel> getWeather(@Path("city_id") String city);
}
