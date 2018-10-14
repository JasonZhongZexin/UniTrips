/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.presenter;

import android.content.Context;

import com.sep.UniTrips.model.UserSetting.UserProfile;
import com.sep.UniTrips.model.UserSetting.UserSettingInterface;
import com.sep.UniTrips.model.UserSetting.UserSettingTaskManager;
import com.sep.UniTrips.view.UserSettingActivity;

public class UserSettingPresenter implements UserSettingInterface.presenter{

    private Context mConetxt;
    private UserSettingActivity mUserSettingActivity;
    private UserSettingTaskManager mTaskManager;

    public UserSettingPresenter(Context context,UserSettingActivity userSettingActivity) {
        this.mConetxt = context;
        this.mUserSettingActivity = userSettingActivity;
        mTaskManager = new UserSettingTaskManager(this,mConetxt);
    }


    @Override
    public void setUserProfile(UserProfile userProfile) {
        mTaskManager.setUserProfile(userProfile);
    }

    @Override
    public void getUserProfile() {
        mTaskManager.getUserProfile();
    }

    @Override
    public void updateView(Boolean isSuccess) {
        mUserSettingActivity.updateView(isSuccess);
    }

    public void initialView(UserProfile userProfile){
        mUserSettingActivity.initialView(userProfile);
    }
}
