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

package com.duy.text.converter.clipboard;

import android.annotation.SuppressLint;
import android.os.Build;

/**
 * Created by Duy on 13-Apr-18.
 */

public class TransactionUtils {
    /**
     * https://www.neotechsoftware.com/blog/android-intent-size-limit
     * API 23: 517716 bytes (520107 bytes incl. overhead);
     * API 22: 517876 bytes;
     * API 19: 518368 bytes;
     * API 16: 518396 bytes;
     * API 10: 518580 bytes;
     */
    @SuppressLint("ObsoleteSdkInt")
    public static boolean isTransactionTooLarge(String content) {
        int bytes = content.getBytes().length;
        if (Build.VERSION.SDK_INT >= 23) {
            if (bytes > 517716) {
                return true;
            }
        } else if (Build.VERSION.SDK_INT >= 22) {
            if (bytes > 517876) {
                return true;
            }
        } else if (Build.VERSION.SDK_INT >= 19) {
            if (bytes > 518368) {
                return true;
            }
        } else if (Build.VERSION.SDK_INT >= 16) {
            if (bytes > 518396) {
                return true;
            }
        } else if (Build.VERSION.SDK_INT >= 10) {
            if (bytes > 518580) {
                return true;
            }
        }
        return false;
    }
}
