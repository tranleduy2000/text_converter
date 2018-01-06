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

package com.duy.text.converter.pro.floating.stylish;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.duy.text.converter.core.adapters.StyleAdapter;
import com.duy.text.converter.core.stylish.StylistGenerator;
import com.duy.text.converter.R;
import com.duy.text.converter.pro.floating.FloatingView;

import java.util.ArrayList;

/**
 * Created by Duy on 9/4/2017.
 */

public class FloatingStylishService extends FloatingView implements TextWatcher {


    private EditText mInput;
    private RecyclerView mListResult;
    private StyleAdapter mAdapter;

    @NonNull
    @Override
    protected View inflateButton(@NonNull ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(R.layout.floating_stylish_icon, parent, false);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.floating_stylish, parent, false);
        mInput = view.findViewById(R.id.edit_input);
        mListResult = view.findViewById(R.id.recycler_view);
        mListResult.setLayoutManager(new LinearLayoutManager(getContext()));
        mListResult.setHasFixedSize(true);

        mAdapter = new StyleAdapter(getContext(), R.layout.list_item_style_floating);
        mListResult.setAdapter(mAdapter);

        mInput.addTextChangedListener(this);
        return view;
    }

    @NonNull
    @Override
    protected Notification createNotification() {
        Intent intent = new Intent(this, FloatingStylishService.class).setAction(ACTION_OPEN);
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.floating_stylish))
                .setContentText(getString(R.string.floating_notification_description))
                .setContentIntent(PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .build();
    }


    public void convert() {
        String inp = mInput.getText().toString();
        if (inp.isEmpty()) inp = mInput.getHint().toString();
        ArrayList<String> translate = new StylistGenerator(getContext()).generate( inp);
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

}
