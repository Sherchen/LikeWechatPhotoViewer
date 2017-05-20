package com.sherchen.likewechatphotoviewer.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

/**
 * The description of use:Glide图片加载工具类
 * <br />
 * Created time:2015/12/24 14:13
 * Created by Dave
 */
public class GlideUtils {

    public static void displayImage(Context context, ImageView imageView, String url, int placeHolder){
        test(url);
        Glide
                .with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeHolder)
                .error(placeHolder)
                .dontAnimate()// TODO: 2016/11/13 添加之后图片不会因为某张图片不存在，导致所有图片不加载。
                .into(imageView);
    }

    public static void displayImage(Context context, ImageView imageView, String url, Drawable placeHolder){
        test(url);
        Glide
                .with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeHolder)
                .error(placeHolder)
                .dontAnimate()// TODO: 2016/11/13 添加之后图片不会因为某张图片不存在，导致所有图片不加载。
                .into(imageView);
    }

    public static void displayImage(Context context, SimpleTarget simpleTarget, String url, int placeHolder){
        test(url);
        Glide
                .with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(simpleTarget);
    }

    private static void test(String url) {
        if(KLog.isShowLog()) {
            int index = 4;
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String className = stackTrace[index].getFileName();
            String methodName = stackTrace[index].getMethodName();
            int lineNumber = stackTrace[index].getLineNumber();

            methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(" ] ");
            stringBuilder.append(url);
            Log.v("GlideUtils", stringBuilder.toString());
        }
    }


    public static void displayImage(Context context, ImageView imageView, String url, int placeHolder, BitmapTransformation transformation){
        test(url);
        Glide
                .with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(transformation)
                .placeholder(placeHolder)
                .dontAnimate()
                .into(imageView);
    }


    public static void displayImage(Context context, ImageView imageView, Uri uri){
        KLog.d(uri);
        Glide
                .with(context)
                .load(uri)
                .into(imageView);
    }

//    public static void displayImage(Context context, ImageView imageView, Uri uri, int placeHolder){
//        KLog.d(uri);
//        Glide
//                .with(context)
//                .load(uri)
//                .placeholder(placeHolder)
//                .crossFade()
//                .into(imageView);
//    }

//    public static void displayImage(Context context, ImageView imageView, File file, int placeHolder) {
//        KLog.d(file);
//        Glide
//                .with(context)
//                .load(file)
//                .placeholder(placeHolder)
//                .into(imageView);
//    }

    public static void displayImage(Context context, ImageView imageView, File file) {
        KLog.d(file);
        Glide
                .with(context)
                .load(file)
                .into(imageView);
    }

    public static void displayImage(Context context, ImageView imageView, Uri uri, BitmapTransformation transformation){
        KLog.d(uri);
        Glide
                .with(context)
                .load(uri)
                .transform(transformation)
                .into(imageView);
    }


    public static void displayImageCenterCrop(Context context, ImageView imageView, String url, int placeHolder){
        test(url);
        Glide
                .with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeHolder)
                .centerCrop()
                .dontAnimate()
                .into(imageView);
    }

    public static void displayImageCenterCrop(Context context, ImageView imageView, Uri uri){
        KLog.d(uri);
        Glide
                .with(context)
                .load(uri)
                .centerCrop()
                .into(imageView);
    }

    public static void displayImage(Context context, ImageView imageView, int resId){
        Glide
                .with(context)
                .load(resId)
                .into(imageView);
    }

    public static void displayImage(Context context, ImageView imageView, Uri uri, int width, int height) {
        KLog.d(uri);
        Glide
                .with(context)
                .load(uri)
                .override(width, height)
                .into(imageView);
    }


    public static void displayImage(Fragment fragment, ImageView imageView, String url, int placeholder, BitmapTransformation transformation){
        test(url);
        Glide
                .with(fragment)
                .fromUri()
                .load(Uri.parse(url))
                .transform(transformation)
                .placeholder(placeholder)
                .dontAnimate()
                .into(imageView);
    }

    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    public static void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }
}
