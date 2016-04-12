package demo.myframework.common;

import rx.Subscriber;

/**
 * @Author: lizhipeng
 * @Data: 16/4/12 下午4:17
 * @Description: 自定义调阅者以及结果监听接口
 */
public class ResultSubscriber<T> extends Subscriber<T> {
    private int mRequestType;
    private OnResultListener<T> mListener = null;

    /**
     * 自定义订阅，参数用来区分网络接口，以用来在不同接口操作过程中，处理不同的逻辑
     * @param requestType
     */
    public ResultSubscriber(int requestType) {
        this.mRequestType = requestType;
        mListener = new OnResultListener<T>() {
            @Override
            public void onStart(int requestType) {
            }
            @Override
            public void onCompleted(int requestType) {
            }
            @Override
            public void onError(Throwable e, int requestType) {
            }
            @Override
            public void onNext(T t, int requestType) {
            }
        };
    }

    @Override
    public void onStart() {
        mListener.onStart(mRequestType);
    }

    @Override
    public void onCompleted() {
        mListener.onCompleted(mRequestType);
    }

    @Override
    public void onError(Throwable e) {
        if (e != null){
            mListener.onError(e,mRequestType);
        }
    }

    @Override
    public void onNext(T t) {
        mListener.onNext(t,mRequestType);
    }

    /**
     * 设置订阅监听器
     * @param listener
     */
    public void setOnResultListener(OnResultListener listener){
        if (listener != null){
            mListener = listener;
        }
    }

    /**
     * 订阅的监听器
     * @param <T>
     */
    public interface OnResultListener<T> {
        /**
         * 网络请求订阅开始
         */
        void onStart(int requestType);

        void onCompleted(int requestType);

        void onError(Throwable e,int requestType);

        void onNext(T t,int requestType);
    }
}
