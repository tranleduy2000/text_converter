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

package com.duy.text.converter.pro.menu.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duy.text.converter.R;
import com.duy.text.converter.core.stylish.StylistGenerator;
import com.duy.text.converter.pro.menu.adapters.StylishProcessTextAdapter;

import java.util.ArrayList;


/**
 * Created by DUy on 07-Feb-17.
 */

public class StylistProcessTextFragment extends Fragment {
    public static final String KEY = "StylistFragment";
    private static final String KEY_INPUT = "KEY_INPUT";
    private StylishProcessTextAdapter mAdapter;

    public static StylistProcessTextFragment newInstance(String input) {
        StylistProcessTextFragment fragment = new StylistProcessTextFragment();
        Bundle args = new Bundle();
        args.putString(KEY_INPUT, input);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stylish_process_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String input = getArguments().getString(KEY_INPUT);

        RecyclerView mListResult = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mListResult.setLayoutManager(layoutManager);
        mListResult.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));

        mAdapter = new StylishProcessTextAdapter(getContext());
        if (getActivity() instanceof OnTextSelectedListener) {
            mAdapter.setListener((OnTextSelectedListener) getActivity());
        }
        mListResult.setAdapter(mAdapter);

        convert(input);
    }


    public void convert(String inp) {
        if (inp.isEmpty()) inp = "Type something";
        ArrayList<String> translate = new StylistGenerator(getContext()).generate(inp);
        mAdapter.addAll(translate);
    }


}
