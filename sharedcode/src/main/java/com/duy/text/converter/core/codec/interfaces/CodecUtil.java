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

package com.duy.text.converter.core.codec.interfaces;

import android.content.Context;

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
import com.duy.text.converter.core.codec.RotCodec;
import com.duy.text.converter.core.codec.SubscriptCodec;
import com.duy.text.converter.core.codec.SupscriptCodec;
import com.duy.text.converter.core.codec.URLCodec;
import com.duy.text.converter.core.codec.UpperCaseCodec;
import com.duy.text.converter.core.codec.UpsideDownTool;
import com.duy.text.converter.core.codec.ZalgoBigCodec;
import com.duy.text.converter.core.codec.ZalgoMiniCodec;
import com.duy.text.converter.core.codec.ZalgoNormalCodec;

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
            case UPPER:
                return new UpperCaseCodec().decode(inp);
            case LOWER:
                return new LowerCaseCodec().decode(inp);
            case REVERSER:
                return new ReverserCodec().decode(inp);
            case UPSIDE_DOWNSIDE:
                return UpsideDownTool.textToUpsideDown(inp);
            case SUPPER_SCRIPT:
                return new SupscriptCodec().decode(inp);
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
                return new RotCodec().decode(inp);
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
            case UPPER:
                return new UpperCaseCodec().encode(inp);
            case LOWER:
                return new LowerCaseCodec().encode(inp);
            case REVERSER:
                return new ReverserCodec().encode(inp);
            case UPSIDE_DOWNSIDE:
                return UpsideDownTool.textToUpsideDown(inp);
            case SUPPER_SCRIPT:
                return new SupscriptCodec().encode(inp);
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
                return new RotCodec().encode(inp);
            default:
                return encodeMethod.getCodec().encode(inp);
        }
    }


}
