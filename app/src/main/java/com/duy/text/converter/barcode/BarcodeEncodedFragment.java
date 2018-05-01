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

package com.duy.text.converter.barcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
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

import com.duy.text.converter.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by Duy on 23-Aug-17.
 */

public class BarcodeEncodedFragment extends Fragment {
    private TextView mTxtError;
    private ImageView mImageBarcode;
    private BarcodeGenerateTask mGenerateTask;
    private Bitmap mCurrentBarcode = null;
    private ProgressBar mProgressBar;
    private BarcodeFormat mBarcodeFormat;

    public static BarcodeEncodedFragment newInstance(String data, BarcodeFormat barcodeFormat) {
        Bundle args = new Bundle();
        args.putString("data", data);
        args.putSerializable("format", barcodeFormat);
        BarcodeEncodedFragment fragment = new BarcodeEncodedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barcode_encoded, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageBarcode = view.findViewById(R.id.img_barcode);
        mTxtError = view.findViewById(R.id.txt_message);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);

        String data = getArguments().getString("data");
        mBarcodeFormat = (BarcodeFormat) getArguments().getSerializable("format");
        mGenerateTask = new BarcodeGenerateTask(data, mBarcodeFormat);
        mGenerateTask.execute();
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Nullable
    private File saveCurrentImage() throws IOException {
        if (!permissionGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                return null;
            }
        }
        if (mCurrentBarcode != null) {
            File file;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss", Locale.US);
            String fileName = mBarcodeFormat.toString() + "_" + dateFormat.format(new Date()) + ".png";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String filePath = "TextConverter" + File.separator + fileName;
                file = new File(Environment.getExternalStorageDirectory(), filePath);
            } else {
                String filePath = "image" + File.separator + fileName;
                file = new File(getContext().getFilesDir(), filePath);
            }
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            file.setReadable(true);
            mCurrentBarcode.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
            Toast.makeText(getContext(), getString(R.string.message_saved_in) + " " + file.getPath(), Toast.LENGTH_SHORT).show();
            return file;
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mGenerateTask != null) {
            if (!mGenerateTask.isCancelled()) {
                mGenerateTask.cancel(true);
            }
        }
        if (mCurrentBarcode != null && !mCurrentBarcode.isRecycled()) mCurrentBarcode.recycle();
    }

    private boolean permissionGranted() {
        int result = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void shareCurrentImage() {
        try {
            File file = saveCurrentImage();
            if (file != null) {
                Uri bitmapUri;
                if (Build.VERSION.SDK_INT >= N) {
                    bitmapUri = FileProvider.getUriForFile(getContext(),
                            getContext().getPackageName() + ".fileprovider", file);
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
        if (mCurrentBarcode != null && !mCurrentBarcode.isRecycled()) mCurrentBarcode.recycle();
        this.mCurrentBarcode = bitmap;
        mImageBarcode.setImageBitmap(bitmap);
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
            mProgressBar.setVisibility(View.VISIBLE);
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
            mProgressBar.setVisibility(View.INVISIBLE);
            if (bitmap != null) {
                mTxtError.setVisibility(View.INVISIBLE);
                mImageBarcode.setVisibility(View.VISIBLE);
                setCurrentBarcode(bitmap);
            } else {
                mImageBarcode.setVisibility(View.INVISIBLE);
                mTxtError.setVisibility(View.VISIBLE);
                mTxtError.setText(error.getMessage());
            }
        }
    }

}
