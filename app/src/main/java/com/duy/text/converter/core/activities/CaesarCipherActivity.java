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

package com.duy.text.converter.core.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.duy.text.converter.R;
import com.duy.text.converter.core.activities.base.BaseActivity;
import com.duy.text.converter.core.codec.CaesarCodec;

import java.util.ArrayList;

import flynn.tim.ciphersolver.Result;
import flynn.tim.ciphersolver.ResultListAdapter;

public class CaesarCipherActivity extends BaseActivity implements TextWatcher {

    private ArrayList<Result> mResultList = new ArrayList<>();
    private EditText mEditInput;
    private ListView mListView;
    private RadioButton mIsEncrypt;
    private Spinner mSpinnerOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caesar_cipher);
        setupToolbar();
        setTitle(R.string.title_menu_caesar_cipher);

        mEditInput = findViewById(R.id.edit_input);
        mListView = findViewById((R.id.list_result));
        mIsEncrypt = findViewById(R.id.ckb_encrypt);
        mSpinnerOffset = findViewById(R.id.spinner_offset);
        mIsEncrypt.setChecked(true);

        mEditInput.addTextChangedListener(this);
    }

    private void generateResult() {
        mResultList.clear();
        String input = mEditInput.getText().toString().trim();
        if (!input.isEmpty()) {
            if (mSpinnerOffset.getSelectedItem().toString().equalsIgnoreCase("All")) {
                if (mIsEncrypt.isChecked()) {
                    for (int offset = 1; offset <= 26; offset++) {
                        String result = new CaesarCodec(offset).encode(input);
                        mResultList.add(new Result("Offset " + offset + ":\t" + result, false, false));
                    }
                } else {
                    for (int offset = 1; offset <= 26; offset++) {
                        String result = new CaesarCodec(offset).decode(input);
                        mResultList.add(new Result("Offset " + offset + ":\t" + result, false, false));
                    }
                }
            } else {
                int offset = Integer.parseInt(mSpinnerOffset.getSelectedItem().toString());
                if (mIsEncrypt.isChecked()) {
                    String result;
                    result = new CaesarCodec(offset).encode(input);
                    mResultList.add(new Result(result, true, false));
                } else {
                    String result;
                    result = new CaesarCodec(offset).decode(input);
                    mResultList.add(new Result(result, true, false));
                }
            }
        }

        final ResultListAdapter adapter = new ResultListAdapter(CaesarCipherActivity.this, R.layout.list_item_caesar, mResultList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mResultList.get(position).getEx() && !mResultList.get(position).getChecked()) {
                    mResultList.get(position).setEx(true);
                } else if (mResultList.get(position).getEx()) {
                    mResultList.get(position).setEx(false);
                    mResultList.get(position).setChecked(true);
                } else if (mResultList.get(position).getChecked()) {
                    mResultList.get(position).setChecked(false);
                }
                adapter.updateList(mResultList);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String string = mResultList.get(pos).getResult().toUpperCase();
                if (mSpinnerOffset.getSelectedItem().toString().equalsIgnoreCase("All")) {
                    String[] parts = string.split(":");
                    String part2 = parts[1];
                    ClipData clip = ClipData.newPlainText("label", part2);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(CaesarCipherActivity.this, part2 + " copied to clipboard", Toast.LENGTH_SHORT).show();
                } else {
                    ClipData clip = ClipData.newPlainText("label", mResultList.get(pos).getResult().toUpperCase());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(CaesarCipherActivity.this, mResultList.get(pos).getResult().toUpperCase() + " copied to clipboard", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
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
