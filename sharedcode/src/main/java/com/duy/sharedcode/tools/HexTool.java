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

package com.duy.sharedcode.tools;

import android.support.annotation.NonNull;

import java.math.BigInteger;

/**
 * Created by DUy on 06-Feb-17.
 */

public class HexTool implements Encoder, Decoder {

    /**
     * convert text to hex
     */
    public static String textToHex(String text) {
        String tmp = String.format("%x", new BigInteger(1, text.getBytes(/*YOUR_CHARSET?*/)));
        StringBuilder result = new StringBuilder();
        if (text.length() == 0) return "";
        for (int i = 0; i < tmp.length(); i += 2) {
            String str = tmp.substring(i, i + 2);
            result.append(str).append(" ");
        }
        return result.toString();
    }

    /**
     * convert text to hex
     */
    public static String hexToTex(String text) {
        StringBuilder result = new StringBuilder();
        String[] arr = text.split(" ");

        for (String arg : arr) {
            try {
                result.append((char) Integer.parseInt(arg, 16));
            } catch (Exception e) {
                result.append(" ").append(arg).append(" ");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return textToHex(text);
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        try {
            return hexToTex(text);
        } catch (Exception e) {
            return text;
        }
    }
}
