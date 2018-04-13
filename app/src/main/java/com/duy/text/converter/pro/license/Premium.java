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
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.duy.common.purchase.InAppPurchaseHelper;
import com.duy.common.utils.DLog;
import com.duy.text.converter.pro.activities.UpgradeActivity;

/**
 * Created by Duy on 26-Jul-17.
 */

public class Premium {
    public static final String PRO_PACKAGE = "com.duy.text_converter.pro";
    private static final String FREE_PACKAGE = "duy.com.text_converter";
    private static final String KEY_CRACKED = "pirate";
    private static final String TAG = "Premium";

    public static boolean isCrack(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getBoolean(KEY_CRACKED, false);
    }

    public static void setCracked(Context context, boolean isCrack) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().putBoolean(KEY_CRACKED, isCrack).apply();
    }

    public static boolean isFree(Context context) {
       /* return context.getPackageName().equals(FREE_PACKAGE) || isCrack(context);*/
        return !isPremium(context);
    }

    public static boolean isPremium(Context context) {
        boolean proPackage = context.getPackageName().equals(PRO_PACKAGE) && !isCrack(context);
        boolean premiumUser = com.duy.common.purchase.Premium.isPremiumUser(context);
        DLog.d(TAG, "proPackage = " + proPackage);
        DLog.d(TAG, "premiumUser = " + premiumUser);
        return (proPackage || premiumUser) && false;
    }

    public static void upgrade(final Activity activity) {
        Intent intent = new Intent(activity, UpgradeActivity.class);
        activity.startActivityForResult(intent, InAppPurchaseHelper.RC_REQUEST_UPGRADE);
    }
}
