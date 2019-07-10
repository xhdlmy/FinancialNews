package com.aide.financial;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class FinancialApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context sContext;

    public static Context getContext(){
        return sContext;
    }

    public static int sVersionCode;
    public static String sVersionName;
    public static String sPackageName;
    public static String sChannelId;
    public static boolean sIsDebug;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        initBuildAppConfig();
    }

    private void initBuildAppConfig() {
        sVersionCode = BuildConfig.VERSION_CODE;
        sVersionName = BuildConfig.VERSION_NAME;
        sPackageName = BuildConfig.APPLICATION_ID;
        sChannelId = BuildConfig.FLAVOR;
        sIsDebug = BuildConfig.DEBUG;
    }

    private void initStrictAppInfo() {
        PackageManager packageManager = sContext.getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(sContext.getPackageName(),0);
            sVersionCode = packInfo.versionCode;
            sVersionName = packInfo.versionName;
            sPackageName = packInfo.packageName;
            ApplicationInfo appInfo = packageManager.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            sChannelId = appInfo.metaData.getString("CHANNEL_ID");
            ApplicationInfo info = this.getApplicationInfo();
            sIsDebug = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            sVersionCode = -1;
            sVersionName = "package not found!";
            sPackageName = sContext.getPackageName();
            sChannelId = "debug";
            sIsDebug = false;
        }
    }

}
