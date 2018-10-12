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
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sep.UniTrips.presenter.FindPathToSchool;
import com.sep.UniTrips.presenter.Records;
import com.sep.UniTrips.R;

import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;

    // this is used for update trip information in a new thread
    private static final int UPDATE_TRIP_INFORMATION = 1;
    private Handler handler = null;


    private View mView;
    private FloatingActionButton mAddEventFbtn;

    // here we define another button for refresh the current location
    private FloatingActionButton mRefreshLocationFbtn;
    private TextView mStationText;

    private String userTransport = "Train";
    private Location location = null;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
//    public static HomeFragment newInstance(String param1, String param2) {
//        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
////        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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


        // the setting's transportation information
        // the station information which is to show in the form of "station platform time-time"
        mStationText = mView.findViewById(R.id.station_textview);
        // used for new thread communication
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

        location = ((MainActivity) getActivity()).getLocation();
        if (location == null) {
            Toast toast=Toast.makeText(this.getActivity(), "Please enable location usage", Toast.LENGTH_SHORT);
            toast.show();
            return mView;
        }

        // debug
        System.out.println("[Debug in HomeFragement.onCreateView] " + "************** location (" + location.getLatitude() + "," + location.getLongitude() + ")**************");

        // initialize the trip information with transportation manner of userTransport
        userTransport = ((MainActivity) getActivity()).getUserTransport();
        if (userTransport == null) {
            userTransport = "Train";
        }
        // debug
        System.out.println("[Debug in HomeFragement.onCreateView]: " + "************** transport " + userTransport + "**************");
        Thread thread = new UpdateTripInformationThread(location, userTransport);
        thread.start();

        mStationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindTripMapsActivity.class);
                List<double []> coords_double = ((MainActivity) getActivity()).getCoords_double();
                //System.out.println("records string" + Records.coords_double_to_string(coords_double));
                intent.putExtra("coords_string", Records.coords_double_to_string(coords_double));

                startActivity(intent);
            }
        });

        return mView;
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

    public void setUserTransport (String userTransport) {
        this.userTransport = userTransport;
    }


//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
