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
import com.duy.text.converter.utils.CodePointUtil;

import java.util.ArrayList;

/**
 * Created by DUy on 06-Feb-17.
 */

public class HexCodec extends CodecImpl {

    /**
     * convert text to hex
     */
    private String hexToTex(String text) {
        StringBuilder result = new StringBuilder();
        String[] arr = text.split(" ");
        setMax(arr);
        for (String arg : arr) {
            try {
                result.append(Character.toChars(Integer.parseInt(arg, 16)));
                incConfident();
            } catch (Exception e) {
                result.append(" ").append(arg).append(" ");
            }
        }
        return result.toString();
    }

    /**
     * convert text to hex
     */
    private String textToHex(String text) {
        StringBuilder result = new StringBuilder();
        ArrayList<Integer> chars = CodePointUtil.codePointsArr(text);
        setMax(chars.size());
        for (int i = 0; i < chars.size(); i++) {
            Integer c = chars.get(i);
            result.append(Integer.toHexString(c));
            if (i != chars.size() - 1) {
                result.append(" ");
            }
            incConfident();
        }
        return result.toString().toUpperCase();
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return textToHex(text);
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return hexToTex(text);
    }


    @NonNull
    @Override
    public String getName(Context context) {
        return "Hex";
    }
}
