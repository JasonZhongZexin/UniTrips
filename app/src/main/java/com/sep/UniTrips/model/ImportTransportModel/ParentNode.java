/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */
package com.sep.UniTrips.model.ImportTransportModel;

import com.google.gson.annotations.SerializedName;

public class ParentNode {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("disassembledName")
    private String mDisassembledName;
    @SerializedName("type")
    private String mType;

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
     * This is the setter of disassembledName
     * @param disassembledName
     */
    public void setDisassembledName(String disassembledName) {
        this.mDisassembledName = disassembledName;
    }

    /**
     * This is the setter of type
     * @param type
     */
    public void setType(String type) {
        this.mType = type;
    }

    /**
     * This is the getter of id
     * @return id
     */
    public String getId() {

        return mId;
    }


    /**
     * This is the getter of mName
     * @return mName
     */
    public String getName() {
        return mName;
    }

    /**
     * This is the getter of mDisassembledName
     * @return mDisassembledName
     */
    public String getDisassembledName() {
        return mDisassembledName;
    }

    /**
     * This is the getter of mType
     * @return mType
     */
    public String getType() {
        return mType;
    }
}
