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

package com.duy.text.converter.core.codec;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by DUy on 06-Feb-17.
 */

public class HexCodec extends CodecImpl {

    /**
     * convert text to hex
     */
    public static String textToHex(String text) {
        StringBuilder result = new StringBuilder();
        char[] chars = text.toCharArray();
        for (char c : chars) {
            result.append(Integer.toHexString(c)).append(" ");
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
                result.append(Character.toChars(Integer.parseInt(arg, 16)));
            } catch (Exception e) {
                result.append(" ").append(arg).append(" ");
            }
        }
        return result.toString();
    }


    @NonNull
    @Override
    public String encode(@NonNull String text) {
        try {
            return textToHex(text);
        } catch (Exception e) {
            return text;
        }
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
