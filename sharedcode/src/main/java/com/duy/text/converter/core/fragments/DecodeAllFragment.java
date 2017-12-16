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

package com.duy.text.converter.core.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.duy.text.converter.core.codec.Decoder;
import com.duy.text.converter.R;

import java.util.ArrayList;

/**
 * Created by Duy on 11/13/2017.
 */

public class DecodeAllFragment extends Fragment {
    private static final String KEY_INPUT = "input";

    public static DecodeAllFragment newInstance(String input) {

        Bundle args = new Bundle();
        args.putString(KEY_INPUT, input);
        DecodeAllFragment fragment = new DecodeAllFragment();
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


        RecyclerView recyclerView = view.findViewById(R.id.list_decoded);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ResultAdapter(getContext(), input));
    }

    private static class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
        private final Context context;
        private final String input;
        private ArrayList<Decoder> mDecoders;
        private String[] mNames;

        public ResultAdapter(Context context, String input) {
            this.context = context;
            this.input = input;
            initDecoder(context);
        }

        private void initDecoder(Context context) {
            mDecoders = new ArrayList<>();
            mNames = context.getResources().getStringArray(R.array.codec_methods);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_decode_all,
                    parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return mNames.length;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtResult;
            ProgressBar progressBar;

            ViewHolder(View itemView) {
                super(itemView);
                setIsRecyclable(false);
                progressBar = itemView.findViewById(R.id.progress_bar);
                txtResult = itemView.findViewById(R.id.txt_result);
            }

        }
    }
}
