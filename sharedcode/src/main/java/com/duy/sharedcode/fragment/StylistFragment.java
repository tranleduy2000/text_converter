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

package com.duy.sharedcode.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.duy.sharedcode.adapters.StyleAdapter;
import com.duy.sharedcode.effect.StyleTool;
import com.duy.textconverter.sharedcode.R;

import java.util.ArrayList;



/**
 * Created by DUy on 07-Feb-17.
 */

public class StylistFragment extends Fragment implements TextWatcher {
    private static final String TAG = ConverterFragment.class.getSimpleName();
    public static String KEY = "StylistFragment";
    private Context mContext;
    private EditText mInput;
    private RecyclerView mListResult;
    private StyleAdapter mAdapter;

    public static StylistFragment newInstance() {

        Bundle args = new Bundle();

        StylistFragment fragment = new StylistFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_style_list, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInput = view.findViewById(R.id.edit_input);
        mListResult = view.findViewById(R.id.list_out);
        mListResult.setLayoutManager(new LinearLayoutManager(mContext));
        mListResult.setHasFixedSize(true);

        mAdapter = new StyleAdapter(getActivity());
        mListResult.setAdapter(mAdapter);

        mInput.addTextChangedListener(this);
    }


    public void convert() {
        String inp = mInput.getText().toString();
        if (inp.isEmpty()) {
            inp = mInput.getHint().toString();
        }
        ArrayList<String> translate = StyleTool.convert(inp);
        mAdapter.setData(translate);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        convert();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void save() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences.edit().putString(KEY + 1, mInput.getText().toString()).apply();
    }

    public void restore() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mInput.setText(sharedPreferences.getString(KEY + 1, ""));

    }

    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    @Override
    public void onResume() {
        super.onResume();
        restore();
    }
}
