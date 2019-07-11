/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.aide.financial.net.retrofit;

import com.aide.financial.Constant;
import com.aide.financial.net.okhttp.CookieJarImpl;
import com.aide.financial.net.okhttp.OkClient;
import com.aide.financial.net.okhttp.PersistentCookieStore;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建 Retrofit 实例
 */
public class RxRetrofit {

    private static Retrofit getRetrofit() {
        OkHttpClient client = OkClient.getInstance().getOkHttpClient();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(Constant.URL_BASE)
                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }

    public static CookieJarImpl getCookieJar(){
        return OkClient.getInstance().getCookieJar();
    }

    public static PersistentCookieStore getCookieStore(){
        return OkClient.getInstance().getCookieStore();
    }

    public static ApiService getService() {
        return getRetrofit().create(ApiService.class);
    }

}
