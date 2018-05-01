/*
 * Copyright (C)  2017-2018 Tran Le Duy
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
        return !isPremium(context);
    }

    public static boolean isPremium(Context context) {
        boolean proPackage = context.getPackageName().equals(PRO_PACKAGE) && !isCrack(context);
        boolean premiumUser = com.duy.common.purchase.Premium.isPremiumUser(context);
        DLog.d(TAG, "proPackage = " + proPackage);
        DLog.d(TAG, "premiumUser = " + premiumUser);
        return (proPackage || premiumUser);
    }

    public static void upgrade(final Activity activity) {
        Intent intent = new Intent(activity, UpgradeActivity.class);
        activity.startActivityForResult(intent, InAppPurchaseHelper.RC_REQUEST_UPGRADE);
    }
}
