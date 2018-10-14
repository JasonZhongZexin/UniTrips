/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */
package com.sep.UniTrips.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sep.UniTrips.R;
import com.sep.UniTrips.presenter.CourseDetailPresenter;

/**
 * This is the course detail activity, it will display the detail of the choosing course.
 */
public class CourseDetailActivity extends AppCompatActivity {

    private CourseDetailPresenter mCourseDetailPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Intent intent = getIntent();
        mCourseDetailPresent = new CourseDetailPresenter(this,intent,this);
        mCourseDetailPresent.showCourseDetail();
    }

    /**
     * This mehtod will set the given subject destination to the textview
     * @param subjectDescription
     */
    public void setSubjectDescription(String subjectDescription){
        TextView textview = findViewById(R.id.subject_description_tv);
        textview.setText(subjectDescription);
    }

    /**
     * This method will set the given location to the textview
     * @param location
     */
    public void setLocation(String location){
        TextView textview = findViewById(R.id.location_tv);
        textview.setText(location);
    }

    /**
     * This method will set the given duration to the textview
     * @param duration
     */
    public void setDuration(String duration){
        TextView textview = findViewById(R.id.duration_tv);
        textview.setText(duration);
    }

    /**
     * This methos will set the subject code to the textview
     * @param subjectCode
     */
    public void setSubjectCode(String subjectCode){
        TextView textview = findViewById(R.id.subject_code_tv);
        textview.setText(subjectCode);
    }
}
