/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */
package com.sep.UniTrips.model.ImportTransportModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransportDataBeans {

    @SerializedName("journeys")
    private List<JourneysNode> mJourneys;

    public String getVersion() {
        return version;
    }

    @SerializedName("version")
    private String version;

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * This is the setter of journeys
     * @param journeys
     */
    public void setJourneys(List<JourneysNode> journeys) {
        this.mJourneys = journeys;
    }

    /**
     * THis methos is the getter of journeys
     * @return
     */
    public List<JourneysNode> getJourneys() {

        return mJourneys;
    }
}
