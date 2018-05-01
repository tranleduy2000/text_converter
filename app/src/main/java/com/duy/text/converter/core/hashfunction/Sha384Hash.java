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

package com.duy.text.converter.core.hashfunction;

import android.support.annotation.NonNull;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Duy on 2/8/2018.
 */

public class Sha384Hash implements IHash {
    @NonNull
    @Override
    public String getName() {
        return "SHA-384";
    }

    @Override
    public String encode(String text) {
        try {
            return DigestUtils.sha384Hex(text.getBytes());
        } catch (Exception e) {
            return "";
        }
    }
}
