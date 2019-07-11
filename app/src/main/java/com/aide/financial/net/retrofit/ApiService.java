package com.aide.financial.net.retrofit;

import com.aide.financial.net.retrofit.resp.GankResp;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("{category}/{count}/{pager}")
    Observable<GankResp> post(@Path("category") String category, @Path("count") int count, @Path("pager") int pager);

}
