/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */
package com.sep.UniTrips.model.ImportCalendar;
import android.support.annotation.NonNull;


public class Course {

    private int mId;
    private String mSubject_description;
    private String mSubject_code;
    private String mActivity_group_code;
    private String mActivity_code;
    private String mDay_of_week;
    private String mStart_time;
    private String mLocation;
    private String mWeek_pattern;
    private String mDuration;

    public Course(int mId, String mSubject_description, String mSubject_code, String mActivity_group_code, String mActivity_code, String mDay_of_week, String mStart_time, String mLocation, String mWeek_pattern, String mDuration) {
        this.mId = mId;
        this.mSubject_description = mSubject_description;
        this.mSubject_code = mSubject_code;
        this.mActivity_group_code = mActivity_group_code;
        this.mActivity_code = mActivity_code;
        this.mDay_of_week = mDay_of_week;
        this.mStart_time = mStart_time;
        this.mLocation = mLocation;
        this.mWeek_pattern = mWeek_pattern;
        this.mDuration = mDuration;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmSubject_description(String mSubject_description) {
        this.mSubject_description = mSubject_description;
    }

    public void setmSubject_code(String mSubject_code) {
        this.mSubject_code = mSubject_code;
    }

    public void setmActivity_group_code(String mActivity_group_code) {
        this.mActivity_group_code = mActivity_group_code;
    }

    public void setmActivity_code(String mActivity_code) {
        this.mActivity_code = mActivity_code;
    }

    public void setmDay_of_week(String mDay_of_week) {
        this.mDay_of_week = mDay_of_week;
    }

    public void setmStart_time(String mStart_time) {
        this.mStart_time = mStart_time;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public void setmWeek_pattern(String mWeek_pattern) {
        this.mWeek_pattern = mWeek_pattern;
    }

    public void setmDuration(String mDuration) {
        this.mDuration = mDuration;
    }

    public int getmId() {

        return mId;
    }

    public String getmSubject_description() {
        return mSubject_description;
    }

    public String getmSubject_code() {
        return mSubject_code;
    }

    public String getmActivity_group_code() {
        return mActivity_group_code;
    }

    public String getmActivity_code() {
        return mActivity_code;
    }

    public String getmDay_of_week() {
        return mDay_of_week;
    }

    public String getmStart_time() {
        return mStart_time;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmWeek_pattern() {
        return mWeek_pattern;
    }

    public String getmDuration() {
        return mDuration;
    }
}
