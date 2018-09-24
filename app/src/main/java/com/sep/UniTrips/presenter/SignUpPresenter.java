package com.sep.UniTrips.presenter;

import android.content.Context;
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
    public void showErrorMessage() {
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


}
