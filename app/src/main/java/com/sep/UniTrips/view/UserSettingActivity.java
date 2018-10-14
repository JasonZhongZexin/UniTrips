/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sep.UniTrips.R;
import com.sep.UniTrips.model.UserSetting.UserProfile;
import com.sep.UniTrips.model.UserSetting.UserSettingInterface;
import com.sep.UniTrips.presenter.SignUpPresenter;
import com.sep.UniTrips.presenter.UserSettingPresenter;

public class UserSettingActivity extends AppCompatActivity implements UserSettingInterface.view {

    private Spinner mTranportSettingSpinner;
    private Spinner mNotificatationTimeHSpinner;
    private Spinner mNotificatationTimeMSpinner;
    private Spinner mArrivalTimeHSpinner;
    private Spinner mArrivalTimeMSpinner;
    private Button mSaveBtn;
    private UserSettingPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        mPresenter = new UserSettingPresenter(this,this);
        mTranportSettingSpinner = findViewById(R.id.transportSettingSpinner);
        mNotificatationTimeHSpinner = findViewById(R.id.notification_Time_hours_spinner);
        mNotificatationTimeMSpinner = findViewById(R.id.notification_time_min_spinner);
        mArrivalTimeHSpinner = findViewById(R.id.arrival_Time_hours_spinner);
        mArrivalTimeMSpinner = findViewById(R.id.arrival_time_min_spinner);
        mSaveBtn = findViewById(R.id.saveSettingBtn);
        mPresenter.getUserProfile();
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfile newUserProfiel = new UserProfile(mTranportSettingSpinner.getSelectedItem().toString(),
                        Integer.parseInt(mNotificatationTimeHSpinner.getSelectedItem().toString()),
                        Integer.parseInt(mNotificatationTimeMSpinner.getSelectedItem().toString()),
                        Integer.parseInt(mArrivalTimeHSpinner.getSelectedItem().toString()),
                        Integer.parseInt(mArrivalTimeMSpinner.getSelectedItem().toString()));
                mPresenter.setUserProfile(newUserProfiel);
            }
        });
    }


    @Override
    public void initialView(UserProfile userProfile) {
        if(userProfile!=null) {
            int pos = 0;
            switch(userProfile.getPreferredTransport()){
                case "Train": pos=0;
                    break;
                case "":pos=0;
                    break;
                case"Bus":pos = 1;
                    break;
                case"Ferry":pos=2;
                    break;
                case"Light Rail":pos=3;
                    break;
            }
            mTranportSettingSpinner.setSelection(pos);
            pos = userProfile.getNotificationTimeH()+1;
            mNotificatationTimeHSpinner.setSelection(pos);
            pos = userProfile.getNotificationTimeM()/10+1;
            mNotificatationTimeMSpinner.setSelection(pos);
            pos = userProfile.getArrivalTimeH()+1;
            mArrivalTimeHSpinner.setSelection(pos);
            pos = userProfile.getArrivalTimeM()/10+1;
            mArrivalTimeMSpinner.setSelection(pos);
        }
    }
}