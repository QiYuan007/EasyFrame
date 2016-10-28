package demo.myframework.application;

import android.app.Application;

import com.qy.easyframe.common.AppFrame;

import org.xutils.x;

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
        AppFrame.initDebug(true);
    }

    /**
     * 初始化xutils
     */
    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(true); // 开启debug会影响性能
    }
}
