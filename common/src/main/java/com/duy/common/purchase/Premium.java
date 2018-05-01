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

package com.duy.common.purchase;

import android.content.Context;

import com.duy.common.BuildConfig;
import com.duy.common.utils.DLog;


/**
 * Created by Duy on 14-Jul-17.
 */

public class Premium {
    public static final String BASE64_KEY = BuildConfig.BASE64_KEY;

    //SKU for my product: the premium upgrade
    public static final String SKU_PREMIUM = "text_converter_premium";
    private static final String TAG = "Premium";

    /**
     * Faster
     */
    private static boolean IS_PREMIUM = false;

    /**
     * Purchase user
     *
     * @param context - Android context
     */
    public static boolean isPremiumUser(Context context) {
        return IS_PREMIUM || FileUtil.licenseCached(context);
    }

    /**
     * Purchase user
     */
    public static void setPremiumUser(Context context, boolean isPremium) {
        DLog.d(TAG, "setPremiumUser() called with: context = [" + context + "], isPremium = [" + isPremium + "]");
        IS_PREMIUM = isPremium;
        if (isPremium) {
            FileUtil.saveLicence(context);
        } else {
            FileUtil.clearLicence(context);
        }
    }


    /**
     * @param context - the android context
     * @return true if free user
     */
    public static boolean isFreeUser(Context context) {
        return !isPremiumUser(context);
    }


}
