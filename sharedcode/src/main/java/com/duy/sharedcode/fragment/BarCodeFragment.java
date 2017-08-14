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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.duy.sharedcode.BarcodeEncodeActivity;
import com.duy.sharedcode.ClipboardUtil;
import com.duy.sharedcode.view.BaseEditText;
import com.duy.textconverter.sharedcode.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;


/**
 * Created by Duy on 14-Aug-17.
 */

public class BarCodeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "BarCodeFragment";
    private static final String KEY_TEXT = "KEY_TEXT";
    private BaseEditText mInput;
    private Spinner mSpinnerBarcodeFormat;

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

        mSpinnerBarcodeFormat = view.findViewById(R.id.spinner_choose);

        View imgCopyIn = view.findViewById(R.id.img_copy);
        View imgShareIn = view.findViewById(R.id.img_share);
        imgShareIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doShareText(mInput);
            }
        });

        imgCopyIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardUtil.setClipboard(getContext(), mInput.getText().toString());
            }
        });

        String[] data = getResources().getStringArray(R.array.barcode_format);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mSpinnerBarcodeFormat.setAdapter(adapter);
        mSpinnerBarcodeFormat.setSelection(BarcodeFormat.QR_CODE.ordinal());

        view.findViewById(R.id.btn_encode).setOnClickListener(this);
        view.findViewById(R.id.btn_decode).setOnClickListener(this);
    }

    private void doShareText(EditText editText) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
        intent.setType("text/plain");
        getActivity().startActivity(intent);
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
            encode(mInput.getText().toString(), BarcodeFormat.values()[mSpinnerBarcodeFormat.getSelectedItemPosition()]);
        } else if (v.getId() == R.id.btn_decode) {
            decodeBarcode();
        }
    }

    private void decodeBarcode() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.initiateScan();
    }

    private void encode(String s, BarcodeFormat barcodeFormat) {
        Intent intent = new Intent(getContext(), BarcodeEncodeActivity.class);
        intent.putExtra("data", s);
        intent.putExtra("format", barcodeFormat);
        startActivity(intent);
    }
}
