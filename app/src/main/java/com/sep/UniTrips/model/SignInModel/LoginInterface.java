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

import com.google.firebase.auth.FirebaseUser;

public interface LoginInterface {
    interface presenter{
        void restError();
        void setEmailError(String errorMessage);
        void setPasswordError(String errorMessage);
        void focusView();
        void updateUI(FirebaseUser user);
        void attemptLogin(String email, String password);
        void showSignInError(String errorMessage);
    }

    interface view{
        void restError();
        void setEmailError(String errorMessage);
        void setPasswordError(String errorMessage);
        void focusView();
        void updateUI(FirebaseUser user);
        void showSignInError(String errorMessage);
    }
}
