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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sep.UniTrips.R;
import com.sep.UniTrips.presenter.ImportCalendarPresneter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * this is the important calendar class which will connect to UTS web timetable server and load calendar data from the server
 */
public class ImportCalendarTaskManager {
    private Context mContext;
    private String mStudentId;
    private String mPassword;
    private String mToken;
    private static UTSTimeTableServicesInterface sClient;
    private ImportCalendarPresneter mPresenter;
    private String mJsonData;
    private String mYear;
    private ArrayList<Course> mCourses;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    /**
     * This is the constructor of the class.
     * @param context
     * @param presenter the
     */
    public ImportCalendarTaskManager(Context context, ImportCalendarPresneter presenter) {
        this.mContext = context;;
        this.mPresenter = presenter;
        mCourses = new ArrayList<Course>();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * This is the student ID validator
     * @param studentID the input student id by the user
     * @return true if the length of the input id is 6 or 8
     */
    public Boolean isStudentId(String studentID){
        return studentID.length()==8|| studentID.length()==6;
    }

    /**
     * This is the password validator
     * @param password the input password by the user
     * @return true if the length of the password greater then 6
     */
    public Boolean isPasswordValid(String password){
        return password.length()>6;

    }

    /**
     *
     */
    public void attemptGetCalendar(String studentID,String password,String year){
        this.mStudentId = studentID;
        this.mPassword = password;
        this.mYear = year;
        boolean cancel = false;

        //reset errors.
        mPresenter.resetError();

        //check if password is empty.
        // set cancel to true if any error happen.
        if(TextUtils.isEmpty(mPassword)){
            mPresenter.setPasswordError(mContext.getString(R.string.error_field_required));
            cancel = true;
        }

        //check for a valid password(length>6), if the user entered one.
        // set cancel to true if any error happen.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPresenter.setPasswordError(mContext.getString(R.string.error_invalid_password));
            cancel = true;
        }

        //check for valid student ID. set cancel to true if any error happen.
        if (TextUtils.isEmpty(mStudentId)) {
            mPresenter.setIDError(mContext.getString(R.string.error_field_required));
            cancel = true;
        } else if (!isStudentId(mStudentId)) {
            mPresenter.setIDError(mContext.getString(R.string.error_invalid_email));
            cancel = true;
        }

        //if cancel, focus the error view else loginToUTS to UTS myTimetable server, get and store the timetable
        //data to firebase database
        if(cancel){
            mPresenter.focusView();
        }else{
            loginToUTS();
        }

    }
    /**
     * This method use the user's input student ID and password login the user to UTS myTimetable
     * server and get the token and cookie if login successful. Then call the stored calendar method
     * to stored the user timetable data.
     */
    public void loginToUTS(){
        // perform the user loginToUTS attempt.
        //create retrofit instance
        Retrofit retrofit = UTSTimetableServicesClient.getClient(mContext);
        //get client & call object for the request
        sClient = retrofit.create(UTSTimeTableServicesInterface.class);
        Call<LoginBeans> call = sClient.login(mYear,mStudentId,mPassword);
        //get the cookie and token
        call.enqueue(new Callback<LoginBeans>() {
            @Override
            public void onResponse(Call<LoginBeans> call, Response<LoginBeans> response) {
                if(response.isSuccessful()){
                    Headers headerResponse = response.headers();
                    Map<String,List<String>> headerMapList = headerResponse.toMultimap();
                    List<String> allCookies = headerMapList.get("set-cookie");
                    String cookiesValue = "";
                    for(String cookies:allCookies){
                        cookiesValue = cookiesValue+cookies + ";";
                    }
                    String [] cookieArray = cookiesValue.split(";");
                    //stored the cookie to the shared preferences
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor eitor = sp.edit();
                    eitor.clear();
                    String cookie = cookieArray[0]+";"+cookieArray[1]+";"+cookieArray[2]+";"+cookieArray[3];
                    eitor.putString("cookie",cookie);
                    eitor.apply();
                    mToken = response.body().getToken();
                    if(mToken==null){
                        //feedback user when fail to login with the user's input student ID and password
                        mPresenter.showToast(mContext.getString(R.string.incorrect_login_Detail));
                    }else {
                        //stored the calendar to firebase
                        storedCalendar();
                    }
                }else{
                    //feedback user when fail to connect to the server
                    mPresenter.showToast(mContext.getString(R.string.fail_connection));
                }
            }

            @Override
            public void onFailure(Call<LoginBeans> call, Throwable t) {
                mPresenter.showToast(mContext.getString(R.string.fail_connection));
            }
        });
    }

