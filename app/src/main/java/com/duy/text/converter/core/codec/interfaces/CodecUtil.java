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

package com.duy.text.converter.core.codec.interfaces;

import android.content.Context;
import android.support.annotation.NonNull;

import com.duy.text.converter.R;
import com.duy.text.converter.core.codec.AsciiCodec;
import com.duy.text.converter.core.codec.AtbashCodec;
import com.duy.text.converter.core.codec.Base32Codec;
import com.duy.text.converter.core.codec.Base64Codec;
import com.duy.text.converter.core.codec.BinaryCodec;
import com.duy.text.converter.core.codec.CaesarCodec;
import com.duy.text.converter.core.codec.HexCodec;
import com.duy.text.converter.core.codec.LowerCaseCodec;
import com.duy.text.converter.core.codec.MorseCodec;
import com.duy.text.converter.core.codec.OctalCodec;
import com.duy.text.converter.core.codec.RandomCaseCodec;
import com.duy.text.converter.core.codec.ReverserCodec;
import com.duy.text.converter.core.codec.Rot13Codec;
import com.duy.text.converter.core.codec.SubscriptCodec;
import com.duy.text.converter.core.codec.SuperscriptCodec;
import com.duy.text.converter.core.codec.URLCodec;
import com.duy.text.converter.core.codec.UpperCaseCodec;
import com.duy.text.converter.core.codec.UpsideDownCodec;
import com.duy.text.converter.core.codec.ZalgoBigCodec;
import com.duy.text.converter.core.codec.ZalgoMiniCodec;
import com.duy.text.converter.core.codec.ZalgoNormalCodec;

import java.util.ArrayList;

/**
 * Created by Duy on 29-Jul-17.
 */

public class CodecUtil {
    public static String decode(String method, Context context, String inp) {
        String[] array = context.getResources().getStringArray(R.array.codec_methods);
        int pos;
        for (pos = 0; pos < array.length; pos++) {
            String s = array[pos];
            if (s.equals(method)) {
                break;
            }
        }
        CodecMethod decodeMethod = CodecMethod.values()[pos];
        return decode(decodeMethod, inp);
    }

    public static String decode(CodecMethod decodeMethod, String inp) {
        switch (decodeMethod) {
            case ASCII:
                return new AsciiCodec().decode(inp);
            case OCTAL:
                return new OctalCodec().decode(inp);
            case BINARY:
                return new BinaryCodec().decode(inp);
            case HEX:
                return new HexCodec().decode(inp);
            case UPPER_CASE:
                return new UpperCaseCodec().decode(inp);
            case LOWER:
                return new LowerCaseCodec().decode(inp);
            case REVERSER:
                return new ReverserCodec().decode(inp);
            case UPSIDE_DOWNSIDE:
                return new UpsideDownCodec().decode(inp);
            case SUPPER_SCRIPT:
                return new SuperscriptCodec().decode(inp);
            case SUB_SCRIPT:
                return new SubscriptCodec().decode(inp);
            case MORSE_CODE:
                return new MorseCodec().decode(inp);
            case BASE_64:
                return new Base64Codec().decode(inp);
            case ZALGO_MINI:
                break;
            case ZALGO_NORMAL:
                break;
            case ZALGO_BIG:
                break;
            case BASE_32:
                return new Base32Codec().decode(inp);
            case URL:
                return new URLCodec().decode(inp);
            case RANDOM_CASE:
                return new RandomCaseCodec().decode(inp);
            case CAESAR:
                return new CaesarCodec().decode(inp);
            case ATBASH:
                return new AtbashCodec().decode(inp);
            case ROT_13:
                return new Rot13Codec().decode(inp);
            default:
                return decodeMethod.getCodec().decode(inp);
        }
        return inp;
    }

    public static String encode(String name, Context context, String inp) {
        String[] array = context.getResources().getStringArray(R.array.codec_methods);
        int pos;
        for (pos = 0; pos < array.length; pos++) {
            String s = array[pos];
            if (s.equals(name)) {
                break;
            }
        }
        CodecMethod encodeMethod = CodecMethod.values()[pos];
        return encode(encodeMethod, inp);
    }

    public static String encode(CodecMethod encodeMethod, String inp) {
        switch (encodeMethod) {
            case ASCII:
                return new AsciiCodec().encode(inp);
            case OCTAL:
                return new OctalCodec().encode(inp);
            case BINARY:
                return new BinaryCodec().encode(inp);
            case HEX:
                return new HexCodec().encode(inp);
            case UPPER_CASE:
                return new UpperCaseCodec().encode(inp);
            case LOWER:
                return new LowerCaseCodec().encode(inp);
            case REVERSER:
                return new ReverserCodec().encode(inp);
            case UPSIDE_DOWNSIDE:
                return new UpsideDownCodec().encode(inp);
            case SUPPER_SCRIPT:
                return new SuperscriptCodec().encode(inp);
            case SUB_SCRIPT:
                return new SubscriptCodec().encode(inp);
            case MORSE_CODE:
                return new MorseCodec().encode(inp);
            case BASE_64:
                return new Base64Codec().encode(inp);
            case ZALGO_MINI:
                return new ZalgoMiniCodec().encode(inp);
            case ZALGO_NORMAL:
                return new ZalgoNormalCodec().encode(inp);
            case ZALGO_BIG:
                return new ZalgoBigCodec().encode(inp);
            case BASE_32:
                return new Base32Codec().encode(inp);
            case URL:
                return new URLCodec().encode(inp);
            case RANDOM_CASE:
                return new RandomCaseCodec().encode(inp);
            case CAESAR:
                return new CaesarCodec().encode(inp);
            case ATBASH:
                return new AtbashCodec().encode(inp);
            case ROT_13:
                return new Rot13Codec().encode(inp);
            default:
                return encodeMethod.getCodec().encode(inp);
        }
    }

    @NonNull
    public static ArrayList<String> getAllCodecName(@NonNull Context context) {
        ArrayList<String> names = new ArrayList<>();
        for (CodecMethod codec : CodecMethod.values()) {
            names.add(codec.getCodec().getName(context));
        }
        return names;
    }

}
