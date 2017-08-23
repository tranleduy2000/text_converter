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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.duy.textconverter.sharedcode.BuildConfig;
import com.duy.textconverter.sharedcode.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
        view.findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCurrentImage();
            }
        });
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveCurrentImage();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Cannot save", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private File saveCurrentImage() throws IOException {
        if (!permissionGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                return null;
            }
        }
        if (currentBarcode != null) {
            File file;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                file = new File(Environment.getExternalStorageDirectory(),
                        "TextConverter" + File.separator + System.currentTimeMillis() + ".png");
            } else {
                file = new File(getContext().getFilesDir(),
                        "image" + File.separator + System.currentTimeMillis() + ".png");
            }
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            file.setReadable(true);
            currentBarcode.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
            Toast.makeText(getContext(), R.string.saved_in_gallery, Toast.LENGTH_SHORT).show();
            return file;
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        try {
            File file = saveCurrentImage();
            if (file != null) {
                Uri bitmapUri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    bitmapUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID
                            + ".fileprovider", file);
                } else {
                    bitmapUri = Uri.fromFile(file);
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("image/*");
                startActivity(Intent.createChooser(intent, "Share via ..."));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                int width = 1024;
                int height = 1024;
                switch (format) {
                    case AZTEC:
                        break;
                    case CODABAR:
                        height = (int) (width * 0.4f);
                        break;
                    case CODE_39:
                        height = (int) (width * 0.4f);
                        break;
                    case CODE_93:
                        break;
                    case CODE_128:
                        height = (int) (width * 0.4f);
                        break;
                    case DATA_MATRIX:
                        break;
                    case EAN_8:
                        height = (int) (width * 0.4f);
                        break;
                    case EAN_13:
                        height = (int) (width * 0.4f);
                        break;
                    case ITF:
                        height = (int) (width * 0.4f);
                        break;
                    case MAXICODE:
                        break;
                    case PDF_417:
                        height = (int) (width * 0.4f);
                        break;
                    case QR_CODE:
                        break;
                    case RSS_14:
                        break;
                    case RSS_EXPANDED:
                        break;
                    case UPC_A:
                        break;
                    case UPC_E:
                        break;
                    case UPC_EAN_EXTENSION:
                        break;
                }
                BitMatrix bitMatrix = multiFormatWriter.encode(data, format, width, height);
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
