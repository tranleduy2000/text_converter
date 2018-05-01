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
