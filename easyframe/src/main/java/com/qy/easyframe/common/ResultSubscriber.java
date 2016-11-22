package com.qy.easyframe.common;


import com.qy.easyframe.model.IModel;
import com.qy.easyframe.utils.L;

import rx.Subscriber;

public class ResultSubscriber extends Subscriber<IModel> {
    private int mRequestType;
    private OnResultListener mListener = null;

    /**
     * 自定义订阅，参数用来区分网络接口，以用来在不同接口操作过程中，处理不同的逻辑
     */
    public ResultSubscriber() {
        mListener = new OnResultListener() {
            @Override
            public void onStart(int requestType) {
            }

            @Override
            public void onError(int requestType,Throwable e) {
            }

            @Override
            public void onResult(IModel model, int requestType) {
            }
        };
    }

    @Override
    public void onStart() {
        mListener.onStart(mRequestType);
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        try {
            if (e != null) {
                L.d("==========================================================onError=========================================================");
                L.e(""+e.getMessage());
                L.d("==========================================================onError=========================================================");
                mListener.onError(mRequestType,e);
                e.printStackTrace();
                L.d("==========================================================onError=========================================================");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    @Override
    public void onNext(IModel model) {
        mListener.onResult(model, mRequestType);
    }

    /**
     * 设置订阅监听器
     *
     */
    public void setOnResultListener(OnResultListener listener) {
        if (listener != null) {
            mListener = listener;
        }
    }

    /**
     * 设置请求接口类型
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
         * 网络请求错误
         */
        void onError(int requestType,Throwable e);

        /**
         * 处理请求结果
         */
        void onResult(IModel model, int requestType);
    }
}
