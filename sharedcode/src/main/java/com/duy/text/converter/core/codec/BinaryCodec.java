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

public class BinaryCodec extends CodecImpl {

    /**
     * convert text to binary
     * foo ->  01100110 01101111 01101111
     */
    private static String textToBinary(String text) {
        char[] bytes = text.toCharArray();
        StringBuilder binary = new StringBuilder();
        for (char c : bytes) {
            String string = Integer.toBinaryString(c);
            binary.append(string);
            binary.append(' ');
        }
        return binary.toString();
    }

    /**
     * convert binary to text
     * 01100110 01101111 01101111 -> foo
     *
     * @param text input
     * @return unicode text from binary
     */
    private static String binaryToText(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] arr = text.split(" ");
        for (String arg : arr) {
            try {
                int charCode = Integer.parseInt(arg, 2);
                stringBuilder.append(Character.valueOf((char) charCode));
            } catch (Exception e) {
                stringBuilder.append(" ").append(arg).append(" ");
            }
        }
        return stringBuilder.toString();
    }


    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return textToBinary(text);
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        try {
            return binaryToText(text);
        } catch (Exception e) {
            return text;
        }
    }




}
