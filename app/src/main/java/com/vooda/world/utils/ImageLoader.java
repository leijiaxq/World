package com.vooda.world.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vooda.world.R;

import java.io.File;


/**
 * Created by leijiaxq
 * Data       2016/12/27 11:55
 * Describe
 */
public class ImageLoader {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH   = "/";

    private static class ImageLoaderHolder {
        private static final ImageLoader INSTANCE = new ImageLoader();

    }

    private ImageLoader() {
    }

    public static final ImageLoader getInstance() {
        return ImageLoaderHolder.INSTANCE;
    }


    //直接加载网络图片
    public void displayImage(Context context, String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                //                .centerCrop()
                .placeholder(R.drawable.default_picture)
                .error(R.drawable.default_picture)
//                .crossFade()
                .into(imageView);
    }

    //直接加载网络图片
    public void displayImageCrop(Context context, String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.default_picture)
                .error(R.drawable.default_picture)
//                .crossFade()
//                .fitCenter()
                .into(imageView);
    }

    //直接加载网络图片
    public void displayImageCenter(Context context, String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.default_picture)
                .error(R.drawable.default_picture)
//                .crossFade()
                .fitCenter()
                .into(imageView);
    }

    //加载SD卡图片
    public void displayImage(Context context, File file, ImageView imageView) {
        Glide
                .with(context)
                .load(file)
//                .centerCrop()
                .placeholder(R.drawable.default_picture)
                .error(R.drawable.default_picture)
                .into(imageView);

    }

    //加载SD卡图片并设置大小
    public void displayImage(Context context, File file, ImageView imageView, int width, int height) {
        Glide
                .with(context)
                .load(file)
                .override(width, height)
//                .centerCrop()
                .placeholder(R.drawable.default_picture)
                .error(R.drawable.default_picture)
                .into(imageView);

    }

    //加载网络图片并设置大小
    public void displayImage(Context context, String url, ImageView imageView, int width, int height) {
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .override(width, height)
                .crossFade()
                .placeholder(R.drawable.default_picture)
                .error(R.drawable.default_picture)
                .into(imageView);
    }

    //加载drawable图片
    public void displayImage(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                //                .crossFade()
                .placeholder(R.drawable.default_picture)
                .error(R.drawable.default_picture)
                .into(imageView);
    }

    //加载drawable图片显示为圆形图片
    public void displayCricleImage(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .placeholder(R.drawable.default_picture)
                .error(R.drawable.default_picture)
                .into(imageView);
    }

    //加载网络图片显示为圆形图片
    public void displayCricleImage(Context context, String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                //.centerCrop()//网友反馈，设置此属性可能不起作用,在有些设备上可能会不能显示为圆形。
                .transform(new GlideCircleTransform(context))
                .crossFade()
//                .placeholder(R.drawable.default_picture)
                .error(R.drawable.default_picture)
                .into(imageView);
    }

    //加载SD卡图片显示为圆形图片
    public void displayCricleImage(Context context, File file, ImageView imageView) {
        Glide
                .with(context)
                .load(file)
                //.centerCrop()
                .transform(new GlideCircleTransform(context))
                .placeholder(R.drawable.default_picture)
                .error(R.drawable.default_picture)
                .into(imageView);

    }

    //将资源ID转为Uri
    public Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }



}