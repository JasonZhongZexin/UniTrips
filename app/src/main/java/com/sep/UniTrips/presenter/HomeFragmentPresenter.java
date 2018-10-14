/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.sep.UniTrips.R;
import com.sep.UniTrips.model.HomeFragmentModel.HomeFragmentInterface;
import com.sep.UniTrips.model.HomeFragmentModel.HomeFragmentTaskManager;
import com.sep.UniTrips.model.ImportCalendar.Calendar;
import com.sep.UniTrips.model.ImportCalendar.Course;
import com.sep.UniTrips.view.CourseDetailActivity;
import com.sep.UniTrips.view.HomeFragment;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragmentPresenter implements HomeFragmentInterface.Presenter{

    private HomeFragment mHomeFragmentView;
    private Context mContext;
    private Calendar mCalendar;
    private HomeFragmentTaskManager mTaskManager;
    private ArrayList<Course> weeklyCourse = new ArrayList<>();
    public static final String SUBJECT_DESCRIPTION = "subject_description";
    public static final String ACTIVITY_GROUP = "activity_group";
    public static final String LOCATION = "location";
    public static final String SUBJECT_CODE = "subject_code";
    public static final String DURATION = "duration";
    public static final String DATE_OF_WEEK = "dateOfWeek";
    public static final String START_TIME = "startTime";

    public HomeFragmentPresenter(Context context,HomeFragment homeFragment) {
        this.mHomeFragmentView = homeFragment;
        this.mContext = context;
        mTaskManager = new HomeFragmentTaskManager(this,mContext);
    }

    @Override
    public void showCourseData() {
        mTaskManager.readCalendars(new HomeFragmentInterface.CalendarDataCallBack() {
            @Override
            public void onCalendarCallBack(Calendar calendar) {
                if(calendar!=null){
                    java.util.Calendar androidCalendar = java.util.Calendar.getInstance();
                    int currentWeekNumber = androidCalendar.get(java.util.Calendar.WEEK_OF_YEAR);
                    for(Course course:calendar.getCourses()){
                        if((course.getWeek_pattern().charAt((currentWeekNumber)-1))=='1')
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
                            mHomeFragmentView.setOnClickListener(viewTag,weeklyCourse.get(i));
                            tagDouble = Double.parseDouble(startTime);
                            tagDouble += 0.50;
                            startTime = tagDouble.toString();
                        }
                    }
                }
            }
        });
    }
    @Override
    public void displayCourseDetail(Course course,Context context) {
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra(SUBJECT_DESCRIPTION,course.getSubject_description());
        intent.putExtra(ACTIVITY_GROUP,course.getActivity_group_code());
        intent.putExtra(LOCATION,course.getLocation());
        intent.putExtra(SUBJECT_CODE,course.getSubject_code());
        intent.putExtra(DATE_OF_WEEK,course.getDay_of_week());
        intent.putExtra(START_TIME,course.getStart_time());
        double duration = Double.parseDouble(course.getDuration())/60;
        intent.putExtra(DURATION,duration+" "+context.getResources().getString(R.string.hours));
        context.startActivity(intent);
    }
}
