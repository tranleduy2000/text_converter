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

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.duy.common.utils.DLog;
import com.duy.text.converter.R;
import com.duy.text.converter.core.barcode.BarcodeEncodedActivity;
import com.duy.text.converter.core.utils.ClipboardUtil;
import com.duy.text.converter.core.utils.ShareManager;
import com.duy.text.converter.core.view.BaseEditText;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.android.Intents;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;

import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Duy on 14-Aug-17.
 */

public class BarCodeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "BarCodeFragment";
    private static final String KEY_TEXT = "KEY_TEXT";
    private static final int REQUEST_PICK_IMAGE = 1010;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1011;
    private BaseEditText mInput;
    private DecodeImageTask mDecodeImageTask;

    public static BarCodeFragment newInstance() {

        Bundle args = new Bundle();

        BarCodeFragment fragment = new BarCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barcode, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInput = view.findViewById(R.id.edit_input);
        view.findViewById(R.id.img_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareManager.share(mInput.getText(), getContext());
            }
        });
        view.findViewById(R.id.img_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardUtil.setClipboard(getContext(), mInput.getText().toString());
            }
        });
        view.findViewById(R.id.image_paste).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clipboard = ClipboardUtil.getClipboard(getContext());
                mInput.setText(clipboard);
            }
        });

        String[] data = getResources().getStringArray(R.array.barcode_format);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        view.findViewById(R.id.btn_encode).setOnClickListener(this);
        view.findViewById(R.id.btn_decode_cam).setOnClickListener(this);
        view.findViewById(R.id.btn_decode_image).setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    @Override
    public void onResume() {
        super.onResume();
        restore();
    }

    public void save() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.edit().putString(TAG + getArguments().getInt(KEY_TEXT), mInput.getText().toString()).apply();
    }

    public void restore() {
        String text = getArguments().getString(Intent.EXTRA_TEXT, "");
        if (!text.isEmpty()) {
            mInput.setText(text);
        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            mInput.setText(sharedPreferences.getString(TAG + getArguments().getInt(KEY_TEXT), ""));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_encode:
                encode(mInput.getText().toString());
                break;
            case R.id.btn_decode_cam:
                decodeUseCamera();
                break;
            case R.id.btn_decode_image:
                decodeImage();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        decodeImage();
                    } else {
                        Toast.makeText(getContext(), R.string.read_permission_msg, Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                final String contents = data.getStringExtra(Intents.Scan.RESULT);
                mInput.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mInput.setText(contents);
                        if (getContext() != null) {
                            Toast.makeText(getContext(), R.string.decoded, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 200);
            }

        } else if (requestCode == REQUEST_PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (mDecodeImageTask != null && !mDecodeImageTask.isCancelled()) {
                    mDecodeImageTask.cancel(true);
                }
                mDecodeImageTask = new DecodeImageTask(getContext().getApplicationContext(), mInput);
                mDecodeImageTask.execute(data.getData());
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDecodeImageTask != null && !mDecodeImageTask.isCancelled()) {
            mDecodeImageTask.cancel(true);
        }
    }

    private void decodeUseCamera() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    private void encode(String s) {
        Intent intent = new Intent(getContext(), BarcodeEncodedActivity.class);
        intent.putExtra("data", s);
        startActivity(intent);
    }


    private void decodeImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int result = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            if (result != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), R.string.read_permission_msg, Toast.LENGTH_SHORT).show();
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, REQUEST_READ_EXTERNAL_STORAGE);
                return;
            }
        }

        try {
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, getString(R.string.select_image));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

            startActivityForResult(chooserIntent, REQUEST_PICK_IMAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private static class DecodeImageTask extends AsyncTask<Uri, Void, String> {
        private Context context;
        private TextView txtResult;

        private DecodeImageTask(@NonNull Context context, @NonNull TextView txtResult) {
            this.context = context;
            this.txtResult = txtResult;
        }

        @NonNull
        public Context getContext() {
            return context;
        }

        @Override
        protected String doInBackground(Uri... params) {
            Uri uri = params[0];
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.outWidth = 1024;
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, opts);
                if (bitmap == null) {
                    DLog.e(TAG, "uri is not a bitmap," + uri.toString());
                    return null;
                }
                int width = bitmap.getWidth(), height = bitmap.getHeight();
                int[] pixels = new int[width * height];
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                bitmap.recycle();
                RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                BinaryBitmap bBitmap = new BinaryBitmap(new HybridBinarizer(source));
                MultiFormatReader reader = new MultiFormatReader();
                try {
                    Result result = reader.decode(bBitmap);
                    return result.getText();
                } catch (NotFoundException e) {
                    DLog.e(TAG, "decode exception", e);
                    return null;
                }
            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (isCancelled()) {
                return;
            }
            if (s != null) {
                txtResult.setText(s);
                Toast.makeText(getContext(), R.string.decoded, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), R.string.cannot_decode, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
