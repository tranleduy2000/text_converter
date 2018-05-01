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

package com.duy.text.converter.pro.floating.stylish;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.duy.text.converter.R;
import com.duy.text.converter.core.stylish.StylistGenerator;
import com.duy.text.converter.core.stylish.adapter.ResultAdapter;
import com.duy.text.converter.pro.floating.FloatingView;

import java.util.ArrayList;

/**
 * Created by Duy on 9/4/2017.
 */

public class FloatingStylishService extends FloatingView implements TextWatcher {

    private StylistGenerator mGenerator;
    private EditText mInput;
    private RecyclerView mListResult;
    private ResultAdapter mAdapter;

    @NonNull
    @Override
    protected View inflateButton(@NonNull ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(R.layout.floating_stylish_icon, parent, false);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull ViewGroup parent) {
        mGenerator = new StylistGenerator(getContext());

        View view = LayoutInflater.from(getContext()).inflate(R.layout.floating_stylish, parent, false);
        mInput = view.findViewById(R.id.edit_input);

        mAdapter = new ResultAdapter(getContext(), R.layout.list_item_style_floating);

        mListResult = view.findViewById(R.id.recycler_view);
        mListResult.setLayoutManager(new LinearLayoutManager(getContext()));
        mListResult.setHasFixedSize(true);
        mListResult.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
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
                .setContentTitle(getString(R.string.action_floating_stylish))
                .setContentText(getString(R.string.floating_notification_description))
                .setContentIntent(PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .build();
    }


    public void convert() {
        String inp = mInput.getText().toString();
        if (inp.isEmpty()) inp = mInput.getHint().toString();
        ArrayList<String> translate = mGenerator.generate(inp);
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
