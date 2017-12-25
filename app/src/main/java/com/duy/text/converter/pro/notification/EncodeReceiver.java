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

package com.duy.text.converter.pro.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.duy.text.converter.core.codec.interfaces.CodecUtil;
import com.duy.text.converter.core.utils.ClipboardUtil;
import com.google.firebase.analytics.FirebaseAnalytics;

//import android.util.Log;

/**
 * Created by Duy on 26-Jul-17.
 */

public class EncodeReceiver extends BroadcastReceiver {
    private static final String TAG = "StyleReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        FirebaseAnalytics.getInstance(context).logEvent("encode_notification", new Bundle());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (intent != null) {
            String name = "";
            if (intent.getAction().equals(StyleNotification.ACTION_ENCODE_STYLE_1)) {
                name = preferences.getString("pref_encode_style_1", "");
            } else if (intent.getAction().equals(StyleNotification.ACTION_ENCODE_STYLE_2)) {
                name = preferences.getString("pref_encode_style_2", "");
            } else if (intent.getAction().equals(StyleNotification.ACTION_ENCODE_STYLE_3)) {
                name = preferences.getString("pref_encode_style_3", "");
            } else if (intent.getAction().equals(StyleNotification.ACTION_ENCODE_STYLE_4)) {
                name = preferences.getString("pref_encode_style_4", "");
            } else if (intent.getAction().equals(StyleNotification.ACTION_ENCODE_STYLE_5)) {
                name = preferences.getString("pref_encode_style_5", "");
            }
            if (name.isEmpty()) {
                complainNotSet(context);
                closeStatusBar(context);
                return;
            }
            String inp = ClipboardUtil.getClipboard(context);
            String encode = CodecUtil.encode(name, context, inp);
            setText(context, encode);
        }
        closeStatusBar(context);
    }

    private void closeStatusBar(Context context) {

        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

    private void setText(Context context, String encode) {
        ClipboardUtil.setClipboard(context, encode);
    }

    private void complainNotSet(Context context) {
        Toast.makeText(context, "Unset", Toast.LENGTH_SHORT).show();
    }
}
