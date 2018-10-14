/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.model.ImportCalendar;

import java.util.ArrayList;

/**
 * this is the calendar model java class whcih allow to convert the json format timetable data to an calendar object
 */
public class Calendar {

    private ArrayList<Course> mCourses;
    private String mCalendarName;

    public Calendar() {
    }

    public Calendar(ArrayList<Course> courses,String calendarName) {
        this.mCourses = courses;
        this.mCalendarName = calendarName;
    }

    public ArrayList<Course> getCourses() {
        return mCourses;
    }

    public void setCourses(ArrayList<Course> mCourses) {
        this.mCourses = mCourses;
    }

    public void setCalendarName(String mCalendarName) {
        this.mCalendarName = mCalendarName;
    }

    public String getCalendarName() {

        return mCalendarName;
    }
}
