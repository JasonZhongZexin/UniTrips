/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.model.MapModel;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class getRequest {
    public void main(){
        request ();
    }

    public void request() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("/v1/tp")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<stopBean> call = request.getCall();

        call.enqueue(new Callback<stopBean>() {
            @Override
            public void onResponse(Call<stopBean> call, Response<stopBean> response) {
                System.out.print(response.body().getName());
            }

            @Override
            public void onFailure(Call<stopBean> call, Throwable t) {
                System.out.print("False");
            }
        });
    }

    public static Location getLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getAllProviders();
        Location bestLocation = null;

        for (String provider : providers) {
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                bestLocation = location;
            } catch (SecurityException ignored) {
            }
        }

        return bestLocation;
    }

}
