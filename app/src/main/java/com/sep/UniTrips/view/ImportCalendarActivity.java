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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sep.UniTrips.R;
import com.sep.UniTrips.presenter.ImportCalendarPresneter;

public class ImportCalendarActivity extends AppCompatActivity {

    private ImportCalendarPresneter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_calendar);
        mPresenter = new ImportCalendarPresneter(this,this);
    }
}
