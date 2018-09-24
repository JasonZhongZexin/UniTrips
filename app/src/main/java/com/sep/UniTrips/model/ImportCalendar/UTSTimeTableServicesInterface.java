/**
 * Copyright (c) 2018. [ Zexin Zhong ]
 *
 */

package com.sep.UniTrips.model.ImportCalendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UTSTimeTableServicesInterface {

    @FormUrlEncoded
    @POST("/{year}/rest/student/login")
    Call<LoginBeans> login (
            @Path("year") String year,
            @Field("username")String username,
            @Field("password")String password
    );

    @GET("/{year}/student")
    Call<ResponseBody> getResponseBody(
            @Path("year") String year,
            @Query("ss") String token,
            @Header("Cookie") String cookie
    );
}
