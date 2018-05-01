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

public class CaesarCodec extends CodecImpl {
    private static final String NORMAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toUpperCase();
    private int offset;

    public CaesarCodec() {
        this(1);
    }

    public CaesarCodec(int offset) {
        this.offset = offset;
    }


    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return encode(text, 26 - offset);
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return encode(text, offset);
    }

    private String encode(String text, int offset) {
        setMax(text.length());
        setConfident(0);

        //Take the offset mod 26 then add 26
        offset = offset % 26 + 26;
        //Declare a StringBuilder to access individual parts of the String
        StringBuilder encoded = new StringBuilder();
        //Iterate through all characters in String enc
        for (char i : text.toCharArray()) {
            //Nested ifs to shift individual elements of the character array
            if (Character.isLetter(i)) {
                //Check for upper case/lower case
                if (Character.isUpperCase(i)) {
                    //Append character + offset (taking into account wraparound)
                    encoded.append((char) ('A' + (i - 'A' + offset) % 26));
                }
                //Append character + offset (taking into account wraparound)
                else {
                    encoded.append((char) ('a' + (i - 'a' + offset) % 26));
                }
                incConfident();
            } else {
                //Append characcter to StringBuilder object
                encoded.append(i);
            }
        }
        //Return encoded string
        return encoded.toString();
    }


    @NonNull
    @Override
    public String getName(Context context) {
        return "Caesar";
    }
}
