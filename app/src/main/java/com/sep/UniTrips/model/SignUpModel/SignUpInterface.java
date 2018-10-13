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

import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;
import com.sep.UniTrips.view.MainActivity;
import com.sep.UniTrips.view.SignInActivity;


public interface SignUpInterface {
    interface presenter{
        void setEmailError(String errorMessage);
        void setPasswordError(String errorMessage);
        void setConfirmPasswordError(String errorMessage);
        void focusVies();
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
