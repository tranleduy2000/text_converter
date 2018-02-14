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

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duy.common.utils.DLog;
import com.duy.text.converter.R;
import com.duy.text.converter.core.adapters.EncodeResultAdapter;
import com.duy.text.converter.core.codec.interfaces.Codec;
import com.duy.text.converter.core.codec.interfaces.CodecMethod;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Duy on 16-Dec-17.
 */

public class EncodeAllFragment extends Fragment {
    private static final String KEY_INPUT = "KEY_INPUT";
    private static final String KEY_PROCESS_TEXT = "KEY_PROCESS_TEXT";
    @Nullable
    private EncodeTask mEncodeTask;
    private ArrayList<Codec> mEncoders;
    private EncodeResultAdapter mEncodeResultAdapter;

    public static EncodeAllFragment newInstance(String input, boolean processText) {

        Bundle args = new Bundle();
        args.putString(KEY_INPUT, input);
        args.putBoolean(KEY_PROCESS_TEXT, processText);
        EncodeAllFragment fragment = new EncodeAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_decode_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String input = getArguments().getString(KEY_INPUT);
        boolean processText = getArguments().getBoolean(KEY_PROCESS_TEXT);


        RecyclerView recyclerView = view.findViewById(R.id.list_decoded);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mEncodeResultAdapter = new EncodeResultAdapter(getContext(), processText);
        if (processText && getActivity() instanceof OnTextSelectedListener) {
            mEncodeResultAdapter.setListener((OnTextSelectedListener) getActivity());
        }
        recyclerView.setAdapter(mEncodeResultAdapter);

        initCodec();
        generateResult(input);
    }

    private void generateResult(String input) {
        if (mEncodeTask != null) mEncodeTask.cancel(true);
        mEncodeTask = new EncodeTask();
        mEncodeTask.execute(input);
    }

    @Override
    public void onDestroyView() {
        if (mEncodeTask != null) mEncodeTask.cancel(true);
        super.onDestroyView();
    }

    private void initCodec() {
        mEncoders = new ArrayList<>();
        CodecMethod[] values = CodecMethod.values();
        for (CodecMethod value : values) mEncoders.add(value.getCodec());
    }


    private class EncodeTask extends AsyncTask<String, Object, Void> {
        private static final String TAG = "EncodeTask";

        EncodeTask() {
        }

        @Override
        protected Void doInBackground(String... strings) {
            String input = strings[0];
            for (int i = 0, mDecodersSize = mEncoders.size(); i < mDecodersSize; i++) {
                Codec codec = mEncoders.get(i);
                if (isCancelled()) return null;
                String decode = codec.encode(input);
                publishProgress(decode, codec.getName(getContext()));
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            DLog.d(TAG, "onProgressUpdate() called with: values = [" + Arrays.toString(values) + "]");
            String result = (String) values[0];
            String name = (String) values[1];
            addToRecycleView(result, name);
        }

        private void addToRecycleView(String result, String name) {
            if (isCancelled()) return;
            EncodeResultAdapter.EncodeItem item = new EncodeResultAdapter.EncodeItem(name, result);
            mEncodeResultAdapter.add(item);
        }
    }
}
