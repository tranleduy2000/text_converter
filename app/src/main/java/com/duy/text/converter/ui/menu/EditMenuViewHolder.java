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

package com.duy.text.converter.ui.menu;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.duy.common.utils.ShareUtil;
import com.duy.text.converter.R;
import com.duy.text.converter.clipboard.ClipboardFactory;
import com.duy.text.converter.clipboard.IClipboard;

/**
 * Created by Duy on 13-Apr-18.
 */

public class EditMenuViewHolder implements View.OnClickListener {
    @NonNull
    private View mRootView;
    @NonNull
    private EditText mEditText;
    @NonNull
    private Context mContext;

    public EditMenuViewHolder(@NonNull View editMenuView, @NonNull EditText editText) {
        mRootView = editMenuView;
        mEditText = editText;
        mContext = editMenuView.getContext();
    }

    public void bind() {
        bindView(R.id.btn_copy, R.id.btn_paste, R.id.btn_share, R.id.btn_clear);
    }

    private void bindView(int... ids) {
        for (int id : ids) {
            View viewById = findViewById(id);
            if (viewById != null) {
                viewById.setOnClickListener(this);
            }
        }
    }

    @Nullable
    public final <T extends View> T findViewById(@IdRes int id) {
        if (id == View.NO_ID) {
            return null;
        }
        return mRootView.findViewById(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_copy:
                copy();
                break;
            case R.id.btn_paste:
                paste();
                break;
            case R.id.btn_share:
                share();
                break;
            case R.id.btn_clear:
                clear();
                break;
        }

    }

    public void clear() {
        mEditText.getText().clear();
    }

    public void share() {
        ShareUtil.shareText(mContext, mEditText.getText());
    }

    public void paste() {
        IClipboard clipboard = ClipboardFactory.createClipboardManager(mContext);
        CharSequence text = clipboard.getClipboard();
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        Editable editable = mEditText.getText();
        if (selectionEnd != selectionStart) {
            editable.delete(selectionStart, selectionEnd);
            editable.insert(selectionStart, text);
        } else {
            selectionStart = Math.max(0, selectionStart);
            editable.insert(selectionStart, text);
        }
        mEditText.setSelection(selectionStart);
        mEditText.requestFocus();
    }

    public void copy() {

        IClipboard clipboard = ClipboardFactory.createClipboardManager(mContext);
        boolean success = clipboard.setClipboard(mEditText.getText());
        if (success) {
            Toast.makeText(mContext, R.string.message_copied, Toast.LENGTH_SHORT).show();
        }
    }
}
