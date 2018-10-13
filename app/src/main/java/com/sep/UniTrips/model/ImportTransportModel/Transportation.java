/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.model.ImportTransportModel;

import com.google.gson.annotations.SerializedName;

public class Transportation {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("disassembledName")
    private String disassembledName;
    @SerializedName("number")
    private String number;
    @SerializedName("iconID")
    private Long iconID;
    @SerializedName("description")
    private String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisassembledName() {
        return disassembledName;
    }

    public String getNumber() {
        return number;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisassembledName(String disassembledName) {
        this.disassembledName = disassembledName;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setIconID(Long iconID) {
        this.iconID = iconID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIconID() {
        return iconID;
    }

    public String getDescription() {
        return description;
    }
}
