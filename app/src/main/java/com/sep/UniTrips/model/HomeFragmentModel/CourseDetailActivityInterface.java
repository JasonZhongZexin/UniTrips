/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */
package com.sep.UniTrips.model.HomeFragmentModel;

public interface CourseDetailActivityInterface {
    interface presenter{
        /**
         * THis method will start a new activity which will show the detail of the clicked course.
         */
        void showCourseDetail();
    }
    interface view{
        /**
         * This method will set the subject description to the layout.
         * @param subjectDescription
         */
        void setSubjectDescription(String subjectDescription);
        /**
         * This method will set the location of the course to the layout.
         * @param location
         */
        void setLocation(String location);
        /**
         * This method will set the duration of the course to the layout.
         * @param duration
         */
        void setDuration(String duration);
        /**
         * This method will set the subjectCode of the course to the layout.
         * @param subjectCode
         */
        void setSubjectCode(String subjectCode);
    }
}
