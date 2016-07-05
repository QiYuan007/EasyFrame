package demo.myframework.common;


import android.content.Context;
import android.util.Log;

import demo.myframework.model.IModel;
import rx.Subscriber;

/**
 * @Author: lizhipeng
 * @Data: 16/4/12 下午4:17
 * @Description: 自定义调阅者以及结果监听接口
 */
public class ResultSubscriber extends Subscriber<IModel> {
    private int mRequestType;
    private OnResultListener mListener = null;
    private Context mContext;

    /**
     * 自定义订阅，参数用来区分网络接口，以用来在不同接口操作过程中，处理不同的逻辑
     *
     * @param
     */
    public ResultSubscriber(Context context) {
        mContext = context;
        mListener = new OnResultListener() {
            @Override
            public void onStart(int requestType) {
            }

            @Override
            public void onCompleted(int requestType) {
            }

            @Override
            public void onError(int requestType) {
            }

            @Override
            public void onNext(IModel t, int requestType) {
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
        try {
            if (e != null) {
                Log.e("ResultSubscriber","onError====>"+ e.getMessage());
//                Log.i(HTTPInterceptor.TAG, "onErrorMessage=====>" + e.getMessage());
//                Toast.shortToast(mContext, "网络异常，请稍后再试");
                mListener.onError(mRequestType);
                e.printStackTrace();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    @Override
    public void onNext(IModel t) {
        mListener.onNext(t, mRequestType);
    }

    /**
     * 设置订阅监听器
     *
     * @param listener
     */
    public void setOnResultListener(OnResultListener listener) {
        if (listener != null) {
            mListener = listener;
        }
    }

    /**
     * 设置请求接口类型
     *
     * @param requestType
     */
    public void setRequestType(int requestType) {
        this.mRequestType = requestType;
    }

    /**
     * 订阅的监听器
     */
    public interface OnResultListener {
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
        void onError(int requestType);

        /**
         * 处理请求结果
         */
        void onNext(IModel t, int requestType);
    }
}
