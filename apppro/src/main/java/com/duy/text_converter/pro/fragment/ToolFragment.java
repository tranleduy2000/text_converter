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

package com.duy.text_converter.pro.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duy.text_converter.pro.R;
import com.duy.text_converter.pro.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Duy on 10-Aug-17.
 */

public class ToolFragment extends Fragment implements View.OnClickListener {
    private static final int SELECT_FILE_ENCODE = 1001;
    private static final int SELECT_FILE_DECODE = 1002;

    public static ToolFragment newInstance() {
        Bundle args = new Bundle();
        ToolFragment fragment = new ToolFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tool, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.card_encode_file).setOnClickListener(this);
        view.findViewById(R.id.card_decode_file).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_encode_file:
                selectFile(SELECT_FILE_ENCODE);
                break;
            case R.id.card_decode_file:
                selectFile(SELECT_FILE_DECODE);
                break;
        }
    }

    private void selectFile(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_FILE_DECODE:
                break;
            case SELECT_FILE_ENCODE:
                if (resultCode == RESULT_OK) {
                    encodeFile(data.getData());
                }
                break;
        }
    }

    private void encodeFile(Uri data) {
    }

    private class EncodeTask extends AsyncTask<File, Integer, File> {

        @Override
        protected File doInBackground(File... params) {
            try {
                File file = params[0];
                String content = FileUtil.streamToString(new FileInputStream(file));

            } catch (Exception e) {

            }
            return null;
        }
    }
}
