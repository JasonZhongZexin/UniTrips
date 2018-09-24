/**
 * Copyright (c) 2018. [ Zexin Zhong ]
 *
 */

package com.sep.UniTrips.model.ImportCalendar;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

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

public class ImportCalendarModel {
    private Context mContext;
    private String mStudentId;
    private String mPassword;
    private String mToken;
    private static UTSTimeTableServicesInterface sClient;
    private ImportCalendarPresneter mPresenter;
    private String mJsonData;
    private String mYear;


    /**
     * This is the constructor of the class.
     * @param context
     * @param studentid
     * @param password
     * @param presenter
     */
    public ImportCalendarModel(Context context,String studentid,String password,ImportCalendarPresneter presenter) {
        this.mContext = context;
        this.mStudentId = studentid;
        this.mPassword = password;
        this.mPresenter = presenter;
    }

    /**
     * This method will login to UTS timetable web sever and load the data from the server. Then
     * stored it into the database.
     */
    public void login(){
        // perform the user login attempt.
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
                    List<String> allCookies = headerMapList.get("Set-Cookie");
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
                        //feedback the user when the login detail is wrong
//                        mPresenter.showToast(mContext.getString(R.string.incorrect_login_Detail));
                    }else {
                        //load and stored the timetable data
                        importCalendar();
                    }
                }else{
                    //feedback the user when the login detail is wrong
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
     * This method will get the timetable data from the server and stored it to the database.
     */
    public void importCalendar(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        String cookie = sp.getString("cookie","");
//        mCourseDb = Room.databaseBuilder(mContext,CoursesDb.class,"coursesdb").allowMainThreadQueries().build();
//        mCourseDb.dataAccessObject().refreshTable();
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

                        for(int i = 0;i<scriptArray.length;i++){
                            //check if the string contains the icalUrl
                            if(scriptArray[i].matches(".*attend_type.*")){
                                String[] jsonData=scriptArray[i].split("=");
                                String[] jsonString = jsonData[1].split(";");
                                mJsonData = jsonString[0];


                                Log.e("json data",mJsonData);



                                i = scriptArray.length;
                                String allocatedCourses = ((mJsonData.split("allocated")[1]).split("student_enrolment"))[0];


                                Log.e("allocated acourses",allocatedCourses);


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
                                    //stored the course data to the database
                                    if(courseDetailArrayList.size()>1) {
//                                        Course course = new Course();
//                                        course.setSubject_description(courseDetailArrayList.get(0).toString());
//                                        course.setSubject_code(courseDetailArrayList.get(1).toString());
//                                        course.setActivity_group_code(courseDetailArrayList.get(2).toString());
//                                        course.setActivity_code(courseDetailArrayList.get(3).toString());
//                                        course.setDay_of_week(courseDetailArrayList.get(4).toString());
//                                        course.setStart_time(courseDetailArrayList.get(5).toString());
//                                        course.setLocation(courseDetailArrayList.get(6).toString());
//                                        course.setDuration(courseDetailArrayList.get(7).toString());
//                                        course.setWeek_pattern(courseDetailArrayList.get(8).toString());
//                                        mCourseDb.dataAccessObject().addCourse(course);
                                    }
                                }
                            }
                        }
//                        mPresenter.showToast(mContext.getString(R.string.storedSuccess));
                        mPresenter.finishActivity();
                    } catch (IOException e) {
                        Log.e("IOException",e.toString());
                    }
                }else{
                    //feedback the user with the error message
                    mPresenter.showToast(mContext.getString(R.string.no_response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //feedback the user with the error message
                mPresenter.showToast(mContext.getString(R.string.fail_connection));
            }
        });
    }
}
