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
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.sep.UniTrips.R;
import com.sep.UniTrips.model.RestPassword.ResetPasswordInterface;
import com.sep.UniTrips.presenter.ResetPasswordPresenter;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordInterface.view{

    private EditText mEmailEt;
    private View mFocusView;
    private Button submitBtn;
    private Button cancelBtn;
    private AlertDialog mDialog;
    private ResetPasswordPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_password);
        mEmailEt = findViewById(R.id.restPassword_email_Et);
        submitBtn = findViewById(R.id.resetPassword_submitBtn);
        cancelBtn = findViewById(R.id.resetPassword_cancelPassword);
        mPresenter = new ResetPasswordPresenter(this,this);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResetPasswordActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEt.getText().toString();
                mPresenter.requestEmail(email);
            }
        });
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }

    @Override
    public void restError() {
        mEmailEt.setError(null);
    }

    @Override
    public void setEmailError(String errorMessage) {
        mEmailEt.setError(errorMessage);
        mFocusView = mEmailEt;
    }

    @Override
    public void focusView() {
        if(mFocusView!=null){
            mFocusView.requestFocus();
        }
    }

    @Override
    public void updateUI(Boolean result) {
        if(result==true){
            //reset password link send successful
//            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
//            final View dialogView = getLayoutInflater().inflate(R.layout.dialog_email_success,null);
//            Button buttonOk = dialogView.findViewById(R.id.dialog_OkBtn);
//            buttonOk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mDialog.dismiss();
//                }
//            });
//            mBuilder.setView(dialogView);
//            mDialog = mBuilder.create();
//            mDialog.show();
            Toast.makeText(this,getString(R.string.resetPasswordSend),Toast.LENGTH_LONG).show();
        } else{
            //reset password link send successful
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            final View dialogView = getLayoutInflater().inflate(R.layout.dialog_email_wrong,null);
            Button tryAgainBtn = dialogView.findViewById(R.id.dialog_TryAgain);
            Button homeBtn = dialogView.findViewById(R.id.dialog_homeBtn);
            tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
            homeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplication(),SignInActivity.class);
                    startActivity(intent);
                }
            });
            mBuilder.setView(dialogView);
            mDialog = mBuilder.create();
            mDialog.show();
        }
    }
}
