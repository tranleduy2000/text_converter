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

package com.duy.sharedcode.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.duy.sharedcode.codec.CodecMethod;
import com.duy.sharedcode.codec.CodecUtil;
import com.duy.sharedcode.utils.ClipboardUtil;
import com.duy.sharedcode.utils.ShareManager;
import com.duy.sharedcode.view.BaseEditText;
import com.duy.text.converter.R;


/**
 * TextFragment
 * Created by DUy on 06-Feb-17.
 */

public class CodecFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "CodecFragment";
    private BaseEditText mInput, mOutput;
    private Spinner mChoose;
    private TextWatcher mOutputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mOutput.isFocused()) convert(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher mInputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mInput.isFocused()) convert(true);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public static CodecFragment newInstance(String init) {
        CodecFragment fragment = new CodecFragment();
        Bundle bundle = new Bundle();
        if (init != null) {
            bundle.putString(Intent.EXTRA_TEXT, init);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_codec, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInput = view.findViewById(R.id.edit_input);
        mOutput = view.findViewById(R.id.edit_output);
        mInput.addTextChangedListener(mInputWatcher);
        mOutput.addTextChangedListener(mOutputWatcher);


        view.findViewById(R.id.image_paste).setOnClickListener(this);
        view.findViewById(R.id.image_paste_out).setOnClickListener(this);
        view.findViewById(R.id.img_copy).setOnClickListener(this);
        view.findViewById(R.id.img_copy_out).setOnClickListener(this);
        view.findViewById(R.id.img_share).setOnClickListener(this);
        view.findViewById(R.id.img_share_out).setOnClickListener(this);

        String[] data = getResources().getStringArray(R.array.codec_methods);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, data);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mChoose = view.findViewById(R.id.spinner_choose);
        mChoose.setAdapter(adapter);
        mChoose.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.img_copy) {
            ClipboardUtil.setClipboard(getContext(), mInput.getText().toString());

        } else if (i == R.id.image_paste) {
            mInput.setText(ClipboardUtil.getClipboard(getContext()));

        } else if (i == R.id.img_copy_out) {
            ClipboardUtil.setClipboard(getContext(), mOutput.getText().toString());

        } else if (i == R.id.image_paste_out) {
            mOutput.setText(ClipboardUtil.getClipboard(getContext()));

        } else if (i == R.id.img_share) {
            doShareText(mInput);

        } else if (i == R.id.img_share_out) {
            doShareText(mOutput);

        }
    }

    @Override
    public void onDestroyView() {
        mInput.removeTextChangedListener(mInputWatcher);
        mOutput.removeTextChangedListener(mOutputWatcher);
        super.onDestroyView();
    }

    private void doShareText(EditText editText) {
        ShareManager.share(editText.getText().toString(), getContext());
    }

    private void convert(boolean isEncode) {
        int index = mChoose.getSelectedItemPosition();
        CodecMethod method = CodecMethod.values()[index];
        if (isEncode) {
            String inp = mInput.getText().toString();
            mOutput.setText(CodecUtil.encode(method, inp));
        } else {
            String out = mOutput.getText().toString();
            mInput.setText(CodecUtil.decode(method, out));
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
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref.edit().putString(TAG, mInput.getText().toString()).apply();
    }

    public void restore() {
        String text = getArguments().getString(Intent.EXTRA_TEXT, "");
        if (!text.isEmpty()) {
            mInput.setText(text);
        } else {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
            mInput.setText(pref.getString(TAG, ""));
        }
        convert(true);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (mInput.hasFocus()) {
            convert(true);
        } else {
            convert(false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
