package com.gaozhidong.android.noteapp.Adapter;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by zhidong on 2018/2/19.
 */

public abstract class NineGridImageViewAdapter<T> {
    public abstract void onDisplayImage(Context context, ImageView imageView, T t);

    public ImageView generateImageView(Context context){
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}
