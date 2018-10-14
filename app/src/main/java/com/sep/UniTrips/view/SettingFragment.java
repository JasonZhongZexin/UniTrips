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
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.sep.UniTrips.R;

public class SettingFragment extends Fragment {

    private TextView mUserPreferenceTv;
    private TextView mAddCalendarTextView;
    private TextView mAboutTimeBoxesTextView;
    private View mView;
    private Button mLogoutBtn;
    private AlertDialog mDialog;
    private TextView mFQATv;

    String userTransport = null;


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_setting, container, false);
        mAddCalendarTextView = mView.findViewById(R.id.addCalendar_textView);
        mAddCalendarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ImportCalendarActivity.class);
                startActivity(intent);
            }
        });
        mUserPreferenceTv = mView.findViewById(R.id.userPreference_textView);
        mUserPreferenceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserSettingActivity.class);
                startActivityForResult(intent, 0); // 此处设置了用于返回参数的intent
            }
        });
        mFQATv = mView.findViewById(R.id.FQA_textView);
        mFQATv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),FAQ_Activity.class);
                startActivity(intent);
            }
        });
        mLogoutBtn = mView.findViewById(R.id.logoutBtn);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                final View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout,null);
                Button logoutBtn = dialogView.findViewById(R.id.dialog_logoutBtn);
                Button cancelBtn = dialogView.findViewById(R.id.dialog_cancelBtn);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                logoutBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(),WelcomeActivity.class);
                        startActivity(intent);
                    }
                });
                mBuilder.setView(dialogView);
                mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == 1) { // 获取用户的transportation
                userTransport = data.getStringExtra("userPrefernceTransport");
                ((MainActivity) getActivity()).setUserTransport(userTransport);
            }
        }
    }
}
