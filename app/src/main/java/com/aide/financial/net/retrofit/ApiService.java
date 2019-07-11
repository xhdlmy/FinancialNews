package com.aide.financial.net.retrofit;

import com.aide.financial.net.retrofit.resp.GankResp;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("{category}/{count}/{pager}")
    Observable<ResponseBody> post(@Path("category") String category, @Path("count") int count, @Path("pager") int pager);

}
