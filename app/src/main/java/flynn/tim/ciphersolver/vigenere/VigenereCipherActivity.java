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

package flynn.tim.ciphersolver.vigenere;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.duy.common.ads.AdsManager;
import com.duy.text.converter.R;
import com.duy.text.converter.activities.base.BaseActivity;
import com.duy.text.converter.core.stylish.adapter.ResultAdapter;
import com.duy.text.converter.pro.license.Premium;

import java.util.ArrayList;

public class VigenereCipherActivity extends BaseActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    private EditText mInput;
    private RecyclerView mListResult;
    private RadioButton mCkbEncrypt;
    private EditText mInputKey;
    private ResultAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigenere_cipher);
        setupToolbar();
        setTitle(R.string.title_vigenere_cipher);

        mInput = findViewById(R.id.edit_input);
        mInputKey = findViewById(R.id.edit_key);

        mAdapter = new ResultAdapter(this, R.layout.list_item_style);
        mListResult = findViewById(R.id.recycler_view);
        mListResult.setLayoutManager(new LinearLayoutManager(this));
        mListResult.setHasFixedSize(true);
        mListResult.setAdapter(mAdapter);
        mListResult.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mCkbEncrypt = findViewById(R.id.ckb_encrypt);
        mCkbEncrypt.setChecked(true);

        addEvent();
        setInputFilters();

        if (Premium.isPremium(this)){
            View containerAd = findViewById(R.id.container_ad);
            if (containerAd != null) containerAd.setVisibility(View.GONE);
        }else {
            AdsManager.loadAds(this, findViewById(R.id.container_ad), findViewById(R.id.ad_view));
        }
    }

    private void addEvent() {
        mInput.addTextChangedListener(this);
        mInputKey.addTextChangedListener(this);
        mCkbEncrypt.setOnCheckedChangeListener(this);
    }

    private void setInputFilters() {
        mInput.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) { // for backspace
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });

        mInputKey.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) { // for backspace
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });
    }

    private void processData() {
        String result;
        if (mInput.getText().toString().trim().equals("")) {
            result = "No ciphertext entered!";
        } else if (mInputKey.getText().toString().trim().equals("")) {
            result = "No keyword entered!";
        } else {
            if (mCkbEncrypt.isChecked()) {
                result = VigenereCipher.encrypt(mInput.getText().toString().toUpperCase().trim(), mInputKey.getText().toString().trim());
            } else {
                result = VigenereCipher.decrypt(mInput.getText().toString().toUpperCase().trim(), mInputKey.getText().toString().trim());
            }
        }
        ArrayList<String> list = new ArrayList<>();
        list.add(result);
        mAdapter.setData(list);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        processData();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        processData();
    }
}
