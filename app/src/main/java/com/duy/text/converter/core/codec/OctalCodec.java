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

import android.support.annotation.NonNull;
import android.util.Log;

import com.duy.text.converter.core.codec.interfaces.CodecImpl;

/**
 * Created by DUy on 06-Feb-17.
 */

public class OctalCodec extends CodecImpl {
    private static final String TAG = "OctalCodec";


    private String textToOctal(String text) {
        setMax(text.length());
        setConfident(text.length());

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            result.append(Integer.toOctalString(c));
            result.append(" ");
        }
        Log.d(TAG, "textToOctal: " + result.toString());
        return result.toString();
    }

    private String octalToText(String text) {
        String[] arr = text.split(" ");
        setMax(arr);
        StringBuilder result = new StringBuilder();
        for (String arg : arr) {
            try {
                char c = (char) Integer.parseInt(arg, 8);
                result.append(c);
                incConfident();
            } catch (Exception e) {
                result.append(" ").append(arg).append(" ");
            }
        }
        return result.toString();
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return textToOctal(text);
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return octalToText(text);
    }


}
