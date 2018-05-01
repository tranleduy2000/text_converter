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

package com.duy.text.converter.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.Spinner;

import com.duy.text.converter.R;
import com.duy.text.converter.core.codec.interfaces.CodecMethod;
import com.duy.text.converter.ui.menu.EditMenuViewHolder;
import com.duy.text.converter.utils.ShareManager;
import com.duy.text.converter.view.BaseEditText;
import com.duy.text.converter.view.RoundedBackgroundEditText;
import com.duy.text.converter.pro.activities.CodecAllActivity;
import com.duy.text.converter.pro.license.Premium;


/**
 * TextFragment
 * Created by DUy on 06-Feb-17.
 */

public class CodecFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "CodecFragment";
    private BaseEditText mInput, mOutput;
    private Spinner mMethodSpinner;
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
        bundle.putString(Intent.EXTRA_TEXT, init);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_codec, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInput = view.findViewById(R.id.edit_input);
        mInput.setBackgroundDrawable(RoundedBackgroundEditText.createRoundedBackground(getContext()));

        mOutput = view.findViewById(R.id.edit_output);
        mOutput.setBackgroundDrawable(RoundedBackgroundEditText.createRoundedBackground(getContext()));

        mInput.addTextChangedListener(mInputWatcher);
        mOutput.addTextChangedListener(mOutputWatcher);

        EditMenuViewHolder menu = new EditMenuViewHolder(view.findViewById(R.id.edit_menu_input), mInput);
        menu.bind();

        menu = new EditMenuViewHolder(view.findViewById(R.id.edit_menu_output), mOutput);
        menu.bind();

        view.findViewById(R.id.img_encode_all).setOnClickListener(this);
        view.findViewById(R.id.img_decode_all).setOnClickListener(this);

        CodecMethod[] codecMethods = CodecMethod.values();
        String[] names = new String[codecMethods.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = codecMethods[i].getCodec().getName(getContext());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, names);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mMethodSpinner = view.findViewById(R.id.spinner_codec_methods);
        mMethodSpinner.setBackgroundDrawable(RoundedBackgroundEditText.createRoundedBackground(getContext()));
        mMethodSpinner.setAdapter(adapter);
        mMethodSpinner.setOnItemSelectedListener(this);

        restore();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.img_encode_all:
                encodeAll();
                break;
            case R.id.img_decode_all:
                decodeAll();
                break;
        }
    }

    private void decodeAll() {
        if (!Premium.isPremium(getContext())) {
            showDialogUpgrade();
        } else {
            Intent intent = new Intent(getContext(), CodecAllActivity.class);
            intent.setAction(CodecAllActivity.EXTRA_ACTION_DECODE);
            intent.putExtra(CodecAllActivity.EXTRA_INPUT, mOutput.getText().toString());
            startActivity(intent);
        }
    }

    private void encodeAll() {
        if (!Premium.isPremium(getContext())) {
            showDialogUpgrade();
        } else {
            Intent intent = new Intent(getContext(), CodecAllActivity.class);
            intent.setAction(CodecAllActivity.EXTRA_ACTION_ENCODE);
            intent.putExtra(CodecAllActivity.EXTRA_INPUT, mInput.getText().toString());
            startActivity(intent);
        }
    }

    private void showDialogUpgrade() {
        Premium.upgrade(getActivity());
    }

    @Override
    public void onDestroyView() {
        saveData();
        mInput.removeTextChangedListener(mInputWatcher);
        mOutput.removeTextChangedListener(mOutputWatcher);
        super.onDestroyView();
    }

    private void shareText(EditText editText) {
        ShareManager.share(editText.getText().toString(), getContext());
    }

    private void convert(boolean isEncode) {
        int index = mMethodSpinner.getSelectedItemPosition();
        CodecMethod method = CodecMethod.values()[index];
        if (isEncode) {
            String inp = mInput.getText().toString();
            mOutput.setText(method.getCodec().encode(inp));
        } else {
            String out = mOutput.getText().toString();
            mInput.setText(method.getCodec().decode(out));
        }
    }


    public void saveData() {
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
