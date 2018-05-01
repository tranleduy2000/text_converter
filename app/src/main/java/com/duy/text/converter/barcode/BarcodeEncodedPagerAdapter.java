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

package com.duy.text.converter.barcode;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.zxing.BarcodeFormat;

/**
 * Created by Duy on 23-Aug-17.
 */

public class BarcodeEncodedPagerAdapter extends FragmentStatePagerAdapter {
    public static final BarcodeFormat[] BARCODE_FORMATS = new BarcodeFormat[]{
            BarcodeFormat.AZTEC, BarcodeFormat.CODABAR, BarcodeFormat.CODE_39, BarcodeFormat.CODE_128,
            BarcodeFormat.EAN_8, BarcodeFormat.EAN_13, BarcodeFormat.ITF, BarcodeFormat.PDF_417,
            BarcodeFormat.QR_CODE, BarcodeFormat.UPC_A
    };
    private String data;

    public BarcodeEncodedPagerAdapter(FragmentManager fm, String data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return BarcodeEncodedFragment.newInstance(data, BARCODE_FORMATS[position]);
    }

    @Override
    public int getCount() {
        return BARCODE_FORMATS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return BARCODE_FORMATS[position].name();
    }
}
