package com.demo.neteasecloudmusic.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class UpdateUtil {
    /**
     * @return 获取当前程序的版本名
     */
    public static String getVersionName(Context context) throws Exception {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.
                getPackageInfo(context.getPackageName(), 0);
        Log.e(context.toString(), "版本号" + packageInfo.versionCode);
        Log.e(context.toString(), "版本名" + packageInfo.versionName);
        return packageInfo.versionName;
    }
    public static int getVersionCode(Context context) throws Exception{
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo=packageManager.
                getPackageInfo(context.getPackageName(),0);
        return packageInfo.versionCode;
    }
}
