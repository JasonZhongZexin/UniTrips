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


package com.sep.UniTrips.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.sep.UniTrips.model.SignInModel.LoginInterface;
import com.sep.UniTrips.model.SignInModel.LoginTaskManager;
import com.sep.UniTrips.view.SignInActivity;

public class LoginPresenter implements LoginInterface.presenter{

    private SignInActivity mSignInActivity;
    private Context mContext;
    private LoginTaskManager mTaskManager;

    public LoginPresenter(Context context, SignInActivity signInActivity){
        this.mContext = context;
        this.mSignInActivity = signInActivity;
        this.mTaskManager = new LoginTaskManager(mContext,this);
    }


    @Override
    public void restError() {
        mSignInActivity.restError();
    }

    @Override
    public void setEmailError(String errorMessage) {
        mSignInActivity.setEmailError(errorMessage);
    }

    @Override
    public void setPasswordError(String errorMessage) {
        mSignInActivity.setPasswordError(errorMessage);
    }

    @Override
    public void focusView() {
        mSignInActivity.focusView();
    }

    @Override
    public void updateUI(FirebaseUser user) {
        mSignInActivity.updateUI(user);
    }

    @Override
    public void attemptLogin(String email, String password) {
        mTaskManager.attemptLogin(email,password);
    }

    @Override
    public void showSignInError(String errorMessage) {
        mSignInActivity.showSignInError(errorMessage);
    }
}
