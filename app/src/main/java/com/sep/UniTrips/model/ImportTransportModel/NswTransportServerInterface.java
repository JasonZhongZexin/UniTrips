/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */
package com.sep.UniTrips.model.ImportTransportModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NswTransportServerInterface {

    /**
     * this method will get the transport data from the web server
     * @param outputFormat
     * @param coordOutputFormat
     * @param depArrMacro
     * @param type_origin
     * @param name_origin
     * @param type_destination
     * @param name_destination
     * @param calcNumberOfTrips
     * @param TfNSWTR
     * @param version
     * @return
     */
    @Headers({
            "Content-Type: application/json",
            "Authorization: apikey uj0FMcluSPoNjmIwRQ6cBjdMnnSCAoSOeTA0"
    })
    @GET("/v1/tp/trip")
    Call<List<Leg>> getTransportData(
            @Query("outputFormat") String outputFormat,
            @Query("coordOutputFormat") String coordOutputFormat,
            @Query("depArrMacro") String depArrMacro,
            @Query("type_origin") String type_origin,
            @Query("name_origin") String name_origin,
            @Query("type_destination") String type_destination,
            @Query("name_destination") String name_destination,
            @Query("calcNumberOfTrips") Integer calcNumberOfTrips,
            @Query("excludedMeans")String excludeMeans,
            @Query("exclMOT_1")String exclMOT_Train,
            @Query("exclMOT_4")String exclMOT_LightRail,
            @Query("exclMOT_5")String exclMOT_Bus,
            @Query("exclMOT_7")String exclMOT_Coach,
            @Query("exclMOT_8")String exclMOT_Ferry,
            @Query("exclMOT_11")String exclMOT_SchoolBus,
            @Query("TfNSWTR") String TfNSWTR,
            @Query("version") String version

    );
}
