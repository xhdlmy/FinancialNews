package com.aide.financial.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 1 获取正在运行的服务
 * 2 启动/关闭 服务
 * 3 绑定/解绑 服务
 */
public class ServiceUtils {

    /**
     * 判断服务是否运行
     * @param className 完整包名的服务类名
     */
    public static boolean isServiceRunning(@NonNull Context appContext, @NonNull String className) {
        ActivityManager activityManager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> info = activityManager.getRunningServices(0x7FFFFFFF);
        if (info == null || info.size() == 0) return false;
        for (RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) return true;
        }
        return false;
    }

    /**
     * 获取所有运行的服务
     */
    public static Set getAllRunningService(@NonNull Context appContext) {
        ActivityManager activityManager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> info = activityManager.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (info == null || info.size() == 0) return null;
        for (RunningServiceInfo aInfo : info) {
            names.add(aInfo.service.getClassName());
        }
        return names;
    }

    /**
     * 启动服务
     */
    public static void startService(@NonNull Context appContext, String className) throws ClassNotFoundException {
        startService(appContext, Class.forName(className));
    }

    public static void startService(@NonNull Context appContext, Class<?> cls) {
        Intent intent = new Intent(appContext, cls);
        appContext.startService(intent);
    }

    /**
     * 停止服务
     */
    public static boolean stopService(@NonNull Context appContext, String className) throws ClassNotFoundException {
        return stopService(appContext, Class.forName(className));
    }

    public static boolean stopService(@NonNull Context appContext, Class<?> cls) {
        Intent intent = new Intent(appContext, cls);
        return appContext.stopService(intent);
    }

    /**
     * 绑定服务
     * @param className 完整包名的服务类名
     * @param conn      服务连接对象
     * @param flags     绑定选项：绑定时默认创建
     */
    public static void bindService(@NonNull Context appContext, String className, @NonNull ServiceConnection conn, int flags) throws ClassNotFoundException {
        bindService(appContext, Class.forName(className), conn, flags);
    }

    // 绑定时默认创建
    public static void bindService(@NonNull Context appContext, String className, @NonNull ServiceConnection conn) throws ClassNotFoundException {
        bindService(appContext, className, conn, Context.BIND_AUTO_CREATE);
    }

    public static void bindService(@NonNull Context appContext, Class<?> cls, @NonNull ServiceConnection conn, int flags) {
        Intent intent = new Intent(appContext, cls);
        appContext.bindService(intent, conn, flags);
    }

    /**
     * 解绑服务
     * @param conn 服务连接对象
     */
    public static void unbindService(@NonNull Context appContext, @NonNull ServiceConnection conn) {
        appContext.unbindService(conn);
    }

}
