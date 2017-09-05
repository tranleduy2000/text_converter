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

package com.duy.sharedcode.codec;

import android.content.Context;

import com.duy.textconverter.sharedcode.R;

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
                return new AsciiTool().decode(inp);
            case OCTAL:
                return new OctalTool().decode(inp);
            case BINARY:
                return new BinaryTool().decode(inp);
            case HEX:
                return new HexTool().decode(inp);
            case UPPER:
                return UpperLowerTool.upperText(inp);
            case LOWER:
                return UpperLowerTool.lowerText(inp);
            case REVERSER:
                return ReverserTool.reverseText(inp);
            case UPSIDE_DOWNSIDE:
                return UpsideDownTool.textToUpsideDown(inp);
            case SUPPER_SCRIPT:
                return new SupScriptText().decode(inp);
            case SUB_SCRIPT:
                return new SubScriptText().decode(inp);
            case MORSE_CODE:
                return new MorseTool().decode(inp);
            case BASE_64:
                return new Base64Tool().decode(inp);
            case ZALGO_MINI:
                break;
            case ZALGO_NORMAL:
                break;
            case ZALGO_BIG:
                break;
            case BASE_32:
                return new Base32Tool().decode(inp);
            case URL:
                return new URLTool().decode(inp);
            case RANDOM_CASE:
                return new RandomCaseTool().decode(inp);
            case CAESAR:
                return new CaesarTool().decode(inp);
            case ATBASH:
                return new AtbashTool().decode(inp);
            case ROT_13:
                return new RotCodec().decode(inp);
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
                return new AsciiTool().encode(inp);
            case OCTAL:
                return new OctalTool().encode(inp);
            case BINARY:
                return new BinaryTool().encode(inp);
            case HEX:
                return new HexTool().encode(inp);
            case UPPER:
                return UpperLowerTool.upperText(inp);
            case LOWER:
                return UpperLowerTool.lowerText(inp);
            case REVERSER:
                return ReverserTool.reverseText(inp);
            case UPSIDE_DOWNSIDE:
                return UpsideDownTool.textToUpsideDown(inp);
            case SUPPER_SCRIPT:
                return new SupScriptText().encode(inp);
            case SUB_SCRIPT:
                return new SubScriptText().encode(inp);
            case MORSE_CODE:
                return new MorseTool().encode(inp);
            case BASE_64:
                return new Base64Tool().encode(inp);
            case ZALGO_MINI:
                return new ZalgoMiniTool().encode(inp);
            case ZALGO_NORMAL:
                return new ZalgoNormalTool().encode(inp);
            case ZALGO_BIG:
                return new ZalgoBigTool().encode(inp);
            case BASE_32:
                return new Base32Tool().encode(inp);
            case URL:
                return new URLTool().encode(inp);
            case RANDOM_CASE:
                return new RandomCaseTool().encode(inp);
            case CAESAR:
                return new CaesarTool().encode(inp);
            case ATBASH:
                return new AtbashTool().encode(inp);
            case ROT_13:
                return new RotCodec().encode(inp);
        }
        return inp;
    }


}
