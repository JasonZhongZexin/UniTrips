/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */
package com.sep.UniTrips.model.ImportTransportModel;

import com.google.gson.annotations.SerializedName;

public class Leg {

    @SerializedName("duration")
    private long mDuration;
    @SerializedName("distance")
    private long distance;
    @SerializedName("isRealtimeControlled")
    private boolean mRealtimeControlled;
    @SerializedName("origin")
    private DestinationNode mOrigin;
    @SerializedName("destination")
    private DestinationNode mDestination;

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public void setOrigin(DestinationNode origin) {
        mOrigin = origin;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    public void setRealTiemControllerd(Boolean realTiemControllerd) {
        isRealTiemControllerd = realTiemControllerd;
    }

    public long getDistance() {
        return distance;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public Boolean getRealTiemControllerd() {
        return isRealTiemControllerd;
    }

    @SerializedName("transportation")
    private Transportation transportation;
    @SerializedName("isRealTiemControllerd")
    private Boolean isRealTiemControllerd;

    /**
     * This is the getter of duration
     * @return mDuration
     */
    public long getDuration() {
        return mDuration;
    }

    /**
     * This is the getter of mRealtimeControlled
     * @return mRealtimeControlled
     */
    public boolean isRealtimeControlled() {
        return mRealtimeControlled;
    }

    /**
     * This is the getter of mOrigin
     * @return mOrigin
     */
    public DestinationNode getOrigin() {
        return mOrigin;
    }

    /**
     * This is the getter of mDestination
     * @return mDestination
     */
    public DestinationNode getDestination() {
        return mDestination;
    }


    /**
     * This is the setter of duration
     * @param duration
     */
    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    /**
     * This is the setter of realtimeControlled
     * @param realtimeControlled
     */
    public void setRealtimeControlled(boolean realtimeControlled) {
        this.mRealtimeControlled = realtimeControlled;
    }

    /**
     * This is the setter of destination
     * @param destination
     */
    public void setDestination(DestinationNode destination) {
        this.mDestination = destination;
    }
}
