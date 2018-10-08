/**
 * Copyright (c) 2018. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions an limitations under the License.
 */

package com.sep.UniTrips.view;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sep.UniTrips.R;
import com.sep.UniTrips.model.AddEventModel.Event;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {

    private EditText mTitleEt;
    private EditText mStartTimeEt;
    private EditText mEndTimeEt;
    private EditText mNotificationTimeEt;
    private EditText mLocationEt;
    private Button mSaveEventBtn;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Calendar mCalendar = Calendar.getInstance();
    private Calendar mDate = Calendar.getInstance();
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        mTitleEt = findViewById(R.id.eventTitle_et);
        mStartTimeEt = findViewById(R.id.eventStartTime_et);
        mEndTimeEt = findViewById(R.id.eventEndTime_et);
        mNotificationTimeEt = findViewById(R.id.eventNotification_et);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mStartTimeEt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int year = mCalendar.get(Calendar.YEAR);
//                int month = mCalendar.get(Calendar.MONTH);
//                int day = mCalendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog dialog = new DatePickerDialog(getApplicationContext(),
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        mDateSetListener,
//                        year,month,day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//            }
//        });
//        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                mDate.set(Calendar.YEAR, year);
//                mDate.set(Calendar.MONTH, month);
//                mDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            }
//        };
//        mEndTimeEt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        mLocationEt = findViewById(R.id.eventLocation_et);
        mSaveEventBtn = findViewById(R.id.saveEvent_btn);
        mSaveEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEt.getText().toString();
                String startTime = mStartTimeEt.getText().toString();
                String endTime = mEndTimeEt.getText().toString();
                String notificationTime = mNotificationTimeEt.getText().toString();
                String location = mLocationEt.getText().toString();
                Event event = new Event(title,startTime,endTime,location,notificationTime);
                final FirebaseUser currentUser = mAuth.getCurrentUser();
                mDatabase.child("users").child(currentUser.getUid()).child("Calendars").child("My Event").setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Event add successful !",Toast.LENGTH_SHORT).show();
                        startActivity(getParentActivityIntent());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
//    private void updateLabel() {
//        String myFormat = "MM/dd/yy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//        mStartTimeEt.setText(sdf.format(mDate.getTime()));
//    }
}
