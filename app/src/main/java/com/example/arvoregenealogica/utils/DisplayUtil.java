package com.example.arvoregenealogica.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class DisplayUtil {

    public static DisplayMetrics getMetrics() {
        return Resources.getSystem().getDisplayMetrics();
    }

    public static float getDensity() {
        return getMetrics().density;
    }

    public static int getScreenHeight() {
        return getMetrics().heightPixels;
    }

    public static int getScreenWidth() {
        return getMetrics().widthPixels;
    }

    public static int getDensityDpi() {
        return getMetrics().densityDpi;
    }

    public static String getScreenSize(Context context) {
        String screenSizeText = "";

        int screenSize = context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            screenSizeText = "small";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            screenSizeText = "normal";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            screenSizeText = "large";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            screenSizeText = "xlarge";
        }

        return screenSizeText;
    }

    public static String getScreenDensity() {
        String screenDensityText = "";

        int screenDensity = getDensityDpi();
        if (DisplayMetrics.DENSITY_LOW == screenDensity) {
            screenDensityText = "ldpi";
        } else if (DisplayMetrics.DENSITY_MEDIUM == screenDensity) {
            screenDensityText = "mdpi";
        } else if (DisplayMetrics.DENSITY_TV == screenDensity) {
            screenDensityText = "tvdpi";
        } else if (DisplayMetrics.DENSITY_HIGH == screenDensity) {
            screenDensityText = "hdpi";
        } else if (DisplayMetrics.DENSITY_280 == screenDensity) {
            screenDensityText = "280dpi";
        } else if (DisplayMetrics.DENSITY_XHIGH == screenDensity) {
            screenDensityText = "xhdpi";
        } else if (DisplayMetrics.DENSITY_360 == screenDensity) {
            screenDensityText = "360dpi";
        } else if (DisplayMetrics.DENSITY_400 == screenDensity) {
            screenDensityText = "400dpi";
        } else if (DisplayMetrics.DENSITY_420 == screenDensity) {
            screenDensityText = "420dpi";
        } else if (DisplayMetrics.DENSITY_XXHIGH == screenDensity) {
            screenDensityText = "xxhdpi";
        } else if (DisplayMetrics.DENSITY_560 == screenDensity) {
            screenDensityText = "560dpi";
        } else if (DisplayMetrics.DENSITY_XXXHIGH == screenDensity) {
            screenDensityText = "xxxhdpi";
        }
        return screenDensityText;
    }

    public static int px2dip(float pxValue) {
        return (int) (pxValue / getMetrics().density + 0.5f);
    }

    public static int dip2px(float dipValue) {
        return (int) (dipValue * getMetrics().density + 0.5f);
    }

    public static int px2sp(float pxValue) {
        return (int) (pxValue / getMetrics().density + 0.5f);
    }

    public static int sp2px(float spValue) {
        return (int) (spValue * getMetrics().density + 0.5f);
    }

    public static int convertToDp(int num) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, getMetrics());
    }

    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int statusBarHeight = 0;
        int resourceId = resources.getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * Captura de Tela
     */

    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
}
