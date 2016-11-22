package com.qy.easyframe.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class BlurTransformation extends BitmapTransformation {

    private RenderScript mRs;

    public BlurTransformation(Context context) {
        super(context);

        mRs = RenderScript.create(context);
    }

    @SuppressLint("NewApi")
    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);

        // Allocate memory for Renderscript to work with
        Allocation input = Allocation.createFromBitmap(
                mRs,
                blurredBitmap,
                Allocation.MipmapControl.MIPMAP_FULL,
                Allocation.USAGE_SHARED
        );
        Allocation output = Allocation.createTyped(mRs, input.getType());
        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(mRs, Element.U8_4(mRs));
        script.setInput(input);
        // 设置模糊半径
        script.setRadius(10);

        // 开启模糊设置
        script.forEach(output);

        // 复制输出的已经模糊的图片
        output.copyTo(blurredBitmap);
        //回收bitmap
        toTransform.recycle();

        return blurredBitmap;
    }

    @Override
    public String getId() {
        return "patient_blur";
    }
}
