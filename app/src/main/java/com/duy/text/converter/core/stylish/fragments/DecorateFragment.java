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

package com.duy.text.converter.core.stylish.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.duy.common.utils.DLog;
import com.duy.text.converter.R;
import com.duy.text.converter.core.stylish.DecorateTool;
import com.duy.text.converter.core.stylish.adapter.ResultAdapter;

import java.util.ArrayList;


/**
 * Created by Duy on 07-Jun-17.
 */

public class DecorateFragment extends Fragment implements TextWatcher {
    private static final String KEY = "DecorateFragment";
    private static final String TAG = "DecorateFragment";

    private View mRootView;
    private EditText mInput;
    private RecyclerView mListResult;
    private ResultAdapter mAdapter;


    public static DecorateFragment newInstance() {
        Bundle args = new Bundle();
        DecorateFragment fragment = new DecorateFragment();
        fragment.setArguments(args);
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
        mRootView = inflater.inflate(R.layout.fragment_stylish, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInput = mRootView.findViewById(R.id.edit_input);
        mAdapter = new ResultAdapter(getActivity(), R.layout.list_item_style);

        mListResult = mRootView.findViewById(R.id.recycler_view);
        mListResult.setLayoutManager(new LinearLayoutManager(getContext()));
        mListResult.setHasFixedSize(true);
        mListResult.setAdapter(mAdapter);
        mListResult.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mInput.addTextChangedListener(this);
    }

    private void convert() {
        String input = mInput.getText().toString();
        DLog.d(TAG, "convert: " + input);
        ArrayList<String> converted = DecorateTool.generate(input);
        mAdapter.setData(converted);
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

    public void saveData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.edit().putString(KEY + 1, mInput.getText().toString()).apply();
    }

    public void restore() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mInput.setText(sharedPreferences.getString(KEY + 1, ""));
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    public void onResume() {
        super.onResume();
        restore();
    }
}
