#EasyFrame 3.0.0 网络框架使用说明
***
[EasyFrame3.0.0](https://github.com/QiYuan007/EasyFrame)是采用 RxJava 和 Retrofit 实现的网络处理架构，采用 Glide 作为图片处理架构。极大的简化了APP开发中的框架搭建，library项目已经上传 Jcenter 和 Maven 库，使用方式如下：
###Jcenter：
	compile 'com.qy.easyframe:easyframe:3.0.0'
###Maven：
	<dependency>
	  <groupId>com.qy.easyframe</groupId>
	  <artifactId>easyframe</artifactId>
	  <version>3.0.0</version>
	  <type>pom</type>
	</dependency>

###使用方法：
####一.网络请求的回调接口为ResultSubscriber.OnResultListener在请求网络的界面或者业务逻辑中实现此接口并实现接口的三个方法，分别是：

		/**
         * 网络请求订阅开始
         */
        void onStart(int requestType);

        /**
         * 网络请求错误
         */
        void onError(int requestType,Throwable e);

        /**
         * 处理请求结果
         */
        void onResult(IModel model, int requestType);
        
三个方法看名字就知道对应的功能，其中 requestType 字段为不同网络请求的标示，可以自行区分。IModel 类为返回的json字段对应的 JavaBean 类的公共父类，所有的 Response 的类，都要继承 IModel 类。
####二.定义你的针对 Retrofit 的网络请求接口，如下：

	/**
	 * @Author: qiyuan
	 * @Data: 16/4/12 下午2:57
	 * @Description:  网络请求接口
	 */
	public interface INetInterface {
	    /**
	     * get 请求
	     * @param city
	     * @return
	     */
	    @GET("data/cityinfo/{city_id}")
	    Observable<WeatherResponse> getWeather(@Path("city_id") String city);

	    /**
	     * post 请求
	     * @param body javabean请求体
	     * @return
	     */
	    @POST("data/cityinfo/")
	    Observable<WeatherResponse> postWeather(@Body WeatherRequest body);

	    /**
	     * post请求
	     * @param params 表单
	     * @return
	     */
	    @POST("data/cityinfo/")
	    Observable<WeatherResponse> postWeather2(@FieldMap Map<String, String> params);
	}
如上接口，需要自定义的地方有：

* 请求方式的注解内的字符串，替换成自己的除主机地址外的地址或者全地址（通常是除主机外的地址）
* Observable中的泛型，是网络请求响应回来的json字符串对应的JavaBean对象，统一继承IModel父类
* 方法名当然是自己起名字啦。。
#####注意
此接口不能有继承关系

####三.定义第二步网络请求接口的实现类，如下:
	/**
	 * @Author: qiyuan
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
	        Observable<WeatherResponse> observable = HTTPHelper.getInstance().init(Constant.BASE_PATH, INetInterface.class).getWeather(cityId);
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
	//        Observable<T> observable = (Observable<T>) 	HTTPHelper.getInstance().init(Constant.BASE_PATH, INetInterface.class, headers).getRecommendDetails(assetId);
	        Observable<WeatherResponse> observable = HTTPHelper.getInstance().init(Constant.BASE_PATH, INetInterface.class, map).getWeather(cityId);
	        return HTTPHelper.getInstance().doRequest(observable, resultType, listener);
	    }

	}
此类是对第二步接口类的实现，具体接口得实现是在

	HTTPHelper.getInstance().init(Constant.BASE_PATH, INetInterface.class)
方法中操作，只需要讲第二步中的接口得class对象传入即可。

	HTTPHelper.getInstance().doRequest(observable, resultType, listener)
此方法返回Subscriber对象，可以用此对象来中断网络操作，比如在界面销毁时中断。

####Activity或者Fragment或者其他业务中的使用
	比如demo中的点击事件中，直接调用上一步的接口实现类的方法，并传递对应参数。
	mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"onClick");
                HttpRequest.getInstance().getWeather("101010300.html",CODE,MainActivity.this);
            }
        });
####总结
	三步实现网络请求
		1.实现ResultSubscriber.OnResultListener监听
		2.实现对应网络请求的接口类
		3.对接口类进行实现
		4.由于library不能从BuildConfig中读取Debug状态，如果调试期间需要打印log日志，
		可在Application中添加AppFrame.initDebug(true)方法
***
##HTTPHelper类中的初始化方法如下
	<T extends IModel> Subscriber	doRequest(<any> observable, int resultType, ResultSubscriber.OnResultListener listener)
	初始化观察者
	static HTTPHelper getInstance()
	获取单例对象
	Interceptor	getInterceptor()
	获取拦截器
	<I> I	init(String baseUrl, Class<I> clazz)
	初始化
	<I> I	init(String baseUrl, Class<I> clazz, Headers headers) 
	<I> I	init(String baseUrl, Class<I> clazz, long timeOut)
	初始化
	<I> I	init(String baseUrl, Class<I> clazz, long timeOut, Headers headers) 
	HTTPHelper	setInterceptor(Interceptor mInterceptor)
	设置拦截器
***
##Glide图片工具类使用方法及说明
	static void initImageIcon(int id) 
	初始化默认的加载图
	static void	into(android.content.Context context, File file, android.widget.ImageView view)
	glide 从文件中加载图片
	static void	into(android.content.Context context, File file, android.widget.ImageView view, int width, int height)
	glide 通过指定的大小从文件中加载图片
	static void	into(android.content.Context context, int resourceId, android.widget.ImageView view)
	glide 从资源ID中加载图片
	static void	into(android.content.Context context, int resourceId, android.widget.ImageView view, int width, int height)
	glide 通过指定的大小从资源ID中加载图片
	static void	into(android.content.Context context, String url, android.widget.ImageView view)
	glide 从字符串中加载图片（网络地址或者本地地址）
	static void	into(android.content.Context context, String url, android.widget.ImageView view, int defaultId)
	glide 从字符串中加载图片（网络地址或者本地地址）,
	static void	into(android.content.Context context, String url, android.widget.ImageView view, int width, int height)
	glide 通过指定的大小从字符串中加载图片（网络地址或者本地地址）
	static void	into(android.content.Context context, android.net.Uri uri, android.widget.ImageView view)
	glide 从URI中加载图片
	static void	into(android.content.Context context, android.net.Uri uri, android.widget.ImageView view, int width, int height)
	glide 通过指定的大小从Uri中加载图片
	static void	intoBlur(android.content.Context context, String url, android.widget.ImageView view)
	高斯模糊图片处理
	static void	intoDefault(android.content.Context context, int id, android.widget.ImageView view)
	默认glide，不做任何处理
	static void	intoDefault(android.content.Context context, String url, android.widget.ImageView view)
	默认glide，不做任何处理
	static void	intoRound(android.content.Context context, int id, int radius, android.widget.ImageView view)
	圆或者圆角图片处理
	static void	intoRound(android.content.Context context, String url, int radius, android.widget.ImageView view)
	圆或者圆角图片处理
	static void	intoRound(android.content.Context context, String url, int radius, android.widget.ImageView view, int defaultId)
	从字符串中加载圆形图片（网络地址或者本地地址）
***
###数据库处理方法

数据库为抽取的 xUtils 3.0 框架.
详细了解请访问：[xUtils 3.0](https://github.com/wyouflf/xUtils3)
***
###关于作者--起猿
Email：496121717@qq.com, lzp4796121717@gmail.com
有任何建议或者使用中遇到问题都可以给我发邮件, 你也可以加入QQ群：163411187技术交流，idea分享。
##License
	Copyright 2013 Square, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, 
	software distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
	See the License for the specific language governing permissions and limitations 
	under the License.
