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

import com.duy.text.converter.core.codec.interfaces.CodecImpl;
import com.duy.text.converter.core.utils.CodePointUtil;

import java.util.Iterator;

/**
 * Created by DUy on 06-Feb-17.
 */

public class AsciiCodec extends CodecImpl {
    private String asciiToText(String text) {
        String[] arr = text.split(" ");
        setMax(arr.length);
        StringBuilder result = new StringBuilder();
        for (String codePoint : arr) {
            try {
                result.append(Character.toChars(Integer.parseInt(codePoint)));
                incConfident();
            } catch (Exception e) {
                result.append(codePoint);
            }
        }
        return result.toString();
    }

    private String textToAscii(String text) {
        setMax(text.length());
        setConfident(text.length());

        StringBuilder result = new StringBuilder();
        Iterator<Integer> codePoints = CodePointUtil.codePoints(text).iterator();
        while (codePoints.hasNext()) {
            result.append(codePoints.next());
            if (codePoints.hasNext()) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return textToAscii(text);
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return asciiToText(text);
    }


    @NonNull
    @Override
    public String getName(Context context) {
        return "Ascii";
    }
}
