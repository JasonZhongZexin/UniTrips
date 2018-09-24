/**
 * Copyright (c) 2018. [ Zexin Zhong ]
 *
 */

package com.sep.UniTrips.model.ImportCalendar;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UTSTimetableServicesClient {

    private static Retrofit mRetrofit = null;
    private static final String HOST_URL = "https://mytimetable.uts.edu.au";

    public static  Retrofit getClient(final Context context){
        mRetrofit = new Retrofit.Builder().baseUrl(HOST_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return mRetrofit;
    }
}
