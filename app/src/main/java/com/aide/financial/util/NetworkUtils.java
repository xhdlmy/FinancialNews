package com.aide.financial.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.aide.financial.FinancialApplication;

/**
 * Created by computer on 2018/4/8.
 */

public class NetworkUtils {
    
    public static Context sContext = FinancialApplication.sContext;

    public static NetworkInfo getNetworkInfo(){
        // 一定不要用 Activity 作为 Context，否则 ConnectivityManager.mContext 会引发内存泄漏
        ConnectivityManager connectivityManager = (ConnectivityManager) sContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    public static boolean isNetworkConnected() {
        if (sContext != null) {
            NetworkInfo networkInfo = getNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }


    public static String getNetType() {
        NetworkInfo networkInfo = NetworkUtils.getNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                return "wifi";
            } else if (type.equalsIgnoreCase("MOBILE")) {
                return "mobile";
            } else {
                return "unknow";
            }
        } else {
            return "no_net";
        }
    }

}
