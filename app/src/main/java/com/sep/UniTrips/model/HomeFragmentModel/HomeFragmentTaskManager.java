/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.model.HomeFragmentModel;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sep.UniTrips.model.ImportCalendar.Calendar;
import com.sep.UniTrips.model.ImportTransportModel.DestinationNode;
import com.sep.UniTrips.model.ImportTransportModel.JourneysNode;
import com.sep.UniTrips.model.ImportTransportModel.Leg;
import com.sep.UniTrips.model.ImportTransportModel.NswTransportClient;
import com.sep.UniTrips.model.ImportTransportModel.NswTransportServerInterface;
import com.sep.UniTrips.model.ImportTransportModel.TransportDataBeans;
import com.sep.UniTrips.model.UserSetting.UserProfile;
import com.sep.UniTrips.presenter.HomeFragmentPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragmentTaskManager {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private HomeFragmentPresenter mPresenter;
    private Context mContext;
    private LocationManager mLocationManager;
    private String mLocationProvider;
    private String mCurrentLocaiton;
    private static final String UTS_LOCATION = "151.2004942000:-33.8832376000:EPSG:4326";
    private Call<List<Leg>> call;
    private static NswTransportServerInterface sClient;
    private Retrofit retrofit;
    private String preferredTransport;

    public HomeFragmentTaskManager(HomeFragmentPresenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
        this.mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void readCalendars(final HomeFragmentInterface.CalendarDataCallBack callBack) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference ref = mDatabase.child("users").child(currentUser.getUid()).child("Calendars").child("UTS");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar calendar = dataSnapshot.getValue(Calendar.class);
                callBack.onCalendarCallBack(calendar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readTransportOption(final HomeFragmentInterface.UserProfileDataCallBack callBack) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference ref = mDatabase.child("users").child(currentUser.getUid()).child("User Profile");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                callBack.onUserProfileCallBack(userProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getUserLocation() {

        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            mLocationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            mLocationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            //totast display not location provider error
            return;
        }

        //get the location detail
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = mLocationManager.getLastKnownLocation(mLocationProvider);
        if(location!=null){
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            mCurrentLocaiton = String.format("%01.10f:%01.10f:EPSG:4326",longitude,latitude);
        }
    }

    public void getTripDetail(){
        readTransportOption(new HomeFragmentInterface.UserProfileDataCallBack() {
            @Override
            public void onUserProfileCallBack(UserProfile userProfile) {
                getUserLocation();
                preferredTransport = userProfile.getPreferredTransport();
            }
        });
        getTransportDetail();
    }
    public void getTransportDetail(){
        retrofit  = NswTransportClient.getClient(mContext);
        sClient = retrofit.create(NswTransportServerInterface.class);
        switch (preferredTransport) {
            case "Train":
                call = sClient.getTransportData("rapidJSON",
                        "EPSG:4326","dep","coord",mCurrentLocaiton
                        ,"coord",UTS_LOCATION,1,"checkbox",
                        "","1","1","1","1"
                        ,"1","true","10.2.1.42");
                break;
            case "":
                call = sClient.getTransportData("rapidJSON",
                        "EPSG:4326","dep","coord",mCurrentLocaiton
                        ,"coord",UTS_LOCATION,1,"checkbox",
                        "","1","1","1","1"
                        ,"1","true","10.2.1.42");
                break;
            case "Bus":
                call = sClient.getTransportData("rapidJSON",
                        "EPSG:4326","dep","coord",mCurrentLocaiton
                        ,"coord",UTS_LOCATION,1,"checkbox",
                        "1","1","","1","1"
                        ,"1","true","10.2.1.42");
                break;
            case "Ferry":
                call = sClient.getTransportData("rapidJSON",
                        "EPSG:4326","dep","coord",mCurrentLocaiton
                        ,"coord",UTS_LOCATION,1,"checkbox",
                        "1","1","1","1",""
                        ,"1","true","10.2.1.42");
                break;
            case "Light Rail":
                call = sClient.getTransportData("rapidJSON",
                        "EPSG:4326","dep","coord",mCurrentLocaiton
                        ,"coord",UTS_LOCATION,1,"checkbox",
                        "1","","1","1","1"
                        ,"1","true","10.2.1.42");
                break;
        }
        call.enqueue(new Callback<List<Leg>>() {
            @Override
            public void onResponse(Call<List<Leg>> call, Response<List<Leg>> response) {
                if(response.isSuccessful()){
//                            List<JourneysNode> mJourneys = response.body().getJourneys();
                    List<Leg> legs = response.body();
                    DestinationNode origin = legs.get(0).getOrigin();
                    String depatureTime = origin.getDepartureTimeEstimated();
                    String departureInfo = origin.getName();
                    String[] stringArray = departureInfo.split(",");
                    String station = "";
                    String platform = "";
                    Toast.makeText(mContext,origin.getType(),Toast.LENGTH_LONG).show();
                    //due to the problem of the server, sometimes the departure information do not include the
                    //platform data. As a result, before set data, check if the data include platform data
                    if(stringArray.length>1){
                        station = stringArray[0];
                        platform = stringArray[2];
                    }else{
                        station = departureInfo;
                    }
                    //format the departure tiem to the locate time
                    DestinationNode destination = legs.get(0).getDestination();
                    String destionationTime = destination.getArrivalTimeEstimated();
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    TimeZone tz = calendar.getTimeZone();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    try {
                        Date time = simpleDateFormat.parse(depatureTime);
                        SimpleDateFormat localTimeFormat = new SimpleDateFormat("HH:mm");
                        localTimeFormat.setTimeZone(TimeZone.getDefault());
                        String localTime = localTimeFormat.format(time);
                        time = simpleDateFormat.parse(destionationTime);
                        String arrivalTime = localTimeFormat.format(time);
                        //display the data to the view
                        Log.d("TRANSPORT",station+" "+platform+" "+localTime+" "+arrivalTime+" ");
                    } catch (ParseException e) {
                        Log.e("Parse Exception",e.toString());
                    }

                }else{
                    Log.e("ResponseFail","transport server response fail");
                }
            }

            @Override
            public void onFailure(Call<List<Leg>> call, Throwable t) {

            }
        });

    }

}
