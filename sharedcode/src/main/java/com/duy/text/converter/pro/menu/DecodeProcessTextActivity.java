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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.duy.common.utils.DLog;
import com.duy.text.converter.R;
import com.duy.text.converter.core.adapters.DecodeResultAdapter;
import com.duy.text.converter.core.fragments.DecodeAllFragment;
import com.duy.text.converter.pro.license.Premium;

/**
 * Created by Duy on 29-Jul-17.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class DecodeProcessTextActivity extends AppCompatActivity implements DecodeResultAdapter.OnTextSelectedListener {
    private static final String TAG = "DecodeProcessTextActivi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        if (text != null && !Premium.isCrack(this)) {
            setContentView(R.layout.activity_decode_process_text);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle(R.string.decode);
            toolbar.setSubtitle(text);

            String input = text.toString();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, DecodeAllFragment.newInstance(input, true)).commit();
        } else {
            DLog.d(TAG, "onCreate: " + text);
            finish();
        }
    }

    @Override
    public void onTextSelected(String text) {
        Intent intent = getIntent();
        intent.putExtra(Intent.EXTRA_PROCESS_TEXT, text);
        setResult(RESULT_OK, intent);
        finish();
    }
}
