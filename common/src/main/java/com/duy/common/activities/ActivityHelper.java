/*
 * Copyright (c) 2017 by Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duy.common.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.duy.common.R;

/**
 * Created by Duy on 25-Dec-17.
 */

public class ActivityHelper {
    public static boolean setupToolbar(Activity activity) {
        //Not a AppCompatActivity
        if (activity == null || !(activity instanceof AppCompatActivity)) {
            return false;
        }

        //find toolbar and setup, display back button
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            appCompatActivity.setSupportActionBar(toolbar);
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            return true;
        }

        return false;
    }
}
