/**
 * Copyright (c) 2018. [Zexin Zhong]
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions an limitations under the License.
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
