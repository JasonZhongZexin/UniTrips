/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.model.UserSetting;

/**
 * this is the user profile java node
 */
public class UserProfile {

    private String mPreferredTransport;
    private int mNotificationTimeH;
    private int mArrivalTimeH;
    private int mNotificationTimeM;
    private int mArrivalTimeM;

    public UserProfile() {
        this.mArrivalTimeH = 1;
        this.mNotificationTimeH = 1;
        this.mArrivalTimeM = 0;
        this.mNotificationTimeM=0;
        this.mPreferredTransport = "";
    }

    /**
     * this is the method of hte noptification time
     * @param notificationTimeM
     */
    public void setNotificationTimeM(int notificationTimeM) {
        mNotificationTimeM = notificationTimeM;
    }

    /**
     * this is the setting method of the arrival time
     * @param arrivalTimeM
     */
    public void setArrivalTimeM(int arrivalTimeM) {
        mArrivalTimeM = arrivalTimeM;
    }

    /**
     * this is the notification getter method
     * @return
     */
    public int getNotificationTimeM() {

        return mNotificationTimeM;
    }

    /**
     * this is the arrivcal tiome getter method
     * @return
     */
    public int getArrivalTimeM() {
        return mArrivalTimeM;
    }


    public UserProfile(String preferredTransport, int notificationTimeH, int notificationTimeM, int arrivalTimeH, int arrivalTimeM) {
        this.mPreferredTransport = preferredTransport;
        this.mNotificationTimeH = notificationTimeH;
        this.mArrivalTimeH = arrivalTimeH;
        this.mArrivalTimeM = arrivalTimeM;
        this.mNotificationTimeM = notificationTimeM;

    }

    /**
     * this is nthe setperferred transoprt mehod
     * @param preferredTransport
     */
    public void setPreferredTransport(String preferredTransport) {
        this.mPreferredTransport = preferredTransport;
    }

    /**
     * this is th e set notification setter
     * @param notificationTimeH
     */
    public void setNotificationTimeH(int notificationTimeH) {
        this.mNotificationTimeH = notificationTimeH;
    }

    /**
     * this is the arrival setting setter
     */
    public void setArrivalTimeH(int arrtivalTime) {
        this.mArrivalTimeH = arrtivalTime;
    }

    /**
     * this is th e getter of the mPreferredTransport
     *
     */
    public String getPreferredTransport() {

        return mPreferredTransport;
    }

    /**
     *  thi is the notification hours getter
     * @return
     */
    public int getNotificationTimeH() {
        return mNotificationTimeH;
    }

    public int getArrivalTimeH() {
        return mArrivalTimeH;
    }
}
