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

public class BinaryCodec extends CodecImpl {

    /**
     * convert text to binary
     * foo ->  01100110 01101111 01101111
     */
    private String textToBinary(String text) {
        ArrayList<Integer> codePoints = CodePointUtil.codePointsArr(text);
        setMax(codePoints.size());

        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < codePoints.size(); i++) {
            Integer codePoint = codePoints.get(i);
            try {
                String bin = Integer.toBinaryString(codePoint);
                bin = appendZero(bin);
                binary.append(bin);
                if (i != codePoints.size() - 1) {
                    binary.append(' ');
                }
                incConfident();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return binary.toString();
    }

    private String appendZero(String binary) {
        int length = binary.length();
        int count = 8;
        while (count < length) count += 8;
        StringBuilder binBuilder = new StringBuilder(binary);
        while (binBuilder.length() < count) binBuilder.insert(0, "0");
        return binBuilder.toString();
    }


    /**
     * convert binary to text
     * 01100110 01101111 01101111 -> foo
     *
     * @param text input
     * @return unicode text from binary
     */
    private String binaryToText(String text) {
        StringBuilder builder = new StringBuilder();
        String[] arr = text.split(" ");
        setMax(arr);
        for (String arg : arr) {
            try {
                int codePoint = Integer.parseInt(arg, 2);
                builder.append(Character.toChars(codePoint));
                incConfident();
            } catch (Exception e) {
                builder.append(" ").append(arg).append(" ");
            }
        }
        return builder.toString();
    }


    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return textToBinary(text);
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return binaryToText(text);
    }

    @NonNull
    @Override
    public String getName(Context context) {
        return "Binary";
    }
}