    /**
     * get the response body from UTS myTimetable web server. get the courses detail from the response body
     * and stored the calendar to firebase.
     */
    public void storedCalendar(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        String cookie = sp.getString("cookie","");
        //Execution of the call
        Call<ResponseBody> call = sClient.getResponseBody(mYear,mToken,cookie);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    ByteArrayInputStream bais = null;
                    try {
                        bais = new ByteArrayInputStream(((ResponseBody)response.body()).bytes());
                        InputStreamReader reader = new InputStreamReader(bais);
                        BufferedReader in = new BufferedReader(reader);
                        String readed;
                        String html = "";
                        while((readed = in.readLine())!= null){
                            html += readed;
                        }
                        //clean the HTML data and get the json format data from the HTML data
                        String [] htmlArray = html.split("<script>");
                        String [] scriptArray = htmlArray[4].split(";");

                        //clean and get the courses detail string
                        for(int i = 0;i<scriptArray.length;i++){
                            //check if the string contains the icalUrl
                            if(scriptArray[i].matches(".*attend_type.*")){
                                String[] jsonData=scriptArray[i].split("=");
                                String[] jsonString = jsonData[1].split(";");
                                mJsonData = jsonString[0];
                                i = scriptArray.length;
                                String allocatedCourses = ((mJsonData.split("allocated")[1]).split("student_enrolment"))[0];
                                String[] allocatedCoursesArray = allocatedCourses.split("\\},");
                                for(String courseString: allocatedCoursesArray){
                                    String[] courseStringArray = courseString.split(",");
                                    String detailString = "";
                                    //get the course attributes data
                                    for(String detail :courseStringArray){
                                        if(detail.matches(".*subject_description.*")||
                                                detail.matches(".*subject_code.*")||
                                                detail.matches(".*activity_group_code.*")||
                                                detail.matches(".*activity_code.*")||
                                                detail.matches(".*day_of_week.*")||
                                                detail.matches(".*location.*")||
                                                detail.matches(".*duration.*")||
                                                detail.matches(".*week_pattern.*")){
                                            String detailArray[] = detail.split(":");
                                            detailString += detailArray[detailArray.length-1];}else if(detail.matches(".*start_time.*")){
                                            String detailArray[] = detail.split("\"");
                                            detailString += detailArray[detailArray.length-1];
                                        }
                                    }
                                    String[] courseDetailArray = detailString.split("\"");
                                    ArrayList courseDetailArrayList = new ArrayList();
                                    for(String courseDetail: courseDetailArray){
                                        if(courseDetail.length()!=0){
                                            courseDetailArrayList.add(courseDetail);
                                        }
                                    }
                                    //add course to courses list
                                    if(courseDetailArrayList.size()>1) {
                                        Course course = new Course();
                                        course.setSubject_description(courseDetailArrayList.get(0).toString());
                                        course.setSubject_code(courseDetailArrayList.get(1).toString());
                                        course.setActivity_group_code(courseDetailArrayList.get(2).toString());
                                        course.setActivity_code(courseDetailArrayList.get(3).toString());
                                        course.setDay_of_week(courseDetailArrayList.get(4).toString());
                                        course.setStart_time(courseDetailArrayList.get(5).toString());
                                        course.setLocation(courseDetailArrayList.get(6).toString());
                                        course.setDuration(courseDetailArrayList.get(7).toString());
                                        course.setWeek_pattern(courseDetailArrayList.get(8).toString());
                                        mCourses.add(course);
                                    }
                                }
                            }
                        }
//                        mPresenter.showToast(mContext.getString(R.string.storedSuccess));
//                        mPresenter.finishActivity();
                        if(mCourses.size()>0){
                            Calendar calendar = new Calendar(mCourses,"UTS");
                            final FirebaseUser currentUser = mAuth.getCurrentUser();
                            mDatabase.child("users").child(currentUser.getUid()).child("Calendars").child(calendar.getCalendarName()).setValue(calendar).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mPresenter.updateUI();
                                    mPresenter.showToast(mContext.getString(R.string.firebase_Timetable_upload_successful));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mPresenter.showToast(mContext.getString(R.string.firebase_Timetable_upload_fail));
                                }
                            });
                        }
                    } catch (IOException e) {
                        Log.e("IOException",e.toString());
                    }
                }else{
                    //feedback the user with the error message when fail to get the server response
                    mPresenter.showToast(mContext.getString(R.string.no_response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //feedback the user with the error message when fail to connect to the web server
                mPresenter.showToast(mContext.getString(R.string.fail_connection));
            }
        });
    }
}
