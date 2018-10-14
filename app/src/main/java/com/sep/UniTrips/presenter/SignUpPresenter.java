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

/**
 * This is the sign up presenter which allow user will post user' s input to the model and attempt with the user's inpu /
 */
public class SignUpPresenter implements SignUpInterface.presenter {

    private Context mConetxt;
    private SignUpActivity mSignUpActivity;
    private SignUpTaskManager mTaskManager;

    public SignUpPresenter(Context context,SignUpActivity signUpActivity){
        this.mConetxt = context;
        this.mSignUpActivity = signUpActivity;
        this.mTaskManager = new SignUpTaskManager(mConetxt,this);
    }

    /**
     * set the erroe message to the view when the error happen
     * @param errorMessage
     */
    @Override
    public void setEmailError(String errorMessage){
        mSignUpActivity.setEmailError(errorMessage);
    }


    /**
     * set password erroemessage to the view
     * @param errorMessage
     */
    @Override
    public void setPasswordError(String errorMessage){
        mSignUpActivity.setPasswordError(errorMessage);
    }

    /**
     * set confirm password error to the veiw
     * @param errorMessage
     */
    @Override
    public void setConfirmPasswordError(String errorMessage){
        mSignUpActivity.setConfirmPasswordError(errorMessage);
    }

    /**
     * request the focus to the error view
     */
    @Override
    public void focusVies() {
        mSignUpActivity.focusView();
    }

    /**
     * update the UI base on the current user
     * @param user
     */
    @Override
    public void updateUI(FirebaseUser user) {
        mSignUpActivity.updateUI(user);
    }

    /**
     * reset the view error message
     */
    @Override
    public void restError() {
        mSignUpActivity.restError();
    }

    /**
     * pos the login detail to the model and attempt sign up an new account
     * @param email
     * @param password
     * @param confirmPassword
     */
    @Override
    public void attemptCreateAccount(String email, String password, String confirmPassword) {
        restError();
        mTaskManager.attemptCreateAccount(email,password,confirmPassword);
    }

    /**
     * feedback the error message when sign up fail
     * @param errorMessage
     */
    @Override
    public void showSignUpError(String errorMessage) {
        mSignUpActivity.showSignUpError(errorMessage);
    }

    /**
     * display the progress bar
     */
    @Override
    public void showProgressBar() {
        mSignUpActivity.showProgressBar();
    }

    /**
     * dispay the timeout dialog to feedback the user when it fail to connect with the server
     */
    @Override
    public void showTimeoutDialog() {
        mSignUpActivity.showTimeoutDialog();
    }
}
