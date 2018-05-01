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

package com.duy.text.converter.core.codec;

import android.content.Context;
import android.support.annotation.NonNull;

import com.duy.text.converter.core.codec.interfaces.CodecImpl;

/**
 * Created by Duy on 08-Aug-17.
 */

public class AtbashCodec extends CodecImpl {
    private static final String NORMAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toUpperCase();


    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return decodeImpl(text);
    }

    private String decodeImpl(String text) {
        StringBuilder encoded = new StringBuilder();
        char[] chars = text.toCharArray();
        setMax(chars.length);
        for (char c : chars) {
            try {
                int index = NORMAL.indexOf(Character.toUpperCase(c));
                if (index > -1) {
                    char cc = NORMAL.charAt(NORMAL.length() - index - 1);
                    if (Character.isUpperCase(c)) {
                        encoded.append(Character.toUpperCase(cc));
                    } else {
                        encoded.append(Character.toLowerCase(cc));
                    }
                } else {
                    encoded.append(c);
                }
                incConfident();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return encoded.toString();
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return decodeImpl(text);
    }


    @NonNull
    @Override
    public String getName(Context context) {
        return "Atbash";
    }
}
