package com.sep.UniTrips.model.SignInModel;

import com.google.firebase.auth.FirebaseAuth;

public class LoginTaskManager {
    private FirebaseAuth mAuth;

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }


    private void signIn(String email, String password){

//        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                if(!task.isSuccessful()){
//                    //login fail. feedback the user the error message
//                    Toast.makeText(SignInActivity.this, R.string.sign_fail_message,Toast.LENGTH_LONG).show();
//                    updateUI(null);
//                }else{
//                    //login success, update the ui with the signed-in user's information
//                    Toast.makeText(SignInActivity.this, "Login successful",Toast.LENGTH_LONG).show();
//                    Log.d("LOGIN SUCCEFFUL","login successful");
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    updateUI(user);
//                }
//            }
//        });

    }
}
