/**
 * Copyright (c) 2018. [ Zexin Zhong ]
 *
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
