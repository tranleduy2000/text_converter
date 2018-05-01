/*
 * Copyright (c) 2018 by Tran Le Duy
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
