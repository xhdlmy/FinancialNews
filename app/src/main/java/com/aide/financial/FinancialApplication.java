package com.aide.financial;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.tencent.stat.StatConfig;
import com.tencent.stat.StatReportStrategy;
import com.tencent.stat.StatService;

/**
 * Created by Bruce on 2019/6/24.
 */

public class FinancialApplication extends Application {

    public static int sVersionCode;
    public static String sVersionName;
    public static String sPackageName;
    public static String sChannelId;

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this.getApplicationContext();
        initBuildConfig();
        initMta();
    }

    private void initMta() {
        StatConfig.setDebugEnable(isApkInDebug(sContext));
        // 数据发送策略
        StatConfig.setStatSendStrategy(StatReportStrategy.PERIOD);
        StatConfig.setSendPeriodMinutes(5);
        // TLink 推广功能
         StatConfig.setTLinkStatus(true);

        StatService.registerActivityLifecycleCallbacks(this);
    }

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    private void initBuildConfig() {
        sVersionCode = BuildConfig.VERSION_CODE;
        sVersionName = BuildConfig.VERSION_NAME;
        sPackageName = BuildConfig.APPLICATION_ID;
        sChannelId = BuildConfig.FLAVOR;
    }

    private void initAppInfo() {
        PackageManager packageManager = sContext.getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(sContext.getPackageName(),0);
            sVersionCode = packInfo.versionCode;
            sVersionName = packInfo.versionName;
            sPackageName = packInfo.packageName;
            ApplicationInfo appInfo = packageManager.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            sChannelId = appInfo.metaData.getString("CHANNEL_ID");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            sVersionCode = -1;
            sVersionName = "package not found!";
            sPackageName = sContext.getPackageName();
            sChannelId = "debug";
        }
    }

}
