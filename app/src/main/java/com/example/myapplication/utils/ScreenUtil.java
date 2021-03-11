package com.example.myapplication.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Created by zhaopan on 2016/5/12 0012.
 */
public final class ScreenUtil {

    /**
     * 获取显示屏的真实高度。
     */
    public static int getRealScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (wm != null) {
            wm.getDefaultDisplay().getRealSize(point);
        }
        return point.y;
    }

    /**
     * 获取屏幕高度，包含StatusBar，不包含NavigationBar
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
        return outMetrics.heightPixels;
    }

    public static int getScreenShowHeight(Context context){
        return getScreenHeight(context)-getStatusBarHeight(context);
    }
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
        return outMetrics.widthPixels;
    }
    /**
     * 获取虚拟按键的高度
     */
    private static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        Log.d("","NavigationBarHeight = "+result);
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else {
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Exception e) {
                Log.e("", e.toString());
            }
        }
        return sNavBarOverride;
    }
    public static int getStateHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    //获取状态栏高度
    public static int getStatusBarHeight(Context activity) {
        int result = 0;
        if (activity != null) {
            Context context = activity.getApplicationContext();
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 调节系统亮度
     *
     * @param value 亮度值 0 到 255
     */
    public static void changeBrilliance(Context context, int value) {
        try {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public static boolean hasDisplayCutout(Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        final boolean[] hasDisplayCutout = {false};
        decorView.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                DisplayCutout displayCutout = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
                    Log.e("TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + displayCutout.getSafeInsetLeft());
                    Log.e("TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:" + displayCutout.getSafeInsetRight());
                    Log.e("TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + displayCutout.getSafeInsetTop());
                    Log.e("TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + displayCutout.getSafeInsetBottom());
                    List<Rect> rects = displayCutout.getBoundingRects();
                    if (rects == null || rects.size() == 0) {
                        Log.e("TAG", "不是刘海屏");
                        hasDisplayCutout[0] = false;
                    } else {
                        hasDisplayCutout[0] = true;
                    }
                }
            }
        });
        return hasDisplayCutout[0];
    }

    /**
     * 是否有刘海屏
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean hasNotchInScreen(Activity activity) {

        // android  P 以上有标准 API 来判断是否有刘海屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            DisplayCutout displayCutout = activity.getWindow().getDecorView().getRootWindowInsets().getDisplayCutout();
            if (displayCutout != null) {
                // 说明有刘海屏
                return true;
            }
        }
        // 通过其他方式判断是否有刘海屏  目前官方提供有开发文档的就 小米，vivo，华为（荣耀），oppo
        String manufacturer = Build.MANUFACTURER;

        if (StringUtils.isEmpty(manufacturer)) {
            return false;
        } else if (manufacturer.equalsIgnoreCase("HUAWEI")) {
            return hasNotchHw(activity);
        } else if (manufacturer.equalsIgnoreCase("xiaomi")) {
            return hasNotchXiaoMi(activity);
        } else if (manufacturer.equalsIgnoreCase("oppo")) {
            return hasNotchOPPO(activity);
        } else if (manufacturer.equalsIgnoreCase("vivo")) {
            return hasNotchVIVO(activity);
        } else {
            return false;
        }
    }

    /**
     * 判断vivo是否有刘海屏
     * https://swsdl.vivo.com.cn/appstore/developer/uploadfile/20180328/20180328152252602.pdf
     *
     * @param activity
     * @return
     */
    private static boolean hasNotchVIVO(Activity activity) {
        try {
            Class<?> c = Class.forName("android.util.FtFeature");
            Method get = c.getMethod("isFeatureSupport", int.class);
            return (boolean) (get.invoke(c, 0x20));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断oppo是否有刘海屏
     * https://open.oppomobile.com/wiki/doc#id=10159
     *
     * @param activity
     * @return
     */
    private static boolean hasNotchOPPO(Activity activity) {
        return activity.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * 判断xiaomi是否有刘海屏
     * https://dev.mi.com/console/doc/detail?pId=1293
     *
     * @param activity
     * @return
     */
    private static boolean hasNotchXiaoMi(Activity activity) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("getInt", String.class, int.class);
            return (int) (get.invoke(c, "ro.miui.notch", 0)) == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断华为是否有刘海屏
     * https://devcenter-test.huawei.com/consumer/cn/devservice/doc/50114
     *
     * @param activity
     * @return
     */
    private static boolean hasNotchHw(Activity activity) {

        try {
            ClassLoader cl = activity.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            return (boolean) get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            return false;
        }
    }
//    /**
//     * 调节当前屏幕亮度
//     * @param activity
//     * @param value 0 到 255
//     */
//    public static void changeCurrentBrightness(Activity activity, int value) {
//        float val;
//        if (value < 0) {
//            value = 0;
//        } else if (value > 255) {
//            value = 255;
//        }
////        if (Constant.followSystem) {
////            val = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
////        } else {
////            val = value / 255f;
////        }
//        Window window = activity.getWindow();
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.screenBrightness = val;
//        window.setAttributes(params);
//    }

    /**
     * 调节当前屏幕亮度
     *
     * @param activity
     * @param value    0 到 255
     */
    public static void changeCurrentBrilliance(Activity activity, int value) {
//        Constant.readBrilliance = value;
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        float val = value / 255f;
        //LogUtil.i("设置当前屏幕 " + value + " " + val);
        params.screenBrightness = val;
        window.setAttributes(params);
    }

    //    /** 获取当前屏幕亮度 （0~255） */
//    public static int getCurrentBrilliance(Context context) {
//        int value = 0;
//        ContentResolver cr = context.getContentResolver();
//        try {
//            Constant.readBrilliance = value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
////                LogUtil.i("当前亮度 " + value);
//        } catch (Settings.SettingNotFoundException e) {
//        }
//
//        return value;
//    }
// 判断是否开启了 “屏幕自动旋转”,true则为开启
    public static boolean isScreenAutoRotate(Context context) {
        int gravity = 0;
        try {
            gravity = Settings.System.getInt(context.getContentResolver(),
                    Settings.System.ACCELEROMETER_ROTATION);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return gravity == 1;
    }
}
