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


package com.sep.UniTrips.presenter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.sep.UniTrips.model.ImportCalendar.Course;
import com.sep.UniTrips.model.ImportCalendar.ImportCalendarInterface;
import com.sep.UniTrips.model.ImportCalendar.ImportCalendarTaskManager;
import com.sep.UniTrips.view.ImportCalendarActivity;

import java.util.ArrayList;

/**
 * this is the import calendar presenter view which will allow user to import the calendar to the web
 * server
 * */
public class ImportCalendarPresneter implements ImportCalendarInterface.Presenter {

    private ImportCalendarTaskManager mImportCalendarTaskManager;
    private Context mContext;
    private ImportCalendarActivity mImportCalendarView;

    public ImportCalendarPresneter(Context context,ImportCalendarActivity importCalendarView) {
        this.mContext = context;
        this.mImportCalendarView = importCalendarView;
       this.mImportCalendarTaskManager = new ImportCalendarTaskManager(mContext,this);
    }

    @Override
    public void attemptGetCalendar(String id, String password, String year) {
        mImportCalendarTaskManager.attemptGetCalendar(id,password,year);
    }

    /**
     * diaplay the error message int he toast
     * @param message
     */
    @Override
    public void showToast(String message) {
        mImportCalendarView.showToast(message);
    }

    /**
     * reset the view error to null
     */
    @Override
    public void resetError() {
        mImportCalendarView.resetError();
    }

    /**
     * set error message to the password view
     * @param errorMessage
     */
    @Override
    public void setPasswordError(String errorMessage) {
        mImportCalendarView.setPasswordError(errorMessage);
    }

    /**
     * sert error message to the id
     * @param errorMessage
     */
    @Override
    public void setIDError(String errorMessage) {
        mImportCalendarView.setIDError(errorMessage);
    }

    /**
     * requet to focus the view
     */
    @Override
    public void focusView() {
        mImportCalendarView.focusView();
    }

    /**
     * update ui base on the current user
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void updateUI() {
        mImportCalendarView.updateUI();
    }

}
