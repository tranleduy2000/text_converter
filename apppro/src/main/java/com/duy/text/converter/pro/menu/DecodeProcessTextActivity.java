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

package com.duy.text.converter.pro.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.duy.sharedcode.codec.CodecUtil;

/**
 * Created by Duy on 29-Jul-17.
 */

public class DecodeProcessTextActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        boolean readonly = getIntent().getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false);

        if (!readonly && text != null) {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            if (pref.getBoolean("pirate", false)) {
                Toast.makeText(this, "Pirate version", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            String method = pref.getString("pref_key_decode_menu", "");
            if (method.isEmpty()) {
                finish();
                return;
            }
            String result = CodecUtil.decode(method, this, text.toString());
            Intent intent = getIntent();
            intent.putExtra(Intent.EXTRA_PROCESS_TEXT, result);
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}
