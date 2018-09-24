/**
 * Copyright (c) 2018. [ Zexin Zhong ]
 *
 */

package com.sep.UniTrips.model.ImportCalendar;

import com.google.gson.annotations.SerializedName;

public class LoginBeans {

    @SerializedName("success")
    private String mSuccess;
    @SerializedName("token")
    private String mToken;

    public void setSuccess(String mSuccess) {
        this.mSuccess = mSuccess;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public String getSuccess() {

        return mSuccess;
    }

    public String getToken() {
        return mToken;
    }
}
