package com.sep.UniTrips.model.MapModel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetRequest_Interface {
    @Headers({
            "Content-Type: application/json",
            "Authorization: apikey Ys7NXvCei8c0FHRBx0IaPcUeX5Rh0MLYb3JS"
    })
    @GET("/v1/tp/stop_finder")
    Call<stopBean> getCall();
}
