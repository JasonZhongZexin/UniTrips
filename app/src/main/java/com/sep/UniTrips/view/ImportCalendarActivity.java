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

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sep.UniTrips.R;
import com.sep.UniTrips.model.ImportCalendar.ImportCalendarInterface;
import com.sep.UniTrips.presenter.ImportCalendarPresneter;

import java.util.Calendar;

public class ImportCalendarActivity extends AppCompatActivity implements ImportCalendarInterface.View{

    private ImportCalendarPresneter mPresenter;
    private View mFocusView;
    private EditText mStudentIDEt;
    private EditText mPasswordEt;
    private Button mImportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_calendar);
        mPresenter = new ImportCalendarPresneter(this,this);
        mStudentIDEt = findViewById(R.id.studentIDEt);
        mPasswordEt = findViewById(R.id.passwordEt);
        mImportBtn = findViewById(R.id.importBtn);
        mImportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = mStudentIDEt.getText().toString();
                String password = mPasswordEt.getText().toString();
                String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
                mPresenter.attemptGetCalendar(ID,password,year);
            }
        });
    }

    @Override
    public void resetError() {
        mStudentIDEt.setError(null);
        mPasswordEt.setError(null);
    }

    @Override
    public void setPasswordError(String errorMessage) {
        mPasswordEt.setError(errorMessage);
        mFocusView = mPasswordEt;
    }

    @Override
    public void setIDError(String errorMessage) {
        mStudentIDEt.setError(errorMessage);
        mFocusView = mStudentIDEt;
    }

    @Override
    public void focusView() {
        if(mFocusView!=null){
            mFocusView.requestFocus();
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void updateUI() {
        startActivity(getParentActivityIntent());
    }
}
