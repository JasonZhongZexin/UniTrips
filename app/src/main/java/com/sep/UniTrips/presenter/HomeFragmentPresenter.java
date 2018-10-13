/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.presenter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.sep.UniTrips.model.HomeFragmentModel.HomeFragmentInterface;
import com.sep.UniTrips.model.HomeFragmentModel.HomeFragmentTaskManager;
import com.sep.UniTrips.model.ImportCalendar.Calendar;
import com.sep.UniTrips.model.ImportCalendar.Course;
import com.sep.UniTrips.view.HomeFragment;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragmentPresenter implements HomeFragmentInterface.Presenter{

    private HomeFragment mHomeFragmentView;
    private Context mContext;
    private Calendar mCalendar;
    private HomeFragmentTaskManager mTaskManager;
    private ArrayList<Course> weeklyCourse = new ArrayList<>();

    public HomeFragmentPresenter(Context context,HomeFragment homeFragment) {
        this.mHomeFragmentView = homeFragment;
        this.mContext = context;
        mTaskManager = new HomeFragmentTaskManager(this,mContext);
    }

    @Override
    public void showCourseData() {
        mTaskManager.readCalendars(new HomeFragmentInterface.DataCallBack() {
            @Override
            public void onCalendarCallBack(Calendar calendar) {
                if(calendar!=null){
                    java.util.Calendar androidCalendar = java.util.Calendar.getInstance();
                    int currentWeekNumber = androidCalendar.get(java.util.Calendar.WEEK_OF_YEAR);
                    for(Course course:calendar.getCourses()){
                        if((course.getWeek_pattern().charAt(currentWeekNumber))=='1')
                            weeklyCourse.add(course);
                    }
                    for(int i=0;i<weeklyCourse.size();i++) {
                        String date = weeklyCourse.get(i).getDay_of_week();
                        int duration = Integer.parseInt(weeklyCourse.get(i).getDuration())/30;
                        String startTimeFormatted = (weeklyCourse.get(i).getStart_time()).replaceAll(":",".");
                        String startTime = startTimeFormatted.replaceAll(".30",".5");
                        Double tagDouble = Double.parseDouble(startTime);
                        String subjectDescriptionViewTag = date+tagDouble;
                        mHomeFragmentView.setSubjectDescription(subjectDescriptionViewTag,weeklyCourse.get(i)
                                .getSubject_description());
                        String locationViewTag = date+(tagDouble+0.5);
                        mHomeFragmentView.setLocation(locationViewTag,weeklyCourse.get(i).getLocation());
                        Random rnd = new Random();
                        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        for(int j=0;j<duration;j++){
                            String viewTag = date+tagDouble;
                            mHomeFragmentView.showCourseTextView(viewTag,color);
                            //mHomeFragmentView.setOnClickListener(viewTag,weeklyCourse.get(i));
                            tagDouble = Double.parseDouble(startTime);
                            tagDouble += 0.50;
                            startTime = tagDouble.toString();
                        }
                    }
                }
            }
        });
    }

}
