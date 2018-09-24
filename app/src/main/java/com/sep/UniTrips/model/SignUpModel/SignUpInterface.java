package com.sep.UniTrips.model.SignUpModel;

import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;
import com.sep.UniTrips.view.MainActivity;
import com.sep.UniTrips.view.SignInActivity;


public interface SignUpInterface {
    interface presenter{
        void setEmailError(String errorMessage);
        void setPasswordError(String errorMessage);
        void setConfirmPasswordError(String errorMessage);
        void showErrorMessage();
        void updateUI(FirebaseUser user);
        void restError();
        void attemptCreateAccount(String email,String password,String confirmPassword);
        void showSignUpError(String errorMessage);
    }

    interface view{
        void setEmailError(String errorMessage);
        void setPasswordError(String errorMessage);
        void setConfirmPasswordError(String errorMessage);
        void focusView();
        void restError();
        //UpdateUI according to the current user
        void updateUI(FirebaseUser currentUser);
        void showSignUpError(String errorMessage);
    }
}
