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


package com.sep.UniTrips.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.sep.UniTrips.model.SignUpModel.SignUpInterface;
import com.sep.UniTrips.presenter.SignUpPresenter;
import com.sep.UniTrips.R;

/**
 * A loginToUTS screen that offers loginToUTS via email/password.
 */
public class SignUpActivity extends AppCompatActivity  implements SignUpInterface.view{

    private EditText mSignUpEmailEt;
    private EditText mSignUpPasswordEt;
    private EditText mConfirmPasswordEt;
    private View mSignUpFormView;
    private ImageButton mBackBtn;
    private Button mCreateAccountBtn;
    private SignUpPresenter mPresenter;
    private View mFocusView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Set up the login form.
        mBackBtn = (ImageButton) findViewById(R.id.singUpbackBtn);
        mSignUpFormView = findViewById(R.id.email_signUp_form);
        mSignUpEmailEt = (EditText) findViewById(R.id.signUpEmailEt);
        mSignUpPasswordEt = (EditText) findViewById(R.id.signUpPasswordEt);
        mConfirmPasswordEt = (EditText) findViewById(R.id.confirmedPasswordEt);
        mPresenter = new SignUpPresenter(this,this);
        mProgressBar = findViewById(R.id.signUpProgressBar);
        //onClick listener of the back button, launch the parent activity when user pressed the back button
        mBackBtn.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                startActivity(getParentActivityIntent());
            }
        });
        mCreateAccountBtn = findViewById(R.id.sign_up_button);
        mCreateAccountBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mSignUpEmailEt.getText().toString();
                String password = mSignUpPasswordEt.getText().toString();
                String confirmPassword = mConfirmPasswordEt.getText().toString();
                    mPresenter.attemptCreateAccount(email,password,confirmPassword);
            }
        });
    }

    /**
     * set the error message and focus the error view
     * @param errorMessage
     */
    @Override
    public void setEmailError(String errorMessage) {
        mSignUpEmailEt.setError(errorMessage);
        mFocusView = mSignUpEmailEt;
    }

    /**
     * set the error message and focus the error view
     * @param errorMessage
     */
    @Override
    public void setPasswordError(String errorMessage) {
        mSignUpPasswordEt.setError(errorMessage);
        mFocusView = mSignUpPasswordEt;
    }

    /**
     * set the error message and focus the error view
     * @param errorMessage
     */
    @Override
    public void setConfirmPasswordError(String errorMessage) {
        mConfirmPasswordEt.setError(errorMessage);
        mFocusView = mConfirmPasswordEt;
    }

    /**
     * focus the error view if the error view is not null
     *
     */
    @Override
    public void focusView(){
        if(mFocusView!=null){
            mFocusView.requestFocus();
        }
    }

    /**
     * reset all errorMessage to null and disable the focus view
     */
    @Override
    public void restError() {
        mSignUpEmailEt.setError(null);
        mSignUpPasswordEt.setError(null);
        mConfirmPasswordEt.setError(null);
    }

    /**
     * UpdateUI according to the current user, launch the main activity if the user is login successful and
     * current user is not null
     * @param currentUser
     */
    @Override
    public void updateUI(FirebaseUser currentUser){
        //check if user is signed in (non-null)
        if(currentUser!=null){
            mSignUpFormView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * display the show progress bar
     */
    @Override
    public void showProgressBar() {
        mSignUpFormView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * dispay the error message when sign up fail
     * @param errorMessage
     */
    @Override
    public void showSignUpError(String errorMessage){
//        new AlertDialog.Builder(this).setTitle("Error")
//                .setMessage(errorMessage)
//                .setIcon(R.drawable.ic_error_outline_black_30dp)
//                .setPositiveButton(R.string.title_tryAgain, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        mSignUpFormView.setVisibility(View.VISIBLE);
//                        mProgressBar.setVisibility(View.GONE);
//                    }
//                }).show();
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }

    /**
     * feedback error message when connection timeout
     */
    @Override
    public void showTimeoutDialog(){
        new AlertDialog.Builder(this).setTitle(R.string.title_timeout)
                .setMessage(R.string.Internet_Connection_error)
                .setIcon(R.drawable.ic_error_outline_black_30dp)
                .setPositiveButton(R.string.title_tryAgain, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mSignUpFormView.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                    }
                }).show();
    }
}

