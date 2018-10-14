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

package com.sep.UniTrips.model.RestPassword;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.sep.UniTrips.R;
import com.sep.UniTrips.presenter.ResetPasswordPresenter;

public class ResetPasswordTaskManager {

    private FirebaseAuth mAuth;
    private ResetPasswordPresenter mPresenter;
    private Context mContext;

    public ResetPasswordTaskManager(ResetPasswordPresenter mPresenter, Context mContext) {
        this.mAuth = FirebaseAuth.getInstance();
        this.mPresenter = mPresenter;
        this.mContext = mContext;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public void attemptRequestPassword(String email){

        //reset error
        mPresenter.restError();

        boolean cancel = false;

        //validate email input
        if(TextUtils.isEmpty(email)){
            mPresenter.setEmailError(mContext.getString(R.string.error_field_required));
            cancel = true;
        }else if(!isEmailValid(email)){
            mPresenter.setEmailError(mContext.getString(R.string.error_invalid_email));
            cancel = true;
        }

        if(cancel){
            mPresenter.focusView();
        }else{
            sendResetPasswordEmail(email);
        }
    }

    public void sendResetPasswordEmail(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mPresenter.updateUI(true);
                        }else{
                            mPresenter.updateUI(false);
                        }
                    }
                });
    }
}