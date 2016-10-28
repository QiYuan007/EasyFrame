package com.qy.easyframe.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

/**
 * @Author: lizhipeng
 * @Data: 16/5/30 上午9:28
 * @Description: glide 图片圆形以及圆角转换算法
 */
public class RoundTransformation extends BitmapTransformation {
    private float mRadius;//角度

    public RoundTransformation(Context context) {
        this(context, 0);
    }

    /**
     * @param context
     * @param radius  dp 单位
     */
    public RoundTransformation(Context context, int radius) {
        super(context);
        //dp 转 px
        mRadius = Resources.getSystem().getDisplayMetrics().density * radius;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform == null) {
            return null;
        }
        int length = outWidth > outHeight ? outHeight : outWidth;
        Bitmap result = pool.get(outWidth, outHeight, toTransform.getConfig() != null
                ? toTransform.getConfig() : Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        }
        Bitmap transform = TransformationUtils.centerCrop(result, toTransform, outWidth, outHeight);
        if (result != null && result != transform && !pool.put(result)) {
            result.recycle();
        }
        // 根据算出的宽高新建Bitmap对象并设置到画布上
        Bitmap bitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(transform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        if (mRadius <= 0) {//圆
            //获取正方形的bitmap
            float r = length / 2f;
            canvas.drawCircle(outWidth / 2, outHeight / 2, r, paint);
        } else {//圆角
            RectF rectF = new RectF(0f, 0f, outWidth, outHeight);
            canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
        }

        return bitmap;

    }


    @Override
    public String getId() {
        return getClass().getName() + System.currentTimeMillis();
    }
}
