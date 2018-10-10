package com.sep.UniTrips.model.UserSetting;

import android.arch.persistence.room.*;
import android.support.annotation.*;

@Entity(tableName = "Users")
public class User {

    @ColumnInfo(name="username")
    @PrimaryKey
    @NonNull
    private String mUsername;
    @ColumnInfo(name = "password")
    private String mPassword;
    @ColumnInfo(name = "transport_option")
    private String mPTransport;
    @ColumnInfo(name = "email")
    @NonNull
    private String mEmail;
    @ColumnInfo(name = "notification_tiem ")
    private String mPNotificationTime;

    @NonNull
    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getPTransport() {
        return mPTransport;
    }

    @NonNull
    public String getEmail() {
        return mEmail;
    }

    public String getPNotificationTime() {
        return mPNotificationTime;
    }

    public void setUsername(@NonNull String username) {
        mUsername = username;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setPTransport(String PTransport) {
        mPTransport = PTransport;
    }

    public void setEmail(@NonNull String email) {
        mEmail = email;
    }

    public void setPNotificationTime(String PNotificationTime) {
        mPNotificationTime = PNotificationTime;
    }
}
