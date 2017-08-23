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

package com.duy.sharedcode.barcode;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.duy.textconverter.sharedcode.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * Created by Duy on 23-Aug-17.
 */

public class BarcodeEncoderFragment extends Fragment {
    private TextView txtError;
    private ImageView imageBarcode;
    private BarcodeGenerateTask generateTask;
    private Bitmap currentBarcode = null;
    private ProgressBar progressBar;

    public static BarcodeEncoderFragment newInstance(String data, BarcodeFormat barcodeFormat) {
        Bundle args = new Bundle();
        args.putString("data", data);
        args.putSerializable("format", barcodeFormat);
        BarcodeEncoderFragment fragment = new BarcodeEncoderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barcode_encoded, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageBarcode = view.findViewById(R.id.img_barcode);
        txtError = view.findViewById(R.id.txt_message);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        String data = getArguments().getString("data");
        BarcodeFormat barcodeFormat = (BarcodeFormat) getArguments().getSerializable("format");
        generateTask = new BarcodeGenerateTask(data, barcodeFormat);
        generateTask.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (generateTask != null) {
            if (!generateTask.isCancelled()) {
                generateTask.cancel(true);
            }
        }
        if (currentBarcode != null && !currentBarcode.isRecycled()) currentBarcode.recycle();
    }

    private boolean permissionGranted() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void shareCurrentImage() {
        if (!permissionGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                return;
            }
        }

        if (currentBarcode != null) {
            try {
                String bitmapPath = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                        currentBarcode, System.currentTimeMillis() + ".png", "barcode");
                Uri bitmapUri = Uri.parse(bitmapPath);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                intent.setType("image/*");
                try {
                    startActivity(Intent.createChooser(intent, "Share via ..."));
                } catch (ActivityNotFoundException ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            shareCurrentImage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setCurrentBarcode(Bitmap bitmap) {
        if (currentBarcode != null && !currentBarcode.isRecycled()) currentBarcode.recycle();
        this.currentBarcode = bitmap;
        imageBarcode.setImageBitmap(bitmap);
    }

    private class BarcodeGenerateTask extends AsyncTask<Void, Void, Bitmap> {
        private final String data;
        private final BarcodeFormat format;
        private Exception error;

        BarcodeGenerateTask(String data, BarcodeFormat format) {
            this.data = data;
            this.format = format;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(data, format, 512, 512);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                return barcodeEncoder.createBitmap(bitMatrix);
            } catch (Exception e) {
                e.printStackTrace();
                this.error = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (isCancelled()) return;
            progressBar.setVisibility(View.INVISIBLE);
            if (bitmap != null) {
                txtError.setVisibility(View.INVISIBLE);
                imageBarcode.setVisibility(View.VISIBLE);
                setCurrentBarcode(bitmap);
            } else {
                imageBarcode.setVisibility(View.INVISIBLE);
                txtError.setVisibility(View.VISIBLE);
                txtError.setText(error.getMessage());
            }
        }
    }

}
