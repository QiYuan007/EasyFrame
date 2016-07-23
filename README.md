#EasyFrame 2.0 用法
***
###网络框架用法
**由于网络架构采用 RxJava 和 Retrofit 实现,所以在使用时需要如下几个步骤**

1. HTTPHelper.java 类中配置主机地址
2. INetInterface.java 接口中配置访问的接口地址（注意：主机地址拼接接口地址为正常网络地址）

	    /**
	     * get 请求
     	 * @param city
     	 * @return
     	 */
    	@GET("data/cityinfo/{city_id}")
   		Observable<WeatherResponse>getWeather(@Path("city_id") String city);

3. HTTPHelper.java 类中配置和 INetInterface.java 类中定义的接口一一对应的请求方法。

		private INetInterface mNetService;//INetInterface.java接口的实例

	对应2步骤中的实现方法为：

		//*****************对应 INetService接口中定义的请求方法*********************//

    	/**
     	 * get获取网络数据的方法
     	 *
     	 * @param cityId
     	 */
    	public <T extends IModel> Subscriber getWeather(String cityId, int resultType, ResultSubscriber.OnResultListener listener) {
        	Observable<T> observable = (Observable<T>) mNetService.getWeather(cityId);
        	return initObservable(observable, resultType, listener);
    	}		
4. 在View中调用网络接口代码如下：

		 HTTPHelper.getInstance().getWeather("101010300.html",CODE,MainActivity.this);
	CODE 字段为int值，用来区分不同的接口，上述代码中，由于MainActivity实现了
	
		ResultSubscriber.OnResultListener
	接口，所以回调的方法如下：
	
		/**
     	 * 网络请求前调用，通常显示Progressialog
     	 * @param requestType
     	 */
    	@Override
    	public void onStart(int requestType) {
        	Log.i(TAG,"onStart");
	        showProgressDialog("");
    	}

   		/**
     	 * 网络请求完成调用，通常销毁Progressialog
     	 * @param requestType
     	 */
    	@Override
    	public void onCompleted(int requestType) {
        	Log.i(TAG,"onCompleted");
        	dismissProgressDialog();
    	}

    	/**
     	 * 网络请求错误后调用
     	 * @param requestType
     	 */
    	@Override
    	public void onError(int requestType) {
        	Log.i(TAG,"onError");
	        dismissProgressDialog();
    	}

	    /**
    	 * onNext 方法中处理请求下来的数据
	     * @param iModel
    	 * @param requestType
	     */
    	@Override
	    public void onNext(IModel iModel, int requestType) {
        	if (requestType == CODE){
            	mTextView.setText(((WeatherResponse)iModel).getWeatherinfo().toString());
	        }
    	}
***
###图片处理方法
**图片处理采用的是Glide框架，所以此方法是在Glide基础上做的封装处理**

	GlideUtils.java		为工具类
	CustomCachingGlideModule.java	为Glide图片框架的内存缓存和磁盘缓存规则类
	RoundTransformation.java	为Glide图片圆形以及圆角转换算法类
***
###数据库处理方法
**数据库为抽取的 xUtils 3.0 框架**

详细了解请访问：[xUtils 3.0](https://github.com/wyouflf/xUtils3)

	
