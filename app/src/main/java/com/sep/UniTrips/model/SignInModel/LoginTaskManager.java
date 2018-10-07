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


package com.sep.UniTrips.model.SignInModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.sep.UniTrips.R;
import com.sep.UniTrips.presenter.LoginPresenter;
import com.sep.UniTrips.view.SignInActivity;

public class LoginTaskManager {

    private FirebaseAuth mAuth;
    private LoginPresenter mPresenter;
    private Context mContext;

    public LoginTaskManager() {
        // for testing basic functionality not requiring context or other classes
    }

    public LoginTaskManager(Context context, LoginPresenter presenter) {
        this.mAuth = FirebaseAuth.getInstance();
        this.mPresenter = presenter;
        this.mContext = context;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin(String email, String password) {
        // Reset errors.
        mPresenter.restError();

        boolean cancel = false;

        if (TextUtils.isEmpty(password)) {
            mPresenter.setPasswordError(mContext.getString(R.string.error_field_required));
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPresenter.setPasswordError(mContext.getString(R.string.error_invalid_password));
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mPresenter.setEmailError(mContext.getString(R.string.error_field_required));
            cancel = true;
        } else if (!isEmailValid(email)) {
            mPresenter.setEmailError(mContext.getString(R.string.error_invalid_email));
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            mPresenter.focusView();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
            signIn(email, password);
        }
    }

    public boolean attemptLoginLogic(String email, String password) {

        boolean cancel = false;

        if (TextUtils.isEmpty(password)) {
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            cancel = true;
        } else if (!isEmailValid(email)) {
            cancel = true;
        }

        return !cancel;
    }

    public boolean attemptLoginErrorMessaging(String email, String password) {

        boolean cancel = false;

        if (TextUtils.isEmpty(password)) {
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            cancel = true;
        } else if (!isEmailValid(email)) {
            cancel = true;
        }

        return !cancel;
    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    //login fail. feedback the user the error message
                    try{
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException invalidEmail){
                        mPresenter.showSignInError(mContext.getString(R.string.firebase_invalidEmail_exception));
                    }
                    catch (FirebaseAuthInvalidCredentialsException invalidCredentials){
                        mPresenter.showSignInError(mContext.getString(R.string.firebase_password_incorrect_exception));
                    }
                    catch (Exception e) {
                        Log.e("sign in exception","onComplete: " + e.getMessage());
                        e.printStackTrace();
                    }
                    mPresenter.updateUI(null);
                } else {
                    //login success, update the ui with the signed-in user's information
                    //Toast.makeText(SignInActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    mPresenter.updateUI(user);
                }
            }
        });


    }
}
