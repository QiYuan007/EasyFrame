package demo.myframework.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

import java.io.File;

import demo.myframework.R;
import demo.myframework.common.BlurTransformation;

/**
 * @Author: lizhipeng
 * @Data: 16/5/25 上午9:30
 * @Description: Glide 图片框架封装
 */
public class GlideUtils {
    /**
     * 默认glide，不做任何处理
     *
     * @param context
     * @param url
     * @param view
     */
    public static void intoDefault(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .into(view);
    }

    /**
     * glide 从字符串中加载图片（网络地址或者本地地址）
     * @param context
     * @param url
     * @param view
     */
    public static void into(Context context, String url, ImageView view) {
        Glide.with(context).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()//淡入淡出
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);
    }
    /**
     * glide 从资源ID中加载图片
     * @param context
     * @param resourceId
     * @param view
     */
    public static void into(Context context, int resourceId, ImageView view) {
        Glide.with(context).load(resourceId)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()//淡入淡出
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);
    }
    /**
     * glide 从文件中加载图片
     * @param context
     * @param file
     * @param view
     */
    public static void into(Context context, File file, ImageView view) {
        Glide.with(context).load(file)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()//淡入淡出
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);
    }
    /**
     * glide 从URI中加载图片
     * @param context
     * @param uri
     * @param view
     */
    public static void into(Context context, Uri uri, ImageView view) {
        Glide.with(context).load(uri)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()//淡入淡出
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);
    }

    /**
     * glide 通过指定的大小从字符串中加载图片（网络地址或者本地地址）
     * @param context
     * @param url
     * @param view
     * @param width
     * @param height
     */
    public static void into(Context context, String url, ImageView view, int width, int height) {
        DrawableTypeRequest<String> request = Glide.with(context).load(url);
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        request.placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);
    }

    /**
     * glide 通过指定的大小从资源ID中加载图片
     * @param context
     * @param resourceId
     * @param view
     * @param width
     * @param height
     */
    public static void into(Context context, int resourceId, ImageView view, int width, int height) {
        DrawableTypeRequest<Integer> request = Glide.with(context).load(resourceId);
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        request.placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);
    }

    /**
     * glide 通过指定的大小从文件中加载图片
     * @param context
     * @param file
     * @param view
     * @param width
     * @param height
     */
    public static void into(Context context, File file, ImageView view, int width, int height) {
        DrawableTypeRequest<File> request = Glide.with(context).load(file);
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        request.placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);
    }

    /**
     * glide 通过指定的大小从Uri中加载图片
     * @param context
     * @param uri
     * @param view
     * @param width
     * @param height
     */
    public static void into(Context context, Uri uri, ImageView view, int width, int height) {
        DrawableTypeRequest<Uri> request = Glide.with(context).load(uri);
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        request.placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);
    }


    /**
     * 高斯模糊图片处理
     *
     * @param context
     * @param url
     * @param view
     */
    public static void intoBlur(Context context, String url, ImageView view) {
        Glide.with(context).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .transform(new BlurTransformation(context))
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);
    }
}
