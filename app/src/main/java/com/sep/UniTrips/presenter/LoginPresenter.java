package com.sep.UniTrips.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.sep.UniTrips.model.SignInModel.LoginInterface;
import com.sep.UniTrips.view.SignInActivity;

public class LoginPresenter implements LoginInterface.presenter{

    private SignInActivity mSignInActivity;
    private Context mContext;
    public LoginPresenter(Context context, SignInActivity signInActivity){
        this.mContext = context;
        this.mSignInActivity = signInActivity;
    }
    //UpdateUI according to the current user
    private void updateUI(FirebaseUser currentUser){
//        //check if user is signed in (non-null)
//        if(currentUser!=null){
//            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
//            startActivity(intent);
//        }
    }
}
