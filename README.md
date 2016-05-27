
#2016-5-27
新增加功能
        1.完善网络框架
        2.增加glide图片处理框架
        3.抽取xutils3中的数据库操作框架引入

# EasyFrame
使用方法
1.项目为AS上的MVC架构，项目中实现了GSON解析，新建的javaBean需要实现IModel接口。
2.INetService.java 接口为网络请求定义接口，里面的getWeather（）方法为get请求示例。
3.HTTPHelper.java 类为网络请求单例类，里面的getWeather（）方法为示例，与上面一一对应。
4.HTTPInterceptor.java 类为OKhttp拦截器，里面做了简单实现，根据具体项目需求，需要适当改动。
5.ResultSubscriber.java 类 为RXJAVA的订阅器，不需要改动。用法在注释中已经清楚注明。
具体使用
在需要的view界面中，实现ResultSubscriber.OnResultListener接口。覆写四个方法。
#
        /**
         * 网络请求订阅开始
         */
        void onStart(int requestType);
        
        /**
         * 网络请求完成
         */
        void onCompleted(int requestType);
        
        /**
         * 网络请求错误
         */
        void onError(Throwable e,int requestType);
        
        /**
         * 处理请求结果
         */
        void onNext(T t,int requestType);
        
    
    
并在需要执行网络请求的地方，用HTTPHelper.getInstance()对象来调用具体请求方法。
需要说明的是，示例中：
#
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
    
方法的resultType参数，用来区分不同的网络接口，以用来在不同接口连续操作过程中，处理不同的逻辑
