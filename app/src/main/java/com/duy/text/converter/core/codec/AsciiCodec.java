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

import java.util.Iterator;

/**
 * Created by DUy on 06-Feb-17.
 */

public class AsciiCodec extends CodecImpl {
    private String asciiToText(String text) {
        String[] arr = text.split(" ");
        setMax(arr.length);
        StringBuilder result = new StringBuilder();
        for (String codePoint : arr) {
            try {
                result.append(Character.toChars(Integer.parseInt(codePoint)));
                incConfident();
            } catch (Exception e) {
                result.append(codePoint);
            }
        }
        return result.toString();
    }

    private String textToAscii(String text) {
        setMax(text.length());
        setConfident(text.length());

        StringBuilder result = new StringBuilder();
        Iterator<Integer> codePoints = CodePointUtil.codePoints(text).iterator();
        while (codePoints.hasNext()) {
            result.append(codePoints.next());
            if (codePoints.hasNext()) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return textToAscii(text);
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return asciiToText(text);
    }


    @NonNull
    @Override
    public String getName(Context context) {
        return "Ascii";
    }
}
