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

package com.duy.text.converter.pro.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.duy.sharedcode.codec.CodecUtil;
import com.duy.sharedcode.utils.FileUtil;
import com.duy.text.converter.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Duy on 10-Aug-17.
 */

public class CodecFileFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_SELECT_FILE = 1002;
    private static final String TAG = "CodecFileFragment";
    private static final int RED_PERCUSSION_READ_STORAGE = 13;
    private String inputPath;
    private EditText mEditInputPath, mEditOutPath;
    private RadioButton mIsEncode;
    private Spinner mChoose;

    public static CodecFileFragment newInstance() {
        Bundle args = new Bundle();
        CodecFileFragment fragment = new CodecFileFragment();
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
        view.findViewById(R.id.btn_select_file).setOnClickListener(this);
        view.findViewById(R.id.btn_submit).setOnClickListener(this);
        mIsEncode = view.findViewById(R.id.rad_encode);
        mEditInputPath = view.findViewById(R.id.edit_input_path);
        mEditOutPath = view.findViewById(R.id.edit_output_path);
        if (savedInstanceState != null) {
            setInputPath(savedInstanceState.getString("input_path"));
            setOutputPath(savedInstanceState.getString("output_path"));
        }

        String[] data = getResources().getStringArray(R.array.codec_methods);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, data);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mChoose = view.findViewById(R.id.spinner_choose);
        mChoose.setAdapter(adapter);
        view.findViewById(R.id.btn_open_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openResult();
            }
        });
    }

    private void openResult() {
        String path = mEditOutPath.getText().toString();
        if (!path.isEmpty()) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                File file = new File(path);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(Uri.fromFile(file), "text/plain");
                Intent chooser = Intent.createChooser(intent, "Choose an application to open with:");
                startActivity(chooser);
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setOutputPath(final String outputPath) {
        mEditOutPath.post(new Runnable() {
            @Override
            public void run() {
                mEditOutPath.setText(outputPath);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_select_file) {
            selectFile();

        } else if (i == R.id.btn_submit) {
            progressFile();

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("input_path", inputPath);
        outState.putString("output_path", mEditOutPath.getText().toString());
    }

    private void progressFile() {
        new EncodeTask(getContext(), mIsEncode.isChecked(),
                mChoose.getSelectedItem().toString()).execute(inputPath);
    }

    private void selectFile() {
        if (!isPermissionGrated()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_SELECT_FILE);
            }
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_SELECT_FILE);
    }

    private boolean isPermissionGrated() {
        int i;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            i = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            if (i != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        i = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (i != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SELECT_FILE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectFile();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_SELECT_FILE:
                if (resultCode == RESULT_OK) {
                    setInputPath(data.getData().getPath());
                }
                break;
        }
    }

    public void setInputPath(final String inputPath) {
        this.inputPath = inputPath;
        mEditInputPath.post(new Runnable() {
            @Override
            public void run() {
                mEditInputPath.setText(inputPath);
            }
        });
    }

    private class EncodeTask extends AsyncTask<String, Integer, File> {

        private Context context;
        private boolean encode;
        private String methodName;
        private ProgressDialog mProgressDialog;

        public EncodeTask(Context context, boolean encode, String methodName) {
            this.context = context;
            this.encode = encode;
            this.methodName = methodName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setTitle("Progress...");
            mProgressDialog.setMax(3);
            mProgressDialog.show();
        }

        @Override
        protected File doInBackground(String... params) {
            Log.d(TAG, "doInBackground() called with: params = [" + params + "]");

            try {
                File file = new File(params[0]);
                Log.d(TAG, "doInBackground file = " + file);
                publishProgress(0);
                String content = FileUtil.streamToString(new FileInputStream(file));
                Log.d(TAG, "doInBackground content = " + content);

                publishProgress(1);
                content = encode ? CodecUtil.encode(methodName, context, content) :
                        CodecUtil.decode(methodName, context, content);

                publishProgress(2);
                File out = new File(FileUtil.APP_PATH, file.getName().replace(".", "_") + "_" + methodName + ".txt");
                if (!out.exists()) {
                    out.getParentFile().mkdirs();
                    out.createNewFile();
                }
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out));
                bufferedWriter.write(content);
                bufferedWriter.close();
                return out;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            switch (values[0]) {
                case 0:
                    mProgressDialog.setProgress(0);
                    mProgressDialog.setMessage("Reading input");
                    break;
                case 1:
                    mProgressDialog.setProgress(1);
                    mProgressDialog.setMessage("Progressing text");
                    break;
                case 2:
                    mProgressDialog.setProgress(2);
                    mProgressDialog.setMessage("Write to output");
                    break;
            }
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            if (file != null) {
                setOutputPath(file.getPath());
            } else {
                Toast.makeText(context, "Encode failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
