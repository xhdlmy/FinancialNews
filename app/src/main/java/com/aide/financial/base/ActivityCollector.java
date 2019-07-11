package com.aide.financial.base;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class ActivityCollector {
    
    @NonNull
    private static ArrayList<Activity> sActivityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        sActivityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        sActivityList.remove(activity);
    }

    public static Activity getTopActivity() {
        if (sActivityList.isEmpty()) {
            return null;
        } else {
            return sActivityList.get(sActivityList.size() - 1);
        }
    }

    public static int size(){
        return sActivityList.size();
    }

    public static ArrayList<Activity> getList(){
        return sActivityList;
    }

    public static boolean isLastActivity(){
        return sActivityList.size() == 1;
    }
}
