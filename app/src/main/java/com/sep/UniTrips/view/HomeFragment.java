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

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sep.UniTrips.R;
import com.sep.UniTrips.model.HomeFragmentModel.HomeFragmentInterface;
import com.sep.UniTrips.model.ImportCalendar.Course;
import com.sep.UniTrips.presenter.HomeFragmentPresenter;


public class HomeFragment extends Fragment implements HomeFragmentInterface.View {

//    private OnFragmentInteractionListener mListener;
    private View mView;
    private FloatingActionButton mAddEventFbtn;
    private HomeFragmentPresenter mPresenter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =inflater.inflate(R.layout.fragment_home, container, false);
        mAddEventFbtn = mView.findViewById(R.id.addEventBtn);
        mAddEventFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), AddEventActivity.class);
//                startActivity(intent);
                mPresenter.getRealTimeTransportInfo();
            }
        });
        mPresenter = new HomeFragmentPresenter(getActivity(),this);
        //get and show the course data
        mPresenter.showCourseData();
//        //get and show the transport information
//        mPresenter.getRealTimeTransportInfo();

        return mView;
    }

    @Override
    public void showCourseTextView(String viewTag,int color) {
        TextView textView = mView.findViewWithTag(viewTag);
        textView.setBackgroundColor(color);
    }

    @Override
    public void setSubjectDescription(String viewSubjectDescriptionTag, String subject_description) {
        TextView textView_title = mView.findViewWithTag(viewSubjectDescriptionTag);
        textView_title.setTextColor(getResources().getColor(R.color.textColorWhite));
        textView_title.setText(subject_description);
        textView_title.setTypeface(null, Typeface.BOLD);
    }

    @Override
    public void setLocation(String locationViewTag, String location) {
        TextView textView_location = mView.findViewWithTag(locationViewTag);
        textView_location.setTextColor(getResources().getColor(R.color.textColorWhite));
        textView_location.setText(location);
        textView_location.setTypeface(null, Typeface.BOLD);
    }

    @Override
    public void showTransportDetail(String station, String platform, String departureTime, String arrivalTime, String preferredTransport) {
        ImageView iconView = mView.findViewById(R.id.transport_icon_imageView);
        TextView stationTv = mView.findViewById(R.id.station_textview);
        TextView platFormTv = mView.findViewById(R.id.platform_textview);
        TextView timeTv = mView.findViewById(R.id.time_textView);
        LinearLayout transportLayout =mView.findViewById(R.id.transport_layout);
        stationTv.setText(station);
        platFormTv.setText(platform);
        timeTv.setText(departureTime+"-"+arrivalTime);
        switch(preferredTransport){
            case "":
                iconView.setImageResource(R.drawable.train_icon);
                transportLayout.setBackgroundColor(getResources().getColor(R.color.trainColor));
                break;
            case "Train":
                iconView.setImageResource(R.drawable.train_icon);
                transportLayout.setBackgroundColor(getResources().getColor(R.color.trainColor));
                break;
            case"Bus":
                iconView.setImageResource(R.drawable.ic_bus_24dp);
                transportLayout.setBackgroundColor(getResources().getColor(R.color.busColor));
                break;
            case"Ferry":
                iconView.setImageResource(R.drawable.ic_ferry_24dp);
                transportLayout.setBackgroundColor(getResources().getColor(R.color.ferrycolor));
                break;
            case"Light Rail":
                iconView.setImageResource(R.drawable.ic_light_rail_24dp);
                transportLayout.setBackgroundColor(getResources().getColor(R.color.raillightColor));
                break;
        }
    }

    public void setOnClickListener(String viewTag, final Course course) {
//        TextView textView = mView.findViewWithTag(viewTag);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.displayCourseDetail(course,getActivity());
//            }
//        });
    }
}
