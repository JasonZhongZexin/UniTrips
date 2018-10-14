/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */
package com.sep.UniTrips.presenter;

import android.content.Context;
import android.content.Intent;

import com.sep.UniTrips.model.HomeFragmentModel.CourseDetailActivityInterface;
import com.sep.UniTrips.view.CourseDetailActivity;

public class CourseDetailPresenter implements CourseDetailActivityInterface.presenter {

    public Context mContext;
    public Intent mIntent;
    public CourseDetailActivity mCourseDetailView;

    /**
     * This is the constructor of the class which initiate the fields with the given data.
     * @param context
     * @param intent
     * @param courseDetailActivity
     */
    public CourseDetailPresenter(Context context, Intent intent, CourseDetailActivity courseDetailActivity) {
        this.mContext = context;
        this.mIntent = intent;
        mCourseDetailView = courseDetailActivity;
    }

    @Override
    public void showCourseDetail(){
        String subject_description = mIntent.getStringExtra(HomeFragmentPresenter.SUBJECT_DESCRIPTION);
        String duration = mIntent.getStringExtra(HomeFragmentPresenter.DURATION);
        String location = mIntent.getStringExtra(HomeFragmentPresenter.LOCATION);
        String subject_code = mIntent.getStringExtra(HomeFragmentPresenter.SUBJECT_CODE);
        String activity_group = mIntent.getStringExtra(HomeFragmentPresenter.ACTIVITY_GROUP);
        String day_of_Week = mIntent.getStringExtra(HomeFragmentPresenter.DATE_OF_WEEK);
        String startTime = mIntent.getStringExtra(HomeFragmentPresenter.START_TIME);
        mCourseDetailView.setDuration(day_of_Week+" "+startTime+" ( "+duration+" )");
        mCourseDetailView.setSubjectDescription(subject_description+", "+activity_group);
        mCourseDetailView.setLocation(location);
        mCourseDetailView.setSubjectCode(subject_code+","+subject_description);
    }


}
