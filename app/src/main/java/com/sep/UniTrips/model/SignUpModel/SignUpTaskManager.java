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


package com.sep.UniTrips.model.SignUpModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.sep.UniTrips.presenter.SignUpPresenter;
import com.sep.UniTrips.R;

public class SignUpTaskManager {

    private SignUpPresenter mPresenter;
    private Context mContext;
    private FirebaseAuth mAuth;

    public SignUpTaskManager() {
        // for testing logic
    }


    public SignUpTaskManager(Context context,SignUpPresenter presenter){
        this.mContext = context;
        this.mPresenter = presenter;
        this.mAuth = FirebaseAuth.getInstance();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    private boolean isConfirmPassword(String password,String confirmPassword){
        return password.equals(confirmPassword);
    }


    /**
     * Attempts to register the account specified by the create account form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual create account attempt is made.
     */
    public void attemptCreateAccount(String email,String password,String confirmPassword) {

        mPresenter.restError();
        boolean cancel = false;

        //Check if any field is empty.
        if(TextUtils.isEmpty(email)){
            mPresenter.setEmailError(mContext.getString(R.string.error_field_required));
            cancel = true;
        }else if(TextUtils.isEmpty(password)){
            mPresenter.setPasswordError(mContext.getString(R.string.error_field_required));
            cancel = true;
        }else if(TextUtils.isEmpty(confirmPassword)){
            mPresenter.setConfirmPasswordError(mContext.getString(R.string.error_field_required));
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
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPresenter.setPasswordError(mContext.getString(R.string.error_invalid_password));
            cancel = true;
        }
        //Check if the confirm password same with the password
        if(!isConfirmPassword(password,confirmPassword)){
            mPresenter.setConfirmPasswordError(mContext.getString(R.string.confirmPassword_error));
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            mPresenter.focusVies();
        } else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
            createUser(email,password);
        }
    }

    public boolean attemptCreateAccountLogic(String email,String password,String confirmPassword) {

        boolean cancel = false;

        //Check if any field is empty.
        if(TextUtils.isEmpty(email)){
            cancel = true;
        }else if(TextUtils.isEmpty(password)){
            cancel = true;
        }else if(TextUtils.isEmpty(confirmPassword)){
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            cancel = true;
        } else if (!isEmailValid(email)) {
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            cancel = true;
        }
        //Check if the confirm password same with the password
        if(!isConfirmPassword(password,confirmPassword)){
            cancel = true;
        }

        return !cancel;
    }


    /**
     * Create an account via using the firebase createUserWithEmailAndPasswordMethod and
     * waiting for the sign up result. if sign in success, update the UI with the sign-in user,
     * otherwise display an error message to the user.
     */
    public void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){
                    //login fail. feedback the user the error message
                    //Toast.makeText(SignInActivity.this, R.string.sign_fail_message,Toast.LENGTH_LONG).show();
                    try{
                        mPresenter.updateUI(null);
                        throw task.getException();
                    }
                     catch (FirebaseAuthUserCollisionException exception)
                    {
                        Log.d("email exception", "onComplete: exist_email");
                        mPresenter.showSignUpError(mContext.getString(R.string.existEmailError));

                    } catch (Exception e) {
                        Log.e("sign up exception", "onComplete: " + e.getMessage());
                    }
                }else{
                    //login success, update the ui with the signed-in user's information
                    //Toast.makeText(SignInActivity.this, "Login successful",Toast.LENGTH_LONG).show();
                    //Log.d("create account success","CREATE ACCOUNT SUCCESSFUL");
                    FirebaseUser user = mAuth.getCurrentUser();
                    mPresenter.updateUI(user);
                }
            }
        });
    }
}
