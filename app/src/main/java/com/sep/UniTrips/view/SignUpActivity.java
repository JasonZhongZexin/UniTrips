package com.sep.UniTrips.view;

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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.sep.UniTrips.model.SignUpModel.SignUpInterface;
import com.sep.UniTrips.presenter.SignUpPresenter;
import com.sep.UniTrips.R;

/**
 * A login screen that offers login via email/password.
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Set up the login form.
        mBackBtn = (ImageButton) findViewById(R.id.singUpbackBtn);
        mSignUpFormView = findViewById(R.id.signUp_form);
        mSignUpEmailEt = (EditText) findViewById(R.id.signUpEmailEt);
        mSignUpPasswordEt = (EditText) findViewById(R.id.signUpPasswordEt);
        mConfirmPasswordEt = (EditText) findViewById(R.id.confirmedPasswordEt);
        mPresenter = new SignUpPresenter(this,this);

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
                Toast.makeText(getApplication(), (String)"create account button clicked",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setEmailError(String errorMessage) {
        mSignUpEmailEt.setError(errorMessage);
        mFocusView = mSignUpEmailEt;
    }

    @Override
    public void setPasswordError(String errorMessage) {
        mSignUpPasswordEt.setError(errorMessage);
        mFocusView = mSignUpPasswordEt;
    }

    @Override
    public void setConfirmPasswordError(String errorMessage) {
        mConfirmPasswordEt.setError(errorMessage);
        mFocusView = mConfirmPasswordEt;
    }

    @Override
    public void focusView(){
        if(mFocusView!=null){
            mFocusView.requestFocus();
        }
    }

    @Override
    public void restError() {
        mSignUpEmailEt.setError(null);
        mSignUpPasswordEt.setError(null);
        mConfirmPasswordEt.setError(null);
    }

    @Override
    //UpdateUI according to the current user
    public void updateUI(FirebaseUser currentUser){
        //check if user is signed in (non-null)
        if(currentUser!=null){
            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void showSignUpError(String errorMessage){
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show();
    }

    //    /**
//     * Shows the progress UI and hides the login form.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mSignUpFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }


    /********************************************************************************
     ********************************Async task**************************************
     ********************************************************************************/
//    /**
//     * Represents an asynchronous login/registration task used to authenticate
//     * the user.
//     */
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final String mEmail;
//        private final String mPassword;
//
//        UserLoginTask(String email, String password) {
//            mEmail = email;
//            mPassword = password;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
//
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
//
//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }
//
//            // TODO: register the new account here.
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//
//            if (success) {
//                finish();
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }

}

