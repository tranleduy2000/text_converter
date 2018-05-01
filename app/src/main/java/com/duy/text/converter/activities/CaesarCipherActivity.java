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

package com.duy.text.converter.activities;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.duy.common.ads.AdsManager;
import com.duy.text.converter.R;
import com.duy.text.converter.activities.base.BaseActivity;
import com.duy.text.converter.core.codec.CaesarCodec;
import com.duy.text.converter.core.stylish.adapter.ResultAdapter;
import com.duy.text.converter.pro.license.Premium;

import java.util.ArrayList;
import java.util.Locale;

public class CaesarCipherActivity extends BaseActivity implements TextWatcher {

    private EditText mInput;
    private RecyclerView mListResult;
    private RadioButton mIsEncrypt;
    private Spinner mSpinnerOffset;

    private ResultAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caesar_cipher);
        setupToolbar();
        setTitle(R.string.title_menu_caesar_cipher);
        mIsEncrypt = findViewById(R.id.ckb_encrypt);
        mSpinnerOffset = findViewById(R.id.spinner_offset);
        mIsEncrypt.setChecked(true);

        mInput = findViewById(R.id.edit_input);
        mAdapter = new ResultAdapter(this, R.layout.list_item_style);

        mListResult = findViewById(R.id.recycler_view);
        mListResult.setLayoutManager(new LinearLayoutManager(this));
        mListResult.setHasFixedSize(true);
        mListResult.setAdapter(mAdapter);
        mListResult.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mInput.addTextChangedListener(this);
        if (Premium.isPremium(this)) {
            View containerAd = findViewById(R.id.container_ad);
            if (containerAd != null) containerAd.setVisibility(View.GONE);
        } else {
            AdsManager.loadAds(this, findViewById(R.id.container_ad), findViewById(R.id.ad_view));
        }

        mSpinnerOffset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                generateResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void generateResult() {
        ArrayList<String> resultList = new ArrayList<>();
        resultList.clear();
        String input = mInput.getText().toString().trim();
        if (!input.isEmpty()) {
            if (mSpinnerOffset.getSelectedItem().toString().equalsIgnoreCase("All")) {
                if (mIsEncrypt.isChecked()) {
                    for (int offset = 1; offset <= 26; offset++) {
                        String result = new CaesarCodec(offset).encode(input);
                        resultList.add(String.format(Locale.US, "Offset %d:%s", offset, result));
                    }
                } else {
                    for (int offset = 1; offset <= 26; offset++) {
                        String result = new CaesarCodec(offset).decode(input);
                        resultList.add(String.format(Locale.US, "Offset %d:%s", offset, result));
                    }
                }
            } else {
                int offset = Integer.parseInt(mSpinnerOffset.getSelectedItem().toString());
                if (mIsEncrypt.isChecked()) {
                    String result;
                    result = new CaesarCodec(offset).encode(input);
                    resultList.add(result);
                } else {
                    String result;
                    result = new CaesarCodec(offset).decode(input);
                    resultList.add(result);
                }
            }
        }
        mAdapter.setData(resultList);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        generateResult();
    }
}
