package demo.myframework.application;

import android.app.Application;

import org.xutils.x;

import demo.myframework.common.Constant;

/**
 * @Author: lizhipeng
 * @Data: 16/5/23 下午3:21
 * @Description:
 */
public class EasyFrameApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initXutils();
    }

    /**
     * 初始化xutils
     */
    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(Constant.isDebug); // 开启debug会影响性能
    }
}
