/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.model.AddEventModel;

/**
 * THIS IS the event java bean which allow user to Package the event detail into an object.
 */
public class Event {

    private String mTitle;
    private String mStartTime;
    private String mEndTime;
    private String mLocation;
    private String mNotificationTime;

    public Event(String mTitle, String mStartTime, String mEndTime, String mLocation, String mNotificationTime) {
        this.mTitle = mTitle;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mLocation = mLocation;
        this.mNotificationTime = mNotificationTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public void setEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public void setNotificationTime(String mNotificationTime) {
        this.mNotificationTime = mNotificationTime;
    }

    public String getNotificationTime() {
        return mNotificationTime;
    }
}
