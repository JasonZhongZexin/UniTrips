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


package com.sep.UniTrips.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sep.UniTrips.model.HomeFragmentModel.HomeFragmentInterface;
import com.sep.UniTrips.model.ImportCalendar.Course;
import com.sep.UniTrips.model.UserSetting.UserProfile;
import com.sep.UniTrips.presenter.FindPathToSchool;
import com.sep.UniTrips.presenter.HomeFragmentPresenter;
import com.sep.UniTrips.presenter.Records;
import com.sep.UniTrips.R;

import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment implements HomeFragmentInterface.View {
    // this is used for update trip information in a new thread
    private static final int UPDATE_TRIP_INFORMATION = 1;
    private Handler handler = null;
    private View mView;
    private FloatingActionButton mAddEventFbtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView mStationText;
    private String userTransport = "Train";
    private Location location = null;
    private HomeFragmentPresenter mPresenter;

    public HomeFragment() {
        // Required empty public constructor
        mPresenter = new HomeFragmentPresenter(getActivity(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) throws NullPointerException{
        // Inflate the layout for this fragment
        mView =inflater.inflate(R.layout.fragment_home, container, false);
        mAddEventFbtn = mView.findViewById(R.id.addEventBtn);
        mAddEventFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEventActivity.class);
                startActivity(intent);
            }
        });
        this.mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // the setting's transportation information
        // the station information which is to show in the form of "station platform time-time"
        mStationText = mView.findViewById(R.id.station_textview);
        // used for new thread communication
        try{
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case UPDATE_TRIP_INFORMATION:
                            Records trip_information = (Records) msg.obj;
                            mStationText.setText(trip_information.station_info);
                            ((MainActivity) getActivity()).setCoords_double(trip_information.coords_double);
                            break;
                    }
                }
            };
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        location = ((MainActivity) getActivity()).getLocation();
        if (location == null) {
            Toast toast=Toast.makeText(this.getActivity(), "Please enable location usage", Toast.LENGTH_SHORT);
            toast.show();
            return mView;
        }

        // initialize the trip information with transportation manner of userTransport
        userTransport = ((MainActivity) getActivity()).getUserTransport();
        userTransport = null;
        if (userTransport == null) {
            userTransport = ((MainActivity) getActivity()).getUserTransport();
        }
        Thread thread = new UpdateTripInformationThread(location, userTransport);
        thread.start();

        mStationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindTripMapsActivity.class);
                List<double []> coords_double = ((MainActivity) getActivity()).getCoords_double();
                intent.putExtra("coords_string", Records.coords_double_to_string(coords_double));

                startActivity(intent);
            }
        });

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.showCourseData();
    }

    @Override
    public void showCourseTextView(String viewTag, int color) {
        TextView textView = mView.findViewWithTag(viewTag);
        textView.setBackgroundColor(color);
    }

    @Override
    public void setSubjectDescription(String viewSubjectDescriptionTag, String subject_description) {
        TextView textView_title = mView.findViewWithTag(viewSubjectDescriptionTag);
        textView_title.setTextColor(getActivity().getApplication().getApplicationContext().getColor(R.color.textColorWhite));
        textView_title.setText(subject_description);
        textView_title.setTypeface(null, Typeface.BOLD);
    }

    @Override
    public void setLocation(String locationViewTag, String location) {
        TextView textView_location = mView.findViewWithTag(locationViewTag);
        textView_location.setTextColor(getActivity().getApplication().getApplicationContext().getColor(R.color.textColorWhite));
        textView_location.setText(location);
        textView_location.setTypeface(null, Typeface.BOLD);
    }

    @Override
    public void setOnClickListener(String viewTag, final Course course) {
        TextView textView = mView.findViewWithTag(viewTag);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.displayCourseDetail(course,getActivity());
            }
        });
    }

    /**
     * The thread used to update trip information
     */
    class UpdateTripInformationThread extends Thread {

        private String transportation = "Train";
        private Location location = null;
        private double [] location_double = new double[2];

        public UpdateTripInformationThread (Location location, String transportation) {

            this.transportation = transportation;
            this.location = location;
            this.location_double[0] = location.getLatitude();
            this.location_double[1] = location.getLongitude();
        }

        @Override
        public void run() {

            Records trip_infomation = FindPathToSchool.getTripInformation(location_double, transportation);

            Message msg = new Message();
            msg.what = UPDATE_TRIP_INFORMATION;
            msg.obj = trip_infomation;

            handler.sendMessage(msg);
        }
    }

    /**
     * new debug testing
     * @param userTransport
     */
    public void setUserTransport (String userTransport) {
        this.userTransport = userTransport;
    }
}