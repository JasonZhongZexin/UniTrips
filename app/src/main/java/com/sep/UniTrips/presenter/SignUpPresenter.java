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
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.sep.UniTrips.model.SignUpModel.SignUpInterface;
import com.sep.UniTrips.model.SignUpModel.SignUpTaskManager;
import com.sep.UniTrips.view.SignUpActivity;

public class SignUpPresenter implements SignUpInterface.presenter {

    private Context mConetxt;
    private SignUpActivity mSignUpActivity;
    private SignUpTaskManager mTaskManager;

    public SignUpPresenter(Context context,SignUpActivity signUpActivity){
        this.mConetxt = context;
        this.mSignUpActivity = signUpActivity;
        this.mTaskManager = new SignUpTaskManager(mConetxt,this);
    }


    @Override
    public void setEmailError(String errorMessage){
        mSignUpActivity.setEmailError(errorMessage);
    }

    @Override
    public void setPasswordError(String errorMessage){
        mSignUpActivity.setPasswordError(errorMessage);
    }

    @Override
    public void setConfirmPasswordError(String errorMessage){
        mSignUpActivity.setConfirmPasswordError(errorMessage);
    }

    @Override
    public void focusVies() {
        mSignUpActivity.focusView();
    }

    @Override
    public void updateUI(FirebaseUser user) {
        mSignUpActivity.updateUI(user);
    }

    @Override
    public void restError() {
        mSignUpActivity.restError();
    }

    @Override
    public void attemptCreateAccount(String email, String password, String confirmPassword) {
        restError();
        mTaskManager.attemptCreateAccount(email,password,confirmPassword);
    }

    @Override
    public void showSignUpError(String errorMessage) {
        mSignUpActivity.showSignUpError(errorMessage);
    }

    @Override
    public void showProgressBar() {
        mSignUpActivity.showProgressBar();
    }

    @Override
    public void showTimeoutDialog() {
        mSignUpActivity.showTimeoutDialog();
    }
}
