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

package com.duy.text.converter.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.duy.common.utils.ShareUtil;
import com.duy.text.converter.R;
import com.duy.text.converter.core.hashfunction.IHash;
import com.duy.text.converter.core.hashfunction.Md5Hash;
import com.duy.text.converter.core.hashfunction.Sha1Hash;
import com.duy.text.converter.core.hashfunction.Sha256Hash;
import com.duy.text.converter.core.hashfunction.Sha384Hash;
import com.duy.text.converter.core.hashfunction.Sha512Hash;
import com.duy.text.converter.clipboard.ClipboardUtil;
import com.duy.text.converter.view.BaseEditText;
import com.duy.text.converter.view.RoundedBackgroundEditText;

import java.util.ArrayList;

/**
 * Created by Duy on 08-Aug-17.
 */

public class HashFragment extends Fragment {
    private static final String TAG = "HashFragment";
    private final Handler mHandler = new Handler();
    private ArrayList<IHash> mHashFunctions = new ArrayList<>();
    private BaseEditText mInput, mOutput;
    private Spinner mMethodSpinner;
    private final Runnable convertRunnable = new Runnable() {
        @Override
        public void run() {
            convert();
        }
    };
    private TextWatcher mInputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mHandler.removeCallbacks(convertRunnable);
            mHandler.postDelayed(convertRunnable, 300);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public static HashFragment newInstance() {
        Bundle args = new Bundle();
        HashFragment fragment = new HashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mHashFunctions.clear();
        mHashFunctions.add(new Md5Hash());
        mHashFunctions.add(new Sha1Hash());
        mHashFunctions.add(new Sha256Hash());
        mHashFunctions.add(new Sha384Hash());
        mHashFunctions.add(new Sha512Hash());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHashFunctions.clear();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInput = view.findViewById(R.id.edit_input);
        mOutput = view.findViewById(R.id.edit_output);
        mInput.addTextChangedListener(mInputWatcher);

        mMethodSpinner = view.findViewById(R.id.spinner_hash_methods);
        mMethodSpinner.setBackgroundDrawable(RoundedBackgroundEditText.createRoundedBackground(getContext()));
        view.findViewById(R.id.img_share_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.shareText(getContext(), mOutput.getText().toString());
            }
        });
        view.findViewById(R.id.img_copy_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardUtil.setClipboard(getContext(), mOutput.getText().toString());
            }
        });

        ArrayList<String> names = new ArrayList<>();
        for (IHash mHashFunction : mHashFunctions) {
            names.add(mHashFunction.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, names);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mMethodSpinner.setAdapter(adapter);
        mMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void convert() {
        int index = mMethodSpinner.getSelectedItemPosition();
        try {
            IHash hashFunction = mHashFunctions.get(index);
            String encoded = hashFunction.encode(mInput.getText().toString());
            mOutput.setText(encoded);
        } catch (Throwable e) { //out of memory
            e.printStackTrace();
        }
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

    public void save() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.edit().putString(TAG, mInput.getText().toString()).apply();
    }

    public void restore() {
        String text = getArguments().getString(Intent.EXTRA_TEXT, "");
        if (!text.isEmpty()) {
            mInput.setText(text);
        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            mInput.setText(sharedPreferences.getString(TAG, ""));
        }
        convert();
    }


}
