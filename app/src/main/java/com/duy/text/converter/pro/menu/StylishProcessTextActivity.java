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

package com.duy.text.converter.pro.menu;
//Need the following import to get access to the app resources, since this
//class is in a sub-package.


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.duy.text.converter.R;
import com.duy.text.converter.pro.license.Premium;
import com.duy.text.converter.pro.menu.fragments.OnTextSelectedListener;
import com.duy.text.converter.pro.menu.fragments.StylistProcessTextFragment;

@TargetApi(Build.VERSION_CODES.M)
public class StylishProcessTextActivity extends AppCompatActivity implements OnTextSelectedListener {
    private static final String TAG = "EncodeAllProcessTextActivity";
    private static final String KEY_CHANCE = "StylishProcessTextActivity_KEY_CHANCE";
    private static final int MAX_CHANCE_VALUE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        if (text != null) {
            setContentView(R.layout.activity_process_text);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle(R.string.process_text_title_stylish_it);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setSubtitle(text);

            TextView txtMessage = findViewById(R.id.txt_message);
            if (Premium.isPremium(this)) {
                txtMessage.setVisibility(View.GONE);
            } else {
                txtMessage.setText(getString(R.string.chance_remaining, getChanceRemaining()));
                txtMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Premium.upgrade(StylishProcessTextActivity.this);
                    }
                });
            }


            String input = text.toString();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, StylistProcessTextFragment.newInstance(input)).commit();
        } else {
            finish();
        }
    }

    private int getChanceRemaining() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        return pref.getInt(KEY_CHANCE, MAX_CHANCE_VALUE);
    }

    @Override
    public void onTextSelected(String text) {
        if (!Premium.isPremium(this)) {
            if (getChanceRemaining() <= 0) {
                Toast.makeText(this, R.string.please_upgrade, Toast.LENGTH_SHORT).show();
                return;
            } else {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                pref.edit().putInt(KEY_CHANCE, getChanceRemaining() - 1).apply();
            }
        }
        Intent intent = getIntent();
        intent.putExtra(Intent.EXTRA_PROCESS_TEXT, text);
        setResult(RESULT_OK, intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}