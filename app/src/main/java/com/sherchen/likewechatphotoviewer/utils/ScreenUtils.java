package com.sherchen.likewechatphotoviewer.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by dave on 2017/1/10.
 */

public class ScreenUtils {

    public static int getScreenWidth(Context mContext)
    {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static float getScreenDensity(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    public static int getSizeOfDip(Context mContext, int dipValue) {
        return (int) (dipValue * getScreenDensity(mContext)+ 0.5);
    }

    public static int getSizeOfDip(Context mContext, float dipValue) {
        return (int) (dipValue * getScreenDensity(mContext)+ 0.5);
    }

}
