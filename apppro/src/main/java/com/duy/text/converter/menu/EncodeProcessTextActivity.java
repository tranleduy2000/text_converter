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

package com.duy.text.converter.menu;
//Need the following import to get access to the app resources, since this
//class is in a sub-package.


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.duy.sharedcode.codec.CodecUtil;

@TargetApi(Build.VERSION_CODES.M)
public class EncodeProcessTextActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        boolean readonly = getIntent().getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false);

        if (!readonly && text != null && !text.toString().isEmpty()) {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            if (pref.getBoolean("pirate", false)) {
                Toast.makeText(this, "Pirate version", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            String method = pref.getString("pref_key_encode_menu", "");
            if (method.isEmpty()) {
                finish();
                return;
            }
            String result = CodecUtil.encode(method, this, text.toString());
            Intent intent = getIntent();
            intent.putExtra(Intent.EXTRA_PROCESS_TEXT, result);
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}