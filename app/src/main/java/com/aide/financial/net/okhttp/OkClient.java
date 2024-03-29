package com.aide.financial.net.okhttp;

import android.support.annotation.NonNull;

import com.aide.financial.Constant;
import com.aide.financial.FinancialApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * OkClient
 * 持有 OkHttpClient 对象 发送网络请求
 *      - 可以进行持久化cookie存储
 *      - 可以访问 Https
 *      - post 方式：并没有缓存策略，每次请求都是网络请求
 */
public class OkClient<T> {

    public static OkClient sInstance;
    public static OkClient getInstance() {
        if (sInstance == null) {
            synchronized (OkClient.class) {
                if (sInstance == null) {
                    sInstance = new OkClient();
                }
            }
        }
        return sInstance;
    }

    @NonNull
    private OkHttpClient mOkHttpClient;
    private PersistentCookieStore mCookieStore;
    private CookieJarImpl mCookieJar;

    private OkClient() {
        // HTTPS 忽略证书的过期验证
        HttpsVerify.SSLParams sslParams = HttpsVerify.getSslSocketFactory(new HttpsVerify.PastDueCATrustManager());
        // Cookie
        mCookieStore = new PersistentCookieStore(FinancialApplication.getContext());
        mCookieJar = new CookieJarImpl(mCookieStore);
        // OkHttpClient Build
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Constant.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(Constant.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .cookieJar(mCookieJar)
//                .sslSocketFactory(sslParams.sSSLSocketFactory, sslParams.sTrustManager)
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session){
//                        return true;
//                    }
//                })
                // 添加拦截器
                // 添加网络拦截器
                .build();
    }

    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }

    public CookieJarImpl getCookieJar(){
        return mCookieJar;
    }

    public PersistentCookieStore getCookieStore() {
        return mCookieStore;
    }
}

