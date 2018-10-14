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

import com.sep.UniTrips.model.RestPassword.ResetPasswordInterface;
import com.sep.UniTrips.model.RestPassword.ResetPasswordTaskManager;
import com.sep.UniTrips.view.ResetPasswordActivity;

/**
 * this is the reset password presenter which connect the view and model and communication between these two
 */
public class ResetPasswordPresenter implements ResetPasswordInterface.Presenter {

    private Context mContext;
    private ResetPasswordActivity mResetPasswordActivity;
    private ResetPasswordTaskManager mResetPasswordTaskManager;

    public ResetPasswordPresenter(Context mContext, ResetPasswordActivity mResetPasswordActivity) {
        this.mContext = mContext;
        this.mResetPasswordActivity = mResetPasswordActivity;
        this.mResetPasswordTaskManager = new ResetPasswordTaskManager(this,mContext);
    }

    @Override
    public void requestEmail(String email) {
        mResetPasswordTaskManager.attemptRequestPassword(email);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        mResetPasswordActivity.showErrorMessage(errorMessage);
    }

    @Override
    public void restError() {
        mResetPasswordActivity.restError();
    }

    @Override
    public void setEmailError(String errorMessage) {
        mResetPasswordActivity.setEmailError(errorMessage);
    }

    @Override
    public void focusView() {
        mResetPasswordActivity.focusView();
    }

    @Override
    public void updateUI(Boolean result) {
        mResetPasswordActivity.updateUI(result);
    }
}