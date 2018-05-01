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

public class OctalCodec extends CodecImpl {


    private String octalToText(String text) {
        String[] arr = text.split(" ");
        setMax(arr);
        StringBuilder result = new StringBuilder();
        for (String arg : arr) {
            try {
                int codePoint = Integer.parseInt(arg, 8);
                result.append(Character.toChars(codePoint));
                incConfident();
            } catch (Exception e) {
                result.append(" ").append(arg).append(" ");
            }
        }
        return result.toString();
    }

    @NonNull
    @Override
    public String getName(Context context) {
        return "Octal";
    }

    @NonNull
    @Override
    public String encode(@NonNull final String text) {
        final ArrayList<Integer> chars = CodePointUtil.codePointsArr(text);
        setMax(chars.size());
        setConfident(chars.size());

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < chars.size(); i++) {
            Integer c = chars.get(i);
            result.append(Integer.toOctalString(c));
            if (i != chars.size() - 1) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return octalToText(text);
    }


}
