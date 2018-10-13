/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.model.HomeFragmentModel;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.sep.UniTrips.model.ImportCalendar.Calendar;
import com.sep.UniTrips.model.ImportCalendar.Course;
import com.sep.UniTrips.presenter.HomeFragmentPresenter;
import com.sep.UniTrips.view.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragmentTaskManager {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private HomeFragmentPresenter mPresenter;
    private Context mContext;
    private Calendar mCalendarData;

    public HomeFragmentTaskManager(HomeFragmentPresenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
        this.mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void readCalendars(final HomeFragmentInterface.DataCallBack callBack){
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
}
