package com.qy.easyframe.utils;

import android.util.Log;

import com.qy.easyframe.common.AppFrame;

public class L {

    /**
     * 不能被实例化
     */
    private L() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 下面四个是默认tag的函数
    public static void i(String msg)
    {
        if (AppFrame.DEBUG)
            Log.i("HTTP", msg);
    }

    public static void d(String msg)
    {
        if (AppFrame.DEBUG)
            Log.d("HTTP", msg);
    }

    public static void e(String msg)
    {
        if (AppFrame.DEBUG)
            Log.e("HTTP", msg);
    }

    public static void v(String msg)
    {
        if (AppFrame.DEBUG)
            Log.v("HTTP", msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg)
    {
        if (AppFrame.DEBUG)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg)
    {
        if (AppFrame.DEBUG)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg)
    {
        if (AppFrame.DEBUG)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg)
    {
        if (AppFrame.DEBUG)
            Log.i(tag, msg);
    }

}
