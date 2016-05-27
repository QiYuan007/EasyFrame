package demo.myframework.common;

/**
 * @Author: lizhipeng
 * @Data: 16/5/3 下午4:27
 * @Description:常量配置类
 */
public class Constant {
    public static  boolean isDebug = true;//是否打印日志，在application里面已经初始化了,无需手动更改
    public interface LogConfig{//log工具类配置字段接口

        public static final boolean isWrite = false;//是否将日志写入文件
        public static final String TAG = "ezt_doctor";
    }

}
