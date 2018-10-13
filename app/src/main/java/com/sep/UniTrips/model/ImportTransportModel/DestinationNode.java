/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */
package com.sep.UniTrips.model.ImportTransportModel;

import com.google.gson.annotations.SerializedName;

public class DestinationNode {

    @SerializedName("isGloballd")
    private boolean mGloballd;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("type")
    private String mType;
    @SerializedName("arrivalTimeEstimated")
    private String mArrivalTimeEstimated;
    @SerializedName("arrivalTimePlanned")
    private String mArrivalTimePlanned;

    public String getDepartureTimeEstimated() {
        return mDepartureTimeEstimated;
    }

    public String getDepartureTimePlanned() {
        return mDepartureTimePlanned;
    }

    @SerializedName("departureTimeEstimated")
    private String mDepartureTimeEstimated;

    public void setGloballd(boolean globalld) {
        mGloballd = globalld;
    }

    public void setDepartureTimeEstimated(String departureTimeEstimated) {
        mDepartureTimeEstimated = departureTimeEstimated;
    }

    public void setDepartureTimePlanned(String departureTimePlanned) {
        mDepartureTimePlanned = departureTimePlanned;
    }

    @SerializedName("departureTimePlanned")
    private String mDepartureTimePlanned;

    /**
     * This methos will set the isGoballd with the given data
     * @param mIsGloballd
     */
    public void setIsGloballd(boolean mIsGloballd) {
        this.mGloballd = mIsGloballd;
    }
    /**
     * This methos will set the  with the given data
     * @param id
     */
    public void setId(String id) {
        this.mId = id;
    }
/**
     * This methos will set the name with the given data
     * @param name
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * This methos will set the type with the given data
     * @param type
     */
    public void setType(String type) {
        this.mType = type;
    }

    /**
     * This methos will set the arrivalTimeEstimated with the given data
     * @param arrivalTimeEstimated
     */
    public void setArrivalTimeEstimated(String arrivalTimeEstimated) {
        this.mArrivalTimeEstimated = arrivalTimeEstimated;
    }

    /**
     * This methos will set the arrivalTimePlanned with the given data
     * @param arrivalTimePlanned
     */
    public void setArrivalTimePlanned(String arrivalTimePlanned) {
        this.mArrivalTimePlanned = arrivalTimePlanned;
    }

    /**
     * This is the getter of attributes Globalld
     * @return mGloballd
     */
    public boolean isGloballd() {

        return mGloballd;
    }

    /**
     * This is the getter of attributes mId
     * @return mId
     */
    public String getId() {
        return mId;
    }

    /**
     * This is the getter of attributes mName
     * @return mName
     */
    public String getName() {
        return mName;
    }

    /**
     * This is the getter of attributes mType
     * @return mType
     */
    public String getType() {
        return mType;
    }

    /**
     * This is the getter of attributes mArrivalTimeEstimated
     * @return mArrivalTimeEstimated
     */
    public String getArrivalTimeEstimated() {
        return mArrivalTimeEstimated;
    }

    /**
     * This is the getter of attributes mArrivalTimePlanned
     * @return mArrivalTimePlanned
     */
    public String getArrivalTimePlanned() {
        return mArrivalTimePlanned;
    }
}
