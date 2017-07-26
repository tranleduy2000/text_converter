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

package com.duy.text_converter.pro.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.duy.sharedcode.ClipboardManager;
import com.duy.sharedcode.codec.Encoder;
import com.duy.sharedcode.effect.ArrayEffectEncoder;

import java.util.ArrayList;

/**
 * Created by Duy on 26-Jul-17.
 */

public class StyleReceiver extends BroadcastReceiver {
    private static final String TAG = "StyleReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayEffectEncoder effectEncoder = new ArrayEffectEncoder();
        ArrayList<Encoder> encoders = effectEncoder.getEncoders();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
        if (intent != null) {
            int pos = -1;
            if (intent.getAction().equals(StyleNotification.ACTION_STYLE_1)) {
                pos = preferences.getInt(StyleNotification.ACTION_STYLE_1, -1);
            } else if (intent.getAction().equals(StyleNotification.ACTION_STYLE_2)) {
                pos = preferences.getInt(StyleNotification.ACTION_STYLE_2, -1);
            } else if (intent.getAction().equals(StyleNotification.ACTION_STYLE_3)) {
                pos = preferences.getInt(StyleNotification.ACTION_STYLE_3, -1);
            } else if (intent.getAction().equals(StyleNotification.ACTION_STYLE_4)) {
                pos = preferences.getInt(StyleNotification.ACTION_STYLE_4, -1);
            }
            if (pos == -1) {
                pos = (int) ((encoders.size() - 1) * Math.random());
            }
                String clipboard = ClipboardManager.getClipboard(context);
                if (clipboard != null) {
                    String encode = encoders.get(Math.min(encoders.size() - 1, pos)).encode(clipboard);
                    ClipboardManager.setClipboard(context, encode);
            }
        }
    }

    private void complainNotSet(Context context) {
        Toast.makeText(context, "Unset", Toast.LENGTH_SHORT).show();
    }
}
