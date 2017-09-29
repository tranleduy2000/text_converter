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

package com.duy.text_converter.pro.floating.codec;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duy.sharedcode.ClipboardUtil;
import com.duy.sharedcode.codec.CodecMethod;
import com.duy.sharedcode.codec.CodecUtil;
import com.duy.sharedcode.view.BaseEditText;
import com.duy.text_converter.pro.R;
import com.xlythe.view.floating.FloatingView;

/**
 * Created by Duy on 9/4/2017.
 */

public class FloatingCodecService extends FloatingView implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private BaseEditText mInput, mOutput;
    private ViewPager mConvertMethod;
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

    @NonNull
    @Override
    protected View inflateButton(@NonNull ViewGroup parent) {
        return LayoutInflater.from(this).inflate(R.layout.floating_converter_icon, parent, false);
    }

    @NonNull
    @Override
    protected View inflateView(@NonNull ViewGroup parent) {
        ContextThemeWrapper context = new ContextThemeWrapper(this, R.style.AppTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.floating_codec, parent, false);
        mInput = view.findViewById(R.id.edit_input);
        mOutput = view.findViewById(R.id.edit_output);
        mInput.addTextChangedListener(mInputWatcher);
        mOutput.addTextChangedListener(mOutputWatcher);

        view.findViewById(R.id.img_copy).setOnClickListener(this);
        view.findViewById(R.id.image_paste).setOnClickListener(this);
        view.findViewById(R.id.img_copy_out).setOnClickListener(this);
        view.findViewById(R.id.image_paste_out).setOnClickListener(this);

        mConvertMethod = view.findViewById(R.id.spinner_choose);
        mConvertMethod.setAdapter(new CodecMethodAdapter(getContext()));
        mConvertMethod.addOnPageChangeListener(this);
        ((TabLayout) view.findViewById(R.id.tab_layout)).setupWithViewPager(mConvertMethod);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_copy:
                ClipboardUtil.setClipboard(getContext(), mInput.getText().toString());
                break;
            case R.id.image_paste:
                mInput.setText(ClipboardUtil.getClipboard(getContext()));
                break;
            case R.id.img_copy_out:
                ClipboardUtil.setClipboard(getContext(), mOutput.getText().toString());
                break;
            case R.id.image_paste_out:
                mOutput.setText(ClipboardUtil.getClipboard(getContext()));
                break;
        }
    }

    private void convert(boolean to) {
        int index = mConvertMethod.getCurrentItem();
        CodecMethod method = CodecMethod.values()[index];
        if (to) {
            String inp = mInput.getText().toString();
            mOutput.setText(CodecUtil.encode(method, inp));
        } else {
            String out = mOutput.getText().toString();
            mInput.setText(CodecUtil.decode(method, out));
        }
    }

    @NonNull
    @Override
    protected Notification createNotification() {
        Intent intent = new Intent(this, FloatingCodecService.class).setAction(ACTION_OPEN);
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.floating_notification_description))
                .setContentIntent(PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .build();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mInput.hasFocus()) {
            convert(true);
        } else {
            convert(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
