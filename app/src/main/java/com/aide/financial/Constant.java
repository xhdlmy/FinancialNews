package com.aide.financial;

/**
 * Created by Bruce on 2019/6/24.
 */

public class Constant {

    public static final String INFO_ALL = FinancialApplication.getContext().getString(R.string.info_all);
    public static final String INFO_WEB = FinancialApplication.getContext().getString(R.string.info_web);
    public static final String INFO_ANDROID = FinancialApplication.getContext().getString(R.string.info_android);
    public static final String INFO_IOS = FinancialApplication.getContext().getString(R.string.info_ios);
    public static final String INFO_REST = FinancialApplication.getContext().getString(R.string.info_rest);
    public static final String INFO_EXPAND = FinancialApplication.getContext().getString(R.string.info_expands);
    public static final String INFO_WEAL = FinancialApplication.getContext().getString(R.string.info_weal);

    public static final int COUNT_10 = 10;
//    public static final int COUNT_20 = 20;

    public static final String GLIDE_DIR = "glide";
    public static final int GLIDE_CACHE_SIZE = 1024 * 1024 * 24; // 24M 缓存

    /**
     * HTTP 协议相关
     */
    // 超时时间
    public static final String URL_BASE = "http://gank.io/api/data/";
    public static final long CONNECT_TIMEOUT = 30000L;
    public static final long READ_TIMEOUT = 30000L;
}
