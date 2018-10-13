/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.model.HomeFragmentModel;

import java.util.ArrayList;
import java.util.Calendar;

public interface HomeFragmentInterface {
    interface View{
        void showCourseTextView(String viewTag,int color);
        void setSubjectDescription(String viewSubjectDescriptionTag, String subject_description);
        void setLocation(String locationViewTag, String location);
    }
    interface Presenter{
        void showCourseData();
    }
    interface DataCallBack{
        void onCalendarCallBack(com.sep.UniTrips.model.ImportCalendar.Calendar calendar);
    }
}
