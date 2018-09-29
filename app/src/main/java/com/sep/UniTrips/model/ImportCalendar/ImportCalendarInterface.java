/**
 * Copyright (c) 2018. [Zexin Zhong]
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.model.ImportCalendar;

public interface ImportCalendarInterface {
    interface Presenter{
        void login(String year, String id, String password);
        Boolean isStudentId(String studentId);
        Boolean isPasswordValid(String password);
        void checkId_password();
        void showSnackBar(String errorMessage);
        void showToast(String errorMessage);
        void finishActivity();
    }

    interface View{

    }
}
