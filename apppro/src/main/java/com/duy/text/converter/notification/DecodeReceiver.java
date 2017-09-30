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

package com.duy.text.converter.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.duy.sharedcode.utils.ClipboardUtil;
import com.duy.sharedcode.codec.CodecUtil;
import com.duy.text.converter.license.Premium;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Duy on 26-Jul-17.
 */

public class DecodeReceiver extends BroadcastReceiver {
    private static final String TAG = "DecodeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        FirebaseAnalytics.getInstance(context).logEvent("decode_notification", new Bundle());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (Premium.isCrack(context)) {
            Toast.makeText(context, "Pirate version", Toast.LENGTH_SHORT).show();
            return;
        }
//        Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
        if (intent != null) {
            String methodName = "";
            if (intent.getAction().equals(StyleNotification.ACTION_DECODE_STYLE_1)) {
                methodName = preferences.getString("pref_decode_style_1", "");
            } else if (intent.getAction().equals(StyleNotification.ACTION_DECODE_STYLE_2)) {
                methodName = preferences.getString("pref_decode_style_2", "");
            } else if (intent.getAction().equals(StyleNotification.ACTION_DECODE_STYLE_3)) {
                methodName = preferences.getString("pref_decode_style_3", "");
            } else if (intent.getAction().equals(StyleNotification.ACTION_DECODE_STYLE_4)) {
                methodName = preferences.getString("pref_decode_style_4", "");
            } else if (intent.getAction().equals(StyleNotification.ACTION_DECODE_STYLE_5)) {
                methodName = preferences.getString("pref_decode_style_5", "");
            }
            if (methodName.isEmpty()) {
                complainNotSet(context);
                return;
            }
            String inp = ClipboardUtil.getClipboard(context);
            String result = CodecUtil.decode(methodName, context, inp);
            setText(context, result);
        }
        closeStatusBar(context);
    }

    private void closeStatusBar(Context context) {
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

    private void setText(Context context, String decode) {
        ClipboardUtil.setClipboard(context, decode);
    }

    private void complainNotSet(Context context) {
        Toast.makeText(context, "Unset", Toast.LENGTH_SHORT).show();
    }
}
