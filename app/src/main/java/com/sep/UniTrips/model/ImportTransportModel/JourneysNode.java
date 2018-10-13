/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */
package com.sep.UniTrips.model.ImportTransportModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JourneysNode {

    @SerializedName("legs")
    private List<Leg> legs;
    @SerializedName("rating")
    private long rating;
    @SerializedName("isAdditional")
    private boolean isAdditional;
    @SerializedName("interchanges")
    private long interchanges;

    /**
     * This method will return the list of legs
     * @return List<Leg>
     */
    public List<Leg> getLegs() {
        return legs;
    }

    public long getRating() {
        return rating;
    }

    public boolean isAdditional() {
        return isAdditional;
    }

    public long getInterchanges() {
        return interchanges;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public void setAdditional(boolean additional) {
        isAdditional = additional;
    }

    public void setInterchanges(long interchanges) {
        this.interchanges = interchanges;
    }

    /**
     * This method will set the legs to the given legs
     * @param legs
     */
    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }
}
