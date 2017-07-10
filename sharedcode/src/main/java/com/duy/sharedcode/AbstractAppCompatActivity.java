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

package com.duy.sharedcode;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.duy.textconverter.sharedcode.BuildConfig;
import com.kobakei.ratethisapp.RateThisApp;


/**
 * abstract theme for app
 * <p>
 * auto set theme when user changed theme
 * <p>
 * Created by Duy on 19/7/2016
 */
public abstract class AbstractAppCompatActivity extends AppCompatActivity {
    public static final String APP_ID = BuildConfig.APPLICATION_ID;

    @Override
    protected void onStart() {
        super.onStart();
        // Monitor launch times and interval from installation
        RateThisApp.onStart(this);
        // If the criteria is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this);
        RateThisApp.setCallback(new RateThisApp.Callback() {
            @Override
            public void onYesClicked() {
                gotoPlayStore(MainActivity.APP_ID);
            }

            @Override
            public void onNoClicked() {
            }

            @Override
            public void onCancelClicked() {
            }
        });

    }

    public void gotoPlayStore(String APP_ID) {
        Uri uri = Uri.parse("market://details?id=" + APP_ID);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |

                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + APP_ID)));
        }
    }


}
