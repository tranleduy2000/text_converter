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

package com.duy.text.converter.pro.license;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;

import com.duy.common.StoreUtil;
import com.duy.text.converter.R;
import com.duy.text.converter.core.activities.MainActivity;

/**
 * Created by Duy on 26-Jul-17.
 */

public class Premium {
    public static final String KEY_CRACK = "pirate";
    public static boolean PREMIUM = true;

    public static boolean isCrack(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getBoolean(KEY_CRACK, false);
    }

    public static void setCrack(Context context, boolean isCrack) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().putBoolean(KEY_CRACK, isCrack).apply();
    }

    public static boolean isFree(Context context) {
        return context.getPackageName().equals("duy.com.text_converter")
                || isCrack(context);
    }

    public static boolean isPremium(MainActivity context) {
        return context.getPackageName().equals("com.duy.text_converter.pro")
                && !isCrack(context);
    }

    public static void showDialogUpgrade(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.upgrade);
        builder.setMessage(R.string.pro_feature);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StoreUtil.gotoPlayStore(activity, "com.duy.text_converter.pro");
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}
