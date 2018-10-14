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

import android.content.Intent;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sep.UniTrips.R;
import com.sep.UniTrips.model.SignInModel.LoginInterface;
import com.sep.UniTrips.presenter.LoginPresenter;

/**
 * A loginToUTS screen that offers loginToUTS via username and password.
 */
public class SignInActivity extends AppCompatActivity implements LoginInterface.view{

    // UI references.
    private AutoCompleteTextView mEmailEt;
    private EditText mPasswordEt;
    private View mLoginFormView;
    private ImageButton mBackBtn;
    private TextView mForgetPasswordTv;
    private Button mSignInBtn;
    private FirebaseAuth mAuth;
    private View mFocusView;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //Remove the title of this page
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //Remove the status bar of the activity
//        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
         //Set up the loginToUTS form.
        mAuth = FirebaseAuth.getInstance();
        mPresenter = new LoginPresenter(this,this);
        mEmailEt = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordEt=findViewById(R.id.password);

        mBackBtn = findViewById(R.id.backBtn);
        mBackBtn.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                startActivity(getParentActivityIntent());
            }
        });

        mForgetPasswordTv = findViewById(R.id.passwordRestTv);
        mForgetPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        mPasswordEt = (EditText) findViewById(R.id.password);
        mPasswordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    String email = mEmailEt.getText().toString();
                    String password = mPasswordEt.getText().toString();
                    mPresenter.attemptLogin(email,password);
                    return true;
                }
                return false;
            }
        });

        mLoginFormView = findViewById(R.id.signUp_form);
        mSignInBtn = findViewById(R.id.sign_in_button);
        mSignInBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEt.getText().toString();
                String password = mPasswordEt.getText().toString();
                mPresenter.attemptLogin(email,password);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    //UpdateUI according to the current user
    @Override
    public void updateUI(FirebaseUser currentUser){
        //check if user is signed in (non-null)
        if(currentUser!=null){
            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void showSignInError(String errorMessage) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }

    @Override
    public void restError() {
        mEmailEt.setError(null);
        mPasswordEt.setError(null);
    }

    @Override
    public void setEmailError(String errorMessage) {
        mEmailEt.setError(errorMessage);
        mFocusView = mEmailEt;
    }

    @Override
    public void setPasswordError(String errorMessage) {
        mPasswordEt.setError(errorMessage);
        mFocusView = mPasswordEt;
    }

    @Override
    public void focusView() {
        if(mFocusView!=null){
            mFocusView.requestFocus();
        }
    }

    //use for unit testing, check dialog
//    public static boolean hasOpenedDialog(FragmentActivity activity){}
}


