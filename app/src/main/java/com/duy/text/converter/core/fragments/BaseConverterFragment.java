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

package com.duy.text.converter.core.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.duy.common.utils.ShareUtil;
import com.duy.text.converter.R;
import com.duy.text.converter.core.utils.ClipboardUtil;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Duy on 2/10/2018.
 */

public class BaseConverterFragment extends Fragment implements View.OnClickListener {
    private HashMap<Base, EditText> mEditTextBase = new HashMap<>();

    public static BaseConverterFragment newInstance() {

        Bundle args = new Bundle();

        BaseConverterFragment fragment = new BaseConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_converter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //bind view
        mEditTextBase.clear();
        mEditTextBase.put(Base.BINARY, (EditText) view.findViewById(R.id.edit_binary));
        mEditTextBase.put(Base.OCTAL, (EditText) view.findViewById(R.id.edit_octal));
        mEditTextBase.put(Base.DECIMAL, (EditText) view.findViewById(R.id.edit_decimal));
        mEditTextBase.put(Base.HEX, (EditText) view.findViewById(R.id.edit_hex));

        //set input filter, disable keyboard
        for (Map.Entry<Base, EditText> entry : mEditTextBase.entrySet()) {
            EditText editText = entry.getValue();
            editText.addTextChangedListener(new OnBaseChangeListener(editText, entry.getKey()));
            editText.setFilters(new InputFilter[]{new BaseInputFilter(entry.getKey())});
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                editText.setShowSoftInputOnFocus(false);
//            } else {
//                editText.setTextIsSelectable(true);
//            }
        }

        addKeyEvent(view.findViewById(R.id.container_keyboard));
    }

    private void addKeyEvent(View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                addKeyEvent(((ViewGroup) view).getChildAt(i));
            }
        } else {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onDestroyView() {
        mEditTextBase.clear();
        super.onDestroyView();
    }


    private String convert(Base fromBase, Base newBase, String source) {
        try {
            BigInteger bigInteger = new BigInteger(source, fromBase.getRadix());
            return bigInteger.toString(newBase.getRadix()).toUpperCase(Locale.US);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                backspace();
                break;
            case R.id.btn_clear:
                clear();
                break;
            case R.id.btn_copy:
                copy();
                break;
            case R.id.btn_paste:
                paste();
                break;
            case R.id.btn_share:
                shareCurrentText();
                break;
            default:
                if (v instanceof Button) {
                    insert(((Button) v).getText());
                }
                break;
        }
    }

    private void shareCurrentText() {
        EditText currentEditText = getCurrentEditText();
        if (currentEditText != null){
            ShareUtil.shareText(getContext(), currentEditText.getText());
        }
    }

    private void paste() {
        EditText editText = getCurrentEditText();
        if (editText != null) {
            String clipboard = ClipboardUtil.getClipboard(getContext());
            editText.setText(clipboard);
        }
    }

    private void copy() {
        EditText editText = getCurrentEditText();
        if (editText != null) {
            ClipboardUtil.setClipboard(getContext(), editText.getText().toString());
        }
    }

    private void clear() {
        EditText editText = getCurrentEditText();
        if (editText != null) {
            editText.getText().clear();
            editText.setSelection(0);
        }
    }

    private void backspace() {
        EditText editText = getCurrentEditText();
        if (editText != null) {
            int selectionStart = editText.getSelectionStart();
            int selectionEnd = editText.getSelectionEnd();
            selectionStart = Math.max(0, selectionStart);
            selectionEnd = Math.max(0, selectionEnd);
            if (selectionStart != selectionEnd) {
                editText.getText().replace(selectionStart, selectionEnd, "");
            } else {
                if (selectionEnd > 0) {
                    editText.getText().delete(selectionEnd - 1, selectionEnd);
                    editText.setSelection(selectionEnd - 1);
                }
            }
        }
    }

    private void insert(CharSequence text) {
        EditText editText = getCurrentEditText();
        if (editText != null) {
            int selectionStart = editText.getSelectionStart();
            int selectionEnd = editText.getSelectionEnd();
            selectionStart = Math.max(0, selectionStart);
            selectionEnd = Math.max(0, selectionEnd);
            editText.getText().replace(selectionStart, selectionEnd, "");
            editText.getText().insert(selectionStart, text);
        }

    }

    @Nullable
    private EditText getCurrentEditText() {
        for (Map.Entry<Base, EditText> entry : mEditTextBase.entrySet()) {
            EditText editText = entry.getValue();
            if (editText.isFocused()) {
                return editText;
            }
        }
        return null;
    }

    enum Base {
        BINARY(2), OCTAL(8), DECIMAL(10), HEX(16);

        private int radix;

        Base(int radix) {
            this.radix = radix;
        }

        public int getRadix() {
            return radix;
        }
    }

    private class OnBaseChangeListener implements TextWatcher {

        private EditText parent;
        private Base base;

        OnBaseChangeListener(EditText parent, Base base) {
            this.parent = parent;
            this.base = base;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (parent.isFocused()) {
                for (Map.Entry<Base, EditText> entry : mEditTextBase.entrySet()) {
                    Base newBase = entry.getKey();
                    if (!newBase.equals(base)) {
                        EditText editText = entry.getValue();
                        editText.setText(convert(base, newBase, s.toString()));
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class BaseInputFilter implements InputFilter {
        private static final String NUMBER = "0123456789ABCDEF";
        private String mAcceptChar;

        public BaseInputFilter(Base base) {
            mAcceptChar = NUMBER.substring(0, base.getRadix());
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String str = source.toString();
            StringBuilder result = new StringBuilder();
            for (char c : str.toCharArray()) {
                if (mAcceptChar.contains(Character.toString(Character.toUpperCase(c)))) {
                    result.append(c);
                }
            }
            return result.toString();
        }
    }
}
