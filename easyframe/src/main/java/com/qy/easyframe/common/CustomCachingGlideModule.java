package com.qy.easyframe.common;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * @Author: lizhipeng
 * @Data: 16/5/25 上午9:50
 * @Description: 配置glide图片框架的内存缓存和磁盘缓存规则
 */
public class CustomCachingGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置glide使用ARGB888高清图片模式，默认使用RGB565
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //内存缓存设置,设置为glide默认大小的1.2倍
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
        // 设置磁盘缓存大小，byte为单位，此处为100M
        int cacheSize100MegaBytes = 104857600;
        /**磁盘缓存路径，四者根据情况任选一**/
        //设置磁盘缓存到app的内部默认路径
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes));
//        //设置磁盘缓存到外部默认路径,与内部路径的设置不能共用
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, cacheSize100MegaBytes));
//        // 指定磁盘缓存路径
//        String downloadDirectoryPath = Environment.getDownloadCacheDirectory().getPath();
//        //直接设置一个路径
//        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, cacheSize100MegaBytes));
//        // 指定路径里设置一个文件夹路径,与上面二选一
//        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, "patientCache", cacheSize100MegaBytes));
        /**磁盘缓存路径，四者根据情况任选一**/
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // nothing to do here
    }
}
