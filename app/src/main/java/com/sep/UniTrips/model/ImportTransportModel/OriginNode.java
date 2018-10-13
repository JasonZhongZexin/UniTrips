/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */
package com.sep.UniTrips.model.ImportTransportModel;

import com.google.gson.annotations.SerializedName;

public class OriginNode {

    @SerializedName("isGloballd")
    private boolean mGloballd;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("type")
    private String mType;
    @SerializedName("departureTimeEstimated")
    private String mDepartureTimeEstimated;
    @SerializedName("departureTimePlanned")
    private String mDepartureTimePlanned;

    /**
     * This is the setter of Globalld
     * @param mIsGloballd
     */
    public void setGloballd(boolean mIsGloballd) {
        this.mGloballd = mIsGloballd;
    }

    /**
     * This is the setter of id
     * @param id
     */
    public void setId(String id) {
        this.mId = id;
    }


    /**
     * This is the setter of name
     * @param name
     */
    public void setName(String name) {
        this.mName = name;
    }


    /**
     * This is the setter of type
     * @param type
     */
    public void setType(String type) {
        this.mType = type;
    }


    /**
     * This is the setter of departureTimeEstimated
     * @param departureTimeEstimated
     */
    public void setDepartureTimeEstimated(String departureTimeEstimated) {
        this.mDepartureTimeEstimated = departureTimeEstimated;
    }

    /**
     * This is the setter of departureTimePlanned
     * @param departureTimePlanned
     */
    public void setDepartureTimePlanned(String departureTimePlanned) {
        this.mDepartureTimePlanned = departureTimePlanned;
    }

    /**
     * This is the setter of mGloballd
     */
    public boolean getGloballd(){
        return this.mGloballd;
    }

    /**
     * This is the setter of mId
     */
    public String getId() {
        return mId;
    }

    /**
     * This is the setter of mName
     */
    public String getName() {
        return mName;
    }

    /**
     * This is the setter of mType
     */
    public String getType() {
        return mType;
    }

    /**
     * This is the setter of mDepartureTimeEstimated
     */
    public String getDepartureTimeEstimated() {
        return mDepartureTimeEstimated;
    }

    /**
     * This is the setter of mDepartureTimePlanned
     */
    public String getDepartureTimePlanned() {
        return mDepartureTimePlanned;
    }
}