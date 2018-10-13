/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */
package com.sep.UniTrips.model.ImportTransportModel;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NswTransportClient {
    private static Retrofit mRetrofit = null;
    private static final String API_URL = "https://api.transport.nsw.gov.au";

    /**
     * This method will return the client retrofit
     * @param context
     * @return retrofit
     */
    public static Retrofit getClient(final Context context) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        return mRetrofit;
    }
}
