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

package com.duy.sharedcode.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.duy.sharedcode.ClipboardUtil;
import com.duy.sharedcode.barcode.BarcodeEncodeActivity;
import com.duy.sharedcode.view.BaseEditText;
import com.duy.textconverter.sharedcode.R;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.android.Intents;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Duy on 14-Aug-17.
 */

public class BarCodeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "BarCodeFragment";
    private static final String KEY_TEXT = "KEY_TEXT";
    private static final int REQUEST_PICK_IMAGE = 1010;
    private BaseEditText mInput;
    private DecodeImageTask decodeImageTask;

    public static BarCodeFragment newInstance() {

        Bundle args = new Bundle();

        BarCodeFragment fragment = new BarCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barcode, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInput = view.findViewById(R.id.edit_input);
        view.findViewById(R.id.img_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doShareText(mInput);
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
    }

    private void doShareText(EditText editText) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
        intent.setType("text/plain");
        startActivity(intent);
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
        if (v.getId() == R.id.btn_encode) {
            encode(mInput.getText().toString());
        } else if (v.getId() == R.id.btn_decode_cam) {
            decodeUseCamera();
        } else if (v.getId() == R.id.btn_decode_image) {
            decodeImage();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    final String contents = data.getStringExtra(Intents.Scan.RESULT);
                    mInput.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mInput.setText(contents);
                            if (getContext() != null) {
                                Toast.makeText(getContext(), "Decoded", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 200);
                }
                break;
            case REQUEST_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    if (decodeImageTask != null && !decodeImageTask.isCancelled()) {
                        decodeImageTask.cancel(true);
                    }
                    decodeImageTask = new DecodeImageTask();
                    decodeImageTask.execute(data.getData());
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (decodeImageTask != null && !decodeImageTask.isCancelled()) decodeImageTask.cancel(true);
    }

    private void decodeUseCamera() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    private void encode(String s) {
        Intent intent = new Intent(getContext(), BarcodeEncodeActivity.class);
        intent.putExtra("data", s);
        startActivity(intent);
    }


    private void decodeImage() {
        try {

            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

            startActivityForResult(chooserIntent, REQUEST_PICK_IMAGE);
        } catch (Exception e) {
        }
    }

    private class DecodeImageTask extends AsyncTask<Uri, Void, String> {
        @Override
        protected String doInBackground(Uri... params) {
            Uri uri = params[0];
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.outWidth = 1024;
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, opts);
                if (bitmap == null) {
                    Log.e(TAG, "uri is not a bitmap," + uri.toString());
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
                    Log.e(TAG, "decode exception", e);
                    return null;
                }
            } catch (FileNotFoundException e) {
                Log.e(TAG, "can not open file" + uri.toString(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (isCancelled()) return;
            if (s != null) {
                mInput.setText(s);
                Toast.makeText(getContext(), R.string.decoded, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
